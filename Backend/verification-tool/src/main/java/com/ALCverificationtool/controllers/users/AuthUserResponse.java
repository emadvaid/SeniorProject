package com.ALCverificationtool.controllers.users;

import com.ALCverificationtool.models.UserAuthentication;

public class AuthUserResponse {

    private User user;
    private UserAuthentication auth;

    public AuthUserResponse() {
    }

    public AuthUserResponse(User userRec, UserAuthentication auth) {
        this.user = userRec;
        this.auth = auth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User userRec) {
        this.user = userRec;
    }

    public UserAuthentication getAuth() {
        return auth;
    }

    public void setAuth(UserAuthentication auth) {
        this.auth = auth;
    }
}

