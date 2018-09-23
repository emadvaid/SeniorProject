package com.programwithemad.restservice.demo.models;

public class PostCreateDealerResponse {
    private int status;

    public PostCreateDealerResponse(User user) {
        if(user != null) {
            status = 0;
        }
        else {
            status = -1;
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
