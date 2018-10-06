package com.ALCverificationtool.controllers.keys;

import com.ALCverificationtool.models.*;
import com.ALCverificationtool.services.keysService.KeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.List;
import java.util.Map;


@RestController
public class KeysController {
    @Autowired
    private KeyService service;

    @CrossOrigin
    @PostMapping("/uploadFile")
    //@RequestParam(value="file")MultipartFile[] file
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestBody fileReturnData file) throws ParserConfigurationException {
        //will read the xml file
        UploadFileResponse response = new UploadFileResponse();
        for(int i = 0; i < file.file.length; i++) {
             response = service.readFile(file.file[i]);
        }
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    };
}
