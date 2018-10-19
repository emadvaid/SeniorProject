package com.ALCverificationtool.controllers.language;

import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.services.languageService.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.awt.geom.RectangularShape;

@RestController
public class languageController {

    @Autowired
    private LanguageService service;

    @CrossOrigin
    @PostMapping("/lang")
    public ResponseEntity<CreateLanguageResponse> createLang(@RequestBody CreateLanguageRequest request) {
        LangRec newLang = this.service.createLang(request.getLangDetails());

        CreateLanguageResponse response = new CreateLanguageResponse(newLang);

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }
    @CrossOrigin
    @GetMapping("/lang")
    public ResponseEntity<GetLanguageResponse> getLang() {
        List<LangRec> LangRecResults = this.service.getLang();

        GetLanguageResponse response = new GetLanguageResponse(LangRecResults);
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);

    }



}
