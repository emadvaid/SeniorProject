package com.programwithemad.restservice.demo.models;

import java.util.Collection;
import java.util.List;

public class GetDealerProfilesResponse {
    private Collection<DealerProfile> profiles;

    public GetDealerProfilesResponse() {}

    public GetDealerProfilesResponse(Collection<DealerProfile> profiles) {
        this.profiles = profiles;
    }

    public Collection<DealerProfile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Collection<DealerProfile> profiles) {
        this.profiles = profiles;
    }
}
