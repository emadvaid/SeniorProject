package com.ALCverificationtool.models;

public class CreateUserRequest {
    User userDetails;

    public CreateUserRequest() {
    }

    public CreateUserRequest(User userDetails) {
        this.userDetails = userDetails;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }
}
