package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.models.VerRec;
import com.ALCverificationtool.services.versionService.VersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class versionController {

    @Autowired
    private VersionService service;

    @CrossOrigin
    @PostMapping("/versions")
    public ResponseEntity<CreateVersionsResponse> createVersion(@RequestBody CreateVersionsRequest request) {
        VerRec newVer = this.service.createVer(request.getVersionDetails());

        CreateVersionsResponse response =  new CreateVersionsResponse(newVer);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/versions")
    public ResponseEntity<GetVersionsResponse> getVer() {
        List<VerRec> VerRecResults = this.service.getVer();

        GetVersionsResponse response = new GetVersionsResponse(VerRecResults);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }



}
