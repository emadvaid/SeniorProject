package com.programwithemad.restservice.demo.controllers.users;

import com.programwithemad.restservice.demo.models.AuthUserRequest;
import com.programwithemad.restservice.demo.models.AuthUserResponse;
import com.programwithemad.restservice.demo.models.User;
import com.programwithemad.restservice.demo.models.UserAuthentication;
import com.programwithemad.restservice.demo.services.userService.UserService;
import com.programwithemad.restservice.demo.services.userService.UserAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    @Autowired
    private UserService service;

    @CrossOrigin
    @PostMapping("/AuthorizeUser")
    public ResponseEntity<AuthUserResponse> authUser(@RequestBody AuthUserRequest request){

        AuthUserResponse response = service.authByUsernamePassword(request.getUsername(), request.getPassword());

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({UserAuthenticationException.class})
    public void handleUserAuthorizationException() {
    }



}
