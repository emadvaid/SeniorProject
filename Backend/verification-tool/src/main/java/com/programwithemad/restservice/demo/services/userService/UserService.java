package com.programwithemad.restservice.demo.services.userService;

import com.programwithemad.restservice.demo.models.AuthUserResponse;
import com.programwithemad.restservice.demo.models.User;

import java.util.List;

public interface UserService {

    AuthUserResponse authByUsernamePassword(String username, String password);

    User createUser(User newUserRecDetails);

    List<User> getUsers();

}

