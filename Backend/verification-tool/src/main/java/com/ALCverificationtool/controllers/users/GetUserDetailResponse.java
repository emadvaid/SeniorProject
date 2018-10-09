package com.ALCverificationtool.controllers.users;

public class GetUserDetailResponse {

    User userDetail;

    public GetUserDetailResponse() {
    }

    public GetUserDetailResponse(User userDetail) {
        this.userDetail = userDetail;
    }

    public User getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(User userDetail) {
        this.userDetail = userDetail;
    }
}
