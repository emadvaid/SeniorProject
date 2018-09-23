package com.programwithemad.restservice.demo.models;

public class AuthUserResponse {

    private User user;
    private UserAuthentication auth;

    public AuthUserResponse() {
    }

    public AuthUserResponse(User user, UserAuthentication auth) {
        this.user = user;
        this.auth = auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAuthentication getAuth() {
        return auth;
    }

    public void setAuth(UserAuthentication auth) {
        this.auth = auth;
    }
}

