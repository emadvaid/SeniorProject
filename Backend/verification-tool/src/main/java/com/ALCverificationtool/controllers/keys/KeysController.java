package com.ALCverificationtool.controllers.keys;

import com.ALCverificationtool.services.keysService.KeyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;


@RestController
public class KeysController {
    @Autowired
    private KeyService service;

    @CrossOrigin
    @PostMapping("/uploadFile")
    //@RequestParam(value="file")MultipartFile[] file
    //,

    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam (value ="file")MultipartFile[] file, @RequestParam (value ="path")String[] paths) throws ParserConfigurationException {
        //will read the xml file
        UploadFileResponse response = new UploadFileResponse();
        for(int i = 0; i < file.length; i++) {
             response = service.readFile(file[i]);
        }

//        int i = service.countNew();
//
//        int j = service.countApproved();

//        for(int i = 0; i < paths.length; i++){
//            System.out.println(paths[i]);
//        }
        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    };

    @CrossOrigin
    @GetMapping("/statistics/new")
    public int getNewKeys() {

        int newKeys = service.countNew();

        System.out.println(newKeys);

        return newKeys;
    }
    @CrossOrigin
    @GetMapping("/statistics/approved")
    public int getApprovedKeys() {

        int approvedKeys = service.countApproved();

        System.out.println(approvedKeys);

        return approvedKeys;
    }
}
