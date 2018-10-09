package com.ALCverificationtool.controllers.users;

public class UpdateUserRequest {
    User userDetail;

    public UpdateUserRequest() {
    }

    public UpdateUserRequest(User userDetail) {
        this.userDetail = userDetail;
    }

    public User getUserDetail() {
        return this.userDetail;
    }

    public void setUserDetails(User userDetails) {
        this.userDetail = userDetails;
    }
}
