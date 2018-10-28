package com.ALCverificationtool.controllers.fileuploads;

import com.ALCverificationtool.services.fileUploadService.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;


@RestController
public class FileUploadController {

    private final FileUploadService service;

    @Autowired
    FileUploadController(FileUploadService service) {
        this.service = service;
    }

    @CrossOrigin
    @PostMapping(value = "/uploadFile", consumes = "multipart/form-data")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @RequestParam (value ="file")MultipartFile[] files,
            @RequestParam (value="versionNumber") String versionNumber
    ) throws ParserConfigurationException {

        System.out.println(files);
        // Loop over all the files in the request
        for (MultipartFile file : files) {
            service.readFile(file, versionNumber);
        }

        HttpHeaders headers = new HttpHeaders();

        return new ResponseEntity<>(new FileUploadResponse(200, "Success"),
                headers, HttpStatus.OK);
    }

    @ExceptionHandler({Exception.class})
    public void resolveException(Exception e) {
        e.printStackTrace();
    }
}
