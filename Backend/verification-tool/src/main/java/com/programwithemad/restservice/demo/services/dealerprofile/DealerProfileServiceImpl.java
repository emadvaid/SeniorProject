package com.programwithemad.restservice.demo.services.dealerprofile;

import com.programwithemad.restservice.demo.dao.DealerProfileRepository;
import com.programwithemad.restservice.demo.dao.users.UserRepository;
import com.programwithemad.restservice.demo.models.DealerProfile;
import com.programwithemad.restservice.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class DealerProfileServiceImpl implements DealerService {

    @Autowired
    UserRepository credDao;

    @Autowired
    DealerProfileRepository dao;

    @Override
    public Collection<DealerProfile> getDealers() {

//        List<DealerProfile> results = new ArrayList<>();
//
//        results.add(new DealerProfile(12, "Dealer12", "12"));
//        results.add(new DealerProfile(13, "Dealer13", "13"));
//        results.add(new DealerProfile(14, "Dealer14", "14"));
//        results.add(new DealerProfile(15, "Dealer15", "15"));

        return dao.findAll();
    }

    @Override
    public Collection<DealerProfile> getDealerProfile(Integer id) {

        return dao.findAll();
    }

    @Override
    public User createDealer(String password, User inputUser) {
        // create a copy of user object
        User tmp = new User(inputUser);

        tmp.setPassword(password);
        tmp.setId(null);

        // create a user record for the dealer
        User user = this.credDao.save(tmp);

        // check that the user was created
        if(user == null || user.getId()==null) {
            // something happened so throw exception
            throw new RuntimeException("Error creating auth cred for dealer");
        }

        //make sure to remove the password
        user.setPassword(null);

        return user;
    }

    @Override
    public DealerProfile updateDealerProfile(Integer id, DealerProfile profile, boolean upsert) {

        if(!upsert) {
            assert(id!=null);
            Optional<DealerProfile> tmp = dao.findById(id);

            if(!tmp.isPresent()) {
                // throw an exception
            }
        }

        // othewise do the upsert/updaTE
        DealerProfile copy = new DealerProfile(profile);
        copy.setId(id);
        DealerProfile result = dao.save(copy);
        return result;
    }
}
