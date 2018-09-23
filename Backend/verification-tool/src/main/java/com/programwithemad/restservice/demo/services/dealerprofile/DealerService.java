package com.programwithemad.restservice.demo.services.dealerprofile;

import com.programwithemad.restservice.demo.models.DealerProfile;
import com.programwithemad.restservice.demo.models.User;

import java.util.Collection;

public interface DealerService {
    Collection<DealerProfile> getDealers();

    User createDealer(String password, User dealer);

    DealerProfile updateDealerProfile(Integer id, DealerProfile profile, boolean upsert);

    public Collection<DealerProfile> getDealerProfile(Integer id);
}
