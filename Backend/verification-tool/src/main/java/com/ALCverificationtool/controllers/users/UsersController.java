package com.ALCverificationtool.controllers.users;

import com.ALCverificationtool.models.*;
import com.ALCverificationtool.services.ServiceException;
import com.ALCverificationtool.services.userService.UserAuthenticationException;
import com.ALCverificationtool.services.userService.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UserService service;

    @CrossOrigin
    @PostMapping("/authorizeUser")
    public ResponseEntity<AuthUserResponse> authUser(@RequestBody AuthUserRequest request){

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
        User newUser = service.createUser(request.getUserDetails());

        CreateUserResponse response = new CreateUserResponse(newUser);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<GetUsersResponse> getUsers(){

        List<User> userResults = this.service.getUsers();

        GetUsersResponse response = new GetUsersResponse(userResults);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public void handleUserAuthorizationException() {
    }
}
