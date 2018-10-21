package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;

public class CreateVersionsResponse {
    VerRec VersionDetails;

    public CreateVersionsResponse() {
    }

    public CreateVersionsResponse(VerRec versionDetails) {
        this.VersionDetails = versionDetails;
    }

    public VerRec getVersionDetails() {
        return VersionDetails;
    }

    public void setVersionDetails(VerRec versionDetails) {
        this.VersionDetails = versionDetails;
    }
}
