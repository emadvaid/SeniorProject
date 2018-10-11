package com.ALCverificationtool.controllers.users;

import com.ALCverificationtool.models.*;
import com.ALCverificationtool.services.ServiceException;
import com.ALCverificationtool.services.userService.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private UserService service;

    @CrossOrigin
    @PostMapping("/authorizeUser")
    public ResponseEntity<AuthUserResponse> authUser(@RequestBody AuthUserRequest request) {

        AuthUserResponse response = service.authByUsernamePassword(request.getUsername(), request.getPassword());

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * Creates a new user.
     *
     * @param request the http request.
     * @return The new user object wrapped in a {@code ResponseEntity.}
     */
    @CrossOrigin
    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        // first create new user
        User newUser = new User(service.createUser(new UserRec(request.getUserDetails())));

        CreateUserResponse response = new CreateUserResponse(newUser);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<GetUsersResponse> getUsers() {

        List<UserRec> userRecResults = this.service.getUsers();

        List<User> userResults = new ArrayList<>();

        for(UserRec rec: userRecResults) {

            if(!rec.getUsername().equals("admin")) {
                userResults.add(new User(rec));
            }

        }

        GetUsersResponse response = new GetUsersResponse(userResults);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/user/{userId}")
    public ResponseEntity<GetUserDetailResponse> getUser(@PathVariable("userId") String userId) {

        Optional<UserRec> userRecResult = this.service.getUserById(userId);

        GetUserDetailResponse response = new GetUserDetailResponse(new User(userRecResult));

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/user/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable("userId") String userId,
                                                         @RequestBody UpdateUserRequest request) {

        Optional<UserRec> userRecResult = this.service.updateUser(userId, request.getUserDetail());

        // make sure the user was in fact updated

        UpdateUserResponse response = new UpdateUserResponse(200, "Success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/user/{userId}/resetpass")
    public ResponseEntity<UpdateUserResponse> resetUserPassword (@PathVariable("userId") String userId) {
        Optional<UserRec> userRecResult = this.service.resetUserPassword(userId);

        // make sure the user was in fact updated
        if(!userRecResult.isPresent() || userRecResult.get().getPassword() != null) {

            return new ResponseEntity<>(new UpdateUserResponse(500, "Failed"),
                    null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UpdateUserResponse response = new UpdateUserResponse(200, "Success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/user/{userId}/activate")
    public ResponseEntity<UpdateUserResponse> activateUser (@PathVariable("userId") String userId) {
        Optional<UserRec> userRecResult = this.service.activateUser(userId);

        // make sure the user was in fact updated
        if(!userRecResult.isPresent() || !userRecResult.get().isActive()) {

            return new ResponseEntity<>(new UpdateUserResponse(500, "Failed"),
                    null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UpdateUserResponse response = new UpdateUserResponse(200, "Success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/user/deactivate/{userId}")
    public ResponseEntity<UpdateUserResponse> deactivateUser (@PathVariable("userId") String userId,
                                                              @RequestBody UpdateUserRequest request) {
        Optional<UserRec> userRecResult = this.service.deactivateUser(userId);

        // make sure the user was in fact updated
        if(!userRecResult.isPresent() || userRecResult.get().isActive()) {

            return new ResponseEntity<>(new UpdateUserResponse(500, "Failed"),
                    null,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        UpdateUserResponse response = new UpdateUserResponse(200, "Success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public void handleUserAuthorizationException(ServiceException e) {
        e.printStackTrace();
    }
}
