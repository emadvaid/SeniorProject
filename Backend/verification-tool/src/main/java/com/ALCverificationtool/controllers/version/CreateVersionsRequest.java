package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;

public class CreateVersionsRequest {
    VerRec VersionDetails;

    public CreateVersionsRequest() {
    }

    public CreateVersionsRequest(VerRec versionDetails) {
        this.VersionDetails = versionDetails;
    }

    public VerRec getVersionDetails() {
        return VersionDetails;
    }

    public void setVersionDetails(VerRec versionDetails) {
        this.VersionDetails = versionDetails;
    }
}
