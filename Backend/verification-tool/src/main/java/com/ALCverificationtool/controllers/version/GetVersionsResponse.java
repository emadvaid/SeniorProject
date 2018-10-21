package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;

import java.util.List;

public class GetVersionsResponse {

    private List<VerRec> VersionDetails;

    public GetVersionsResponse() {
    }

    public GetVersionsResponse(List<VerRec> versionDetails) {
        VersionDetails = versionDetails;
    }

    public List<VerRec> getVersionDetails() {
        return VersionDetails;
    }

    public void setVersionDetails(List<VerRec> versionDetails) {
        VersionDetails = versionDetails;
    }
}
