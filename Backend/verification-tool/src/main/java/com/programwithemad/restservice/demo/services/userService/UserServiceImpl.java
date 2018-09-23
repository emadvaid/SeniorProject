package com.programwithemad.restservice.demo.services.userService;

import com.programwithemad.restservice.demo.dao.users.UserRepository;
import com.programwithemad.restservice.demo.dao.authentication.UserAuthenticationRepository;
import com.programwithemad.restservice.demo.models.AuthUserResponse;
import com.programwithemad.restservice.demo.models.User;
import com.programwithemad.restservice.demo.models.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository credDao;

    @Autowired
    UserAuthenticationRepository authDao;

    @Override
    public AuthUserResponse authByUsernamePassword(String username, String password) {

        // first make sure the username and password both exist and are non-empty
        if(username==null || username.length()<1 || password==null || password.length()<1){
            throw new UserAuthenticationException("Username and Password required");
        }

        // try to find a user record for the given username
        Optional<User> userOpt = credDao.findById(username);

        // fail if record not found
        if(!userOpt.isPresent()){
            throw new UserAuthenticationException("User not found: username = " + username);
        }

        User user = userOpt.get();

        // fail if the passwords don't match
        if(!password.equals(user.getPassword())){
            throw new UserAuthenticationException("Password not match for user: username = " + username);
        }

        // user is authenticated so create a auth record
        UserAuthentication auth = authDao.save(
                new UserAuthentication(user.getId(), user.getType()));

        // create a temp user object to return
        User tmp = new User(user);
        // make sure to remove the password
        tmp.setPassword(null);
        // return the new users record
        return new AuthUserResponse(tmp, auth);
    }
}
