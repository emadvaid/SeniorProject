package com.ALCverificationtool.services.userService;

import com.ALCverificationtool.controllers.users.AuthUserResponse;
import com.ALCverificationtool.dao.authentication.UserAuthenticationRepository;
import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.ResetToken;
import com.ALCverificationtool.controllers.users.User;
import com.ALCverificationtool.models.UserAuthentication;
import com.ALCverificationtool.models.UserRec;
import com.ALCverificationtool.services.authResetService.AuthResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final int MIN_PASSWORD_LEN = 8;

    private final UserRepository userDao;
    private final UserAuthenticationRepository authDao;
    private final AuthResetService authResetService;

    @Autowired
    public UserServiceImpl(
        UserRepository userDao,
        UserAuthenticationRepository authDao,
        AuthResetService authResetService
    ) {
       this.userDao = userDao;
       this.authDao = authDao;
       this.authResetService = authResetService;
    }

    @Override
    public AuthUserResponse authByUsernamePassword(String username, String password) {

        // first make sure the username and password both exist and are non-empty
        if(username==null || username.length()<1 || password==null || password.length()<1){
            throw new UserAuthenticationException("Username and Password required");
        }

        // try to find a userRec record for the given username
        Optional<UserRec> userOpt = userDao.findByUsername(username);

        // fail if record not found
        if(!userOpt.isPresent()){
            throw new UserAuthenticationException("UserRec not found: username = " + username);
        }

        UserRec userRec = userOpt.get();

        // fail if the passwords don't match
        if(!password.equals(userRec.getPassword())){
            throw new UserAuthenticationException("Password not match for userRec: username = " + username);
        }

        // userRec is authenticated so create a auth record
        UserAuthentication auth = authDao.save(
                new UserAuthentication(userRec.getId(), userRec.getType()));

        // create a temp userRec object to return
        UserRec tmp = new UserRec(userRec);
        // make sure to remove the password
        tmp.setPassword(null);
        // return the new users record
        return new AuthUserResponse(new User(tmp), auth);
    }

    @Override
    public Optional<UserRec> createUser(UserRec newUserRecDetails) {

        // check for empty details
        if(newUserRecDetails == null){
            throw new UserException("newUserRecDetails can not be null");
        }

        // check for empty username
        if(newUserRecDetails.getUsername()==null || newUserRecDetails.getUsername().length()<1){
            throw new UserException("Username cant be empty");
        }


        // check for duplicate username
        if(userDao.findByUsername(newUserRecDetails.getUsername()).isPresent()){
            throw new UserException("Username already exists.");
        }

        // Make sure user id is null
        UserRec tmpUserRec = new UserRec(newUserRecDetails);
        tmpUserRec.setId(null);
        tmpUserRec.setPassword(null);
        tmpUserRec.setActive(true);

        // insert the new user
        UserRec newUserRec = userDao.save(tmpUserRec);

        // make sure the insert was successful
        if (newUserRec == null || newUserRec.getId() == null) {
            throw new UserException("Could not create new User");
        }

        // Now create a password reset entity for user
        ResetToken reset = this.authResetService.createPasswordResetToken(newUserRec, true);

        // make sure valid reset
        if (reset == null
                || reset.getId() == null || reset.isActive()
                || !newUserRec.getId().equals(reset.getUserId())) {
            throw new UserException("Error creating password reset for new user");
        }

        return Optional.of(newUserRec);
    }

    @Override
    public List<UserRec> getUsers(){
        // get the user records from the database
        List<UserRec> results = userDao.findAll();

//        for(UserRec rec: results) {
//            if(rec.getUsername().equals("admin")) {
//                results.remove(rec);
//            }
//        }
//
//        // return the user records
        return results;
    }

    @Override
    public Optional<UserRec> getUserById(String userId) {
        return this.userDao.findById(UUID.fromString(userId));
    }

    @Override
    public Optional<UserRec> updateUser(String userId, User userDetails) {

        // first make sure the userId is a valid UUID
        if(!isUUID(userId)) {
            throw new UserException("invalid userId.");
        }

        // next make sure the user exists in the database
        Optional<UserRec> actualUserRecOpt = this.userDao.findById(UUID.fromString(userId));

        if(!actualUserRecOpt.isPresent()) {
            throw new UserException("could not find userRec for userId = " + userId);
        }

        // make sure the user is active
        UserRec actualUserRec = actualUserRecOpt.get();
        if(!actualUserRec.isActive()) {
            throw new UserException("user not active");
        }

        // then copy the relevant values to the userRec Entity
        actualUserRec.setFirstName(userDetails.getFirstName());
        actualUserRec.setLastName(userDetails.getLastName());
        actualUserRec.setLanguages(userDetails.getLanguages());


        // then save
        UserRec newUserRec = this.userDao.save(actualUserRec);

        // then check the user was updated return the updated userRecEntity
        if(newUserRec == null
                || !newUserRec.getId().equals(userDetails.getId())
                || !newUserRec.getFirstName().equals(userDetails.getFirstName())
                || !newUserRec.getLastName().equals(userDetails.getLastName())
        ) {
            throw new UserException("user not updated");
        }
        return Optional.of(newUserRec);
    }

    @Override
    public Optional<UserRec> resetUserPassword(String userId) {
        // first make sure the userId is a valid UUID
        if(!isUUID(userId)) {
            throw new UserException("invalid userId.");
        }

        // next make sure the user exists in the database
        Optional<UserRec> actualUserRecOpt = this.userDao.findById(UUID.fromString(userId));

        if(!actualUserRecOpt.isPresent()) {
            throw new UserException("could not find userRec for userId = " + userId);
        }

        // make sure the user is active
        UserRec actualUserRec = actualUserRecOpt.get();
        if(!actualUserRec.isActive()) {
            throw new UserException("user not active");
        }

        // then copy the relevant values to the userRec Entity
       // actualUserRec.setPassword(null);

        // then save
        UserRec newUserRec = this.userDao.save(actualUserRec);

        // then check the user was updated return the updated userRecEntity
//        if(newUserRec == null|| newUserRec.getPassword() == null) {
//            throw new UserException("user not updated");
//        }

        // create a reset Entity for the user (sends the email)


        // Now create a password reset entity for user
        ResetToken reset = this.authResetService.createPasswordResetToken(newUserRec, false);

        // make sure valid reset
//        if (reset == null
//                || reset.getId() == null || !reset.isActive()
//                || !newUserRec.getId().equals(reset.getUserId())) {
//            throw new UserException("Error creating password reset for user");
//        }

        return Optional.of(newUserRec);

    }

    @Override
    public Optional<UserRec> activateUser(String userId) {
        // first make sure the userId is a valid UUID
        if(!isUUID(userId)) {
            throw new UserException("invalid userId.");
        }

        // next make sure the user exists in the database
        Optional<UserRec> actualUserRecOpt = this.userDao.findById(UUID.fromString(userId));

        if(!actualUserRecOpt.isPresent()) {
            throw new UserException("could not find userRec for userId = " + userId);
        }

        UserRec actualUserRec = actualUserRecOpt.get();

        // then copy the relevant values to the userRec Entity
        actualUserRec.setActive(true);

        // then save
        UserRec newUserRec = this.userDao.save(actualUserRec);

        // then check the user was updated return the updated userRecEntity
        if(newUserRec == null || !newUserRec.isActive() ) {
            throw new UserException("user not active");
        }
        return Optional.of(newUserRec);

    }

    @Override
    public Optional<UserRec> deactivateUser(String userId) {
        // first make sure the userId is a valid UUID
        if(!isUUID(userId)) {
            throw new UserException("invalid userId.");
        }

        // next make sure the user exists in the database
        Optional<UserRec> actualUserRecOpt = this.userDao.findById(UUID.fromString(userId));

        if(!actualUserRecOpt.isPresent()) {
            throw new UserException("could not find userRec for userId = " + userId);
        }

        // make sure the user is active
        UserRec actualUserRec = actualUserRecOpt.get();
        if(!actualUserRec.isActive()) {
            throw new UserException("user not active");
        }

        // then copy the relevant values to the userRec Entity
        actualUserRec.setActive(false);

        // then save
        UserRec newUserRec = this.userDao.save(actualUserRec);

        // then check the user was updated return the updated userRecEntity
        if(newUserRec == null || newUserRec.isActive()) {
            throw new UserException("user not active");
        }
        return Optional.of(newUserRec);
    }

    public boolean isUUID(String string) {
        if(string == null) return false;

        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
