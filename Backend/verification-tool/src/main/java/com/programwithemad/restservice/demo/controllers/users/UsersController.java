package com.programwithemad.restservice.demo.controllers.users;

import com.programwithemad.restservice.demo.models.CreateUserResponse;
import com.programwithemad.restservice.demo.models.*;
import com.programwithemad.restservice.demo.services.userService.UserService;
import com.programwithemad.restservice.demo.services.userService.UserAuthenticationException;
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
    @ExceptionHandler({UserAuthenticationException.class})
    public void handleUserAuthorizationException() {
    }
}
