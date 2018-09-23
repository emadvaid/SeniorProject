package com.programwithemad.restservice.demo.models;

public class PostCreateDealerRequest {
    private String password;
    private User dealerUser;

    public PostCreateDealerRequest() {
    }

    public PostCreateDealerRequest(String password, User dealerUser) {
        this.password = password;
        this.dealerUser = dealerUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getDealerUser() {
        return dealerUser;
    }

    public void setDealerUser(User dealerUser) {
        this.dealerUser = dealerUser;
    }
}