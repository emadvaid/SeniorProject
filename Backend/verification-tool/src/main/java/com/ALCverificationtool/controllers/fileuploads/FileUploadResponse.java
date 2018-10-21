package com.ALCverificationtool.controllers.fileuploads;

public class FileUploadResponse {
    int statusCode;
    String statusMessage;

    public FileUploadResponse() {
    }

    public FileUploadResponse(int statusCode, String statusMessage) {
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
