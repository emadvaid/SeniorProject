package com.ALCverificationtool.controllers.version;

import com.ALCverificationtool.controllers.BasicResponse;
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

    /**
     * This controller method creates a new Version
     * @param request
     * @return
     */
    @CrossOrigin
    @PostMapping("/versions")
    public ResponseEntity<CreateVersionsResponse> createVersion(@RequestBody CreateVersionsRequest request) {
        VerRec newVer = this.service.createVer(request.getVersionDetails());
        CreateVersionsResponse response =  new CreateVersionsResponse(newVer);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * This controller method deletes a version
     * @return
     */
    @CrossOrigin
    @DeleteMapping("/versions/{versionNumber}")
    public ResponseEntity<BasicResponse> deleteVersion(
            @PathVariable("versionNumber") String versionNumber) {

        // we call the service method, and if no Exception, then we consider it a success
        this.service.deleteVersion(versionNumber);

        BasicResponse response = new BasicResponse(200, "success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * This controller method deletes a particular language for te given version
     * @param versionNumber
     * @param langCode
     * @return
     */
    @CrossOrigin
    @DeleteMapping("/versions/{langCode}/{versionNumber}")
    public ResponseEntity<BasicResponse> deleteVersionLanguage(
            @PathVariable("versionNumber") String versionNumber,
            @PathVariable String langCode) {

        // we call the service method, and if no Exception, then we consider it a success
        this.service.deleteVersionLanguage(langCode, versionNumber);

        BasicResponse response = new BasicResponse(200,"success");

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * This controller method gets a list of all the available versions
     * @return
     */
    @CrossOrigin
    @GetMapping("/versions")
    public ResponseEntity<GetVersionsResponse> getVer() {
        List<VerRec> VerRecResults = this.service.getVer();

        GetVersionsResponse response = new GetVersionsResponse(VerRecResults);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    /**
     * This controller method gets a list of all the available versions
     * @return
     */
    @CrossOrigin
    @GetMapping("/versions/{langCode}")
    public ResponseEntity<List<VerRec>> getByLangCode(
            @PathVariable("langCode") String langCode
    ) {
        List<VerRec> VerRecResults = this.service.getByLangCode(langCode);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(VerRecResults, headers, HttpStatus.OK);
    }
}