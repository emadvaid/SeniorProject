package com.programwithemad.restservice.demo.controllers;

import com.programwithemad.restservice.demo.models.*;
import com.programwithemad.restservice.demo.services.dealerprofile.DealerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DealerController {

    @Autowired
    private DealerService service;


    /**
     * Controller method that returns all of the dealer profiles
     * @return
     */
    @CrossOrigin
    @GetMapping("/dealerProfiles")
    public GetDealerProfilesResponse getDealers()
    {
        return new GetDealerProfilesResponse(service.getDealers());

    }

    /**
     * Controller method to create a new dealer
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping("/dealers")
    public PostCreateDealerResponse createDealer(@RequestBody PostCreateDealerRequest request)
    {
        return new PostCreateDealerResponse(
                service.createDealer(request.getPassword(), request.getDealerUser())
        );
    }

//    /**
//     * Controller method to update an existing dealer profile
//     * @param request
//     * @return
//     */
//    @CrossOrigin
//    @PutMapping("/dealerProfile/{id}")
//    public PutUpdateDealerProfileResponse updateDealerProfile(
//            @PathVariable(value="id") Integer id,
//            @RequestBody PostCreateDealerRequest request)
//    {
//        return new PutUpdateDealerProfileRequest(service.updateDealerProfile(id, request.getProfile(), true));
//    }
//    @CrossOrigin
//    @GetMapping("/dealerProfile/{id}")
//    public String index()
//    {
//        return "Hello World";
//    }
}

