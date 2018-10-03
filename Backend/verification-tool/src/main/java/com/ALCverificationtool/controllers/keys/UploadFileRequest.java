package com.ALCverificationtool.controllers.keys;

public class UploadFileRequest {

    private String fileName;

    public UploadFileRequest() {}

    public UploadFileRequest(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() { return fileName;}

    public void setFileName(String fileName) { this.fileName = fileName;}

}
