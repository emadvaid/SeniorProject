package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;

public class CreateVersionsRequest {
    VerRec versionDetails;

    public CreateVersionsRequest() {
    }

    public CreateVersionsRequest(VerRec versionDetails) {
        this.versionDetails = versionDetails;
    }

    public VerRec getVersionDetails() {
        return versionDetails;
    }

    public void setVersionDetails(VerRec versionDetails) {
        this.versionDetails = versionDetails;
    }
}
