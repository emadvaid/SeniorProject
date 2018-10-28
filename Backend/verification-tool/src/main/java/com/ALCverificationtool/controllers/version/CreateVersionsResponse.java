package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;

public class CreateVersionsResponse {
    VerRec versionDetails;

    public CreateVersionsResponse() {
    }

    public CreateVersionsResponse(VerRec versionDetails) {
        this.versionDetails = versionDetails;
    }

    public VerRec getVersionDetails() {
        return versionDetails;
    }

    public void setVersionDetails(VerRec versionDetails) {
        this.versionDetails = versionDetails;
    }
}
