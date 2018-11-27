package com.ALCverificationtool.services.authResetService;

import com.ALCverificationtool.dao.resetTokens.ResetTokenRepository;
import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.ResetToken;
import com.ALCverificationtool.models.UserRec;
import com.ALCverificationtool.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthResetServiceImpl implements AuthResetService  {

    private final JavaMailSender mailSender;
    private final UserRepository userDao;
    private final ResetTokenRepository resetDao;
    private final PasswordEncoder passwordEncoder;

    @Value("${alcverificationtool.url}")
    private String passwordResetUrl;


    @Autowired
    AuthResetServiceImpl(JavaMailSender mailSender,
                         UserRepository userDao,
                         ResetTokenRepository resetDao,
                         PasswordEncoder passwordEncoder) {

        this.mailSender = mailSender;
        this.userDao = userDao;
        this.resetDao = resetDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void resetPasswordWithResetToken(String resetTokenId, String newPassword) {
        // from the resetToken, get the reset entity

        UUID searchKey = UUID.fromString(resetTokenId);

        String SearchKeyStr = searchKey.toString();

        Optional<ResetToken> resetOpt = this.resetDao.findById(searchKey);

        if(!resetOpt.isPresent()) {
            throw new ServiceException("Reset token not found.");
        }

        ResetToken reset = resetOpt.get();

        // make sure this token is still active
        if(!reset.isActive()) {
            throw new ServiceException("Reset token not active.");
        }

        // find the user record for this token
        Optional<UserRec> userRecOpt = this.userDao.findById(reset.getUserId());

        if(!userRecOpt.isPresent()) {
            throw new ServiceException("User not found for reset token.");
        }

        UserRec userRec = userRecOpt.get();

        // make sure user is active
        if(!userRec.isActive()) {
            throw  new ServiceException("Cannot change password for inactive user.");
        }

        // We need to save the encoded password hash+salt
        userRec.setPassword(passwordEncoder.encode(newPassword));

        UserRec result = this.userDao.save(userRec);

        if(result==null || !passwordEncoder.matches(newPassword, userRec.getPassword()) || !result.getId().equals(userRec.getId())) {
            throw new ServiceException("Bad error.");
        }
    }

    @Override
    public ResetToken createPasswordResetToken(UserRec userRec) {
        return this.createPasswordResetToken(userRec, false);
    }

    @Override
    public ResetToken createPasswordResetToken(UserRec userRec, boolean newUser) {

        // first make sure that the user is not null and the id is set (throw an exception)
        if(userRec==null || userRec.getId()==null) {
            throw new AuthResetException("user cannot be null");
        }

        // make sure to get the User record from the database
        Optional<UserRec> actualUserOpt = userDao.findById(userRec.getId());

        if(!actualUserOpt.isPresent()) {
            throw new AuthResetException("user not found for id: " + userRec.getId());
        }

        UserRec actualUserRec = actualUserOpt.get();

        // make sure the user has a valid email (throw exception)
        if(userRec.getEmail() == null){
            throw new AuthResetException("email not set for user");
        }

        // reset the password field to null
        actualUserRec.setPassword(null);
        UserRec savedUserRec = this.userDao.save(actualUserRec);

        //make sure id's are the same
        if(savedUserRec==null || !actualUserRec.getId().equals(savedUserRec.getId())) {
            throw new AuthResetException("Something strange ...");
        }

        // create a reset entity
        ResetToken newResetDetails = new ResetToken();
        newResetDetails.setId(null);
        newResetDetails.setUserId(savedUserRec.getId());
        newResetDetails.setActive(true);

        ResetToken newResetEntity = this.resetDao.save(newResetDetails);

        // check to see if the reset entity was not created
        if(newResetEntity == null) {
            throw new AuthResetException("Failed to create reset entity");
        }

        // send an email to the registered user's email address, with the reset id

        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(msg, false, "utf-8");

            StringBuilder htmlMsgBuilder = new StringBuilder();

            String templateLoc;
            String resetLink;

            if(newUser) {
                templateLoc = "classpath:email-templates/password-reset/newUserEmail.template";
                resetLink = "<a href=\"" + this.passwordResetUrl + "?resetId=" +
                        newResetEntity.getId() + "&newUser=true\">Click here to reset Password</a>";
            }
            else {
                templateLoc = "classpath:email-templates/password-reset/passwordResetEmail.template";
                resetLink = "<a href=\"" + this.passwordResetUrl + "?resetId=" +
                        newResetEntity.getId() + "\">Click here to reset Password</a>";
            }

            try (BufferedReader in = new BufferedReader(new FileReader(ResourceUtils.getFile(templateLoc)))){
                String line;
                while((line = in.readLine())!=null) {
                    // add all substitutions here
                    line = line.replace("<ResetLink/>", resetLink);
                    line = line.replace("<UserName/>", savedUserRec.getUsername());
                    line = line.replace("<FistName/>", savedUserRec.getFirstName());
                    line = line.replace("<LastName/>", savedUserRec.getLastName());
                    htmlMsgBuilder.append(line);
                }
            }
            catch(IOException e) {
                throw new AuthResetException("Could not find email template for uri: " + templateLoc);
            }

            msg.setContent(htmlMsgBuilder.toString(), "text/html");

            mailHelper.setTo(actualUserRec.getEmail());
            mailHelper.setSubject("Password reset ...");
            //mailHelper.setFrom("bogus email acct no one is listening to");

            this.mailSender.send(msg);
        }
        catch (MessagingException e) {
            throw new AuthResetException("Could not send reset email", e);
        }

        return newResetEntity;
    }
}
