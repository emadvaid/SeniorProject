package com.ALCverificationtool.services.userService;

import com.ALCverificationtool.controllers.users.AuthUserResponse;
import com.ALCverificationtool.models.User;
import com.ALCverificationtool.models.UserRec;

import java.util.List;

public interface UserService {

    AuthUserResponse authByUsernamePassword(String username, String password);

    UserRec createUser(UserRec newUserRecDetails);

    List<User> getUsers();

}

