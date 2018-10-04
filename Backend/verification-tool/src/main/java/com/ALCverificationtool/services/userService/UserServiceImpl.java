package com.ALCverificationtool.services.userService;

import com.ALCverificationtool.controllers.users.AuthUserResponse;
import com.ALCverificationtool.dao.authentication.UserAuthenticationRepository;
import com.ALCverificationtool.dao.users.UserRepository;
import com.ALCverificationtool.models.ResetToken;
import com.ALCverificationtool.models.User;
import com.ALCverificationtool.models.UserAuthentication;
import com.ALCverificationtool.models.UserRec;
import com.ALCverificationtool.services.authResetService.AuthResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserRec createUser(UserRec newUserRecDetails) {

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

        return newUserRec;
    }

    public List<User> getUsers(){
        // get the user records from the database
        List<UserRec> userRecList = userDao.findAll();

        // create a result list of user to pass back
        List<User> results = new ArrayList<>();

        // map the user records into user objects
        for(UserRec userRec: userRecList) {
            results.add(new User(userRec));
        }

        // return the user records
        return results;
    }
}
