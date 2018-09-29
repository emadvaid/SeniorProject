package com.ALCverificationtool.services.userService;

import com.ALCverificationtool.controllers.users.AuthUserResponse;
import com.ALCverificationtool.models.User;

import java.util.List;

public interface UserService {

    AuthUserResponse authByUsernamePassword(String username, String password);

    User createUser(User newUserRecDetails);

    List<User> getUsers();

}

