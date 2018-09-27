package com.ALCverificationtool.services.authResetService;

import com.ALCverificationtool.dao.resetEntity.ResetEntityRepository;
import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.ResetEntity;
import com.ALCverificationtool.models.User;
import com.ALCverificationtool.models.UserRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Component
public class AuthResetServiceImpl implements AuthResetService  {

    private final JavaMailSender mailSender;
    private final UserRepository userDao;
    private final ResetEntityRepository resetDao;

    @Autowired
    AuthResetServiceImpl(JavaMailSender mailSender,
                         UserRepository userDao,
                         ResetEntityRepository resetDao) {

        this.mailSender = mailSender;
        this.userDao = userDao;
        this.resetDao = resetDao;
    }

    @Override
    public ResetEntity resetUserPassword(User user) {

        // first make sure that the user is not null and the id is set (throw an exception)
        if(user==null || user.getId()==null) {
            throw new AuthResetException("user cannot be null");
        }

        // make sure to get the User record from the database
        Optional<UserRec> actualUserOpt = userDao.findById(user.getId());

        if(!actualUserOpt.isPresent()) {
            throw new AuthResetException("user not found for id: " + user.getId());
        }

        UserRec actualUserRec = actualUserOpt.get();

        // make sure the user has a valid email (throw exception)
        if(user.getEmail() == null){
            throw new AuthResetException("email not set for user");
        }

        // reset the password field to null
        actualUserRec.setPassword(null);
        this.userDao.save(actualUserRec);

        // create a reset entity
        ResetEntity newResetDetails = new ResetEntity();
        newResetDetails.setId(null);
        newResetDetails.setUserId(actualUserRec.getId());
        newResetDetails.setUsed(false);

        ResetEntity newResetEntity = this.resetDao.save(newResetDetails);

        // check to see if the reset entity was not created
        if(newResetEntity == null) {
            throw new AuthResetException("Failed to create restet entity");
        }

        // send an email to the registered user's email address, with the reset id

        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper mailHelper = new MimeMessageHelper(msg, false, "utf-8");

            String htmlMsg = "<h1>Hello World</h1>";

            msg.setContent(htmlMsg, "text/html");

            mailHelper.setTo(actualUserRec.getEmail());
            mailHelper.setSubject("Password reset ...");
            //mailHelper.setFrom("bogus email acct no one is listening to");

            this.mailSender.send(msg);
        }
        catch (MessagingException e) {
            throw new AuthResetException("Could not send reset email", e);
        }

        return null;
    }
}
