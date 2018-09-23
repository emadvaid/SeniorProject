package com.programwithemad.restservice.demo.services.userService;

import com.programwithemad.restservice.demo.models.AuthUserResponse;
import com.programwithemad.restservice.demo.models.User;

public interface UserService {

    AuthUserResponse authByUsernamePassword(String username, String password);


}

