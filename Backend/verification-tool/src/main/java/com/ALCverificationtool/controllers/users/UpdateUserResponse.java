package com.ALCverificationtool.controllers.users;

public class UpdateUserResponse  {
    int statusCode;
    String statusMessage;

    public UpdateUserResponse() {
    }

    public UpdateUserResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
