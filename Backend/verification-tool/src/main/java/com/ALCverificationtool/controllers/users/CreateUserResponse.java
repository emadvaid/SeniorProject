package com.ALCverificationtool.controllers.users;

public class CreateUserResponse {
    User userDetails;

    public CreateUserResponse() {
    }

    public CreateUserResponse(User userDetails) {
        this.userDetails = userDetails;
    }

    public User getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(User userDetails) {
        this.userDetails = userDetails;
    }
}
