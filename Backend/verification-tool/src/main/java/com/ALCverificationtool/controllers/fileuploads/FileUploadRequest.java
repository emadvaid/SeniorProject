package com.ALCverificationtool.controllers.fileuploads;

public class FileUploadRequest {

    private String fileVersionNumber;

    public FileUploadRequest() {
    }

    public FileUploadRequest(String fileVersionNumber) {
        this.fileVersionNumber = fileVersionNumber;
    }

    public String getFileVersionNumber() {
        return fileVersionNumber;
    }

    public void setFileVersionNumber(String fileVersionNumber) {
        this.fileVersionNumber = fileVersionNumber;
    }
}
