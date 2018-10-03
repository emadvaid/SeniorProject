package com.ALCverificationtool.controllers.keys;

import com.ALCverificationtool.models.keys;

public class UploadFileResponse {
    private keys keys;

    public UploadFileResponse() {}

    public UploadFileResponse(keys key) {
        this.keys = key;
    }

    public keys getKeys() { return keys;}
    public void setKeys(keys keys) { this.keys = keys;}
}
