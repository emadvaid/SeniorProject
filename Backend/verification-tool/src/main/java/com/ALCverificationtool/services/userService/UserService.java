package com.ALCverificationtool.services.userService;

import com.ALCverificationtool.controllers.users.AuthUserResponse;
import com.ALCverificationtool.controllers.users.User;
import com.ALCverificationtool.models.UserRec;

import java.util.List;
import java.util.Optional;

public interface UserService {

    AuthUserResponse authByUsernamePassword(String username, String password);

    Optional<UserRec> createUser(UserRec newUserRecDetails);

    List<UserRec> getUsers();

    Optional<UserRec> getUserById(String userId);

    Optional<UserRec> updateUser(String userId, User userDetails);

    Optional<UserRec> resetUserPassword(String userId);

    Optional<UserRec> activateUser(String userId);

    Optional<UserRec> deactivateUser(String userId);

    Optional<UserRec> getLangByName(String username);
}

