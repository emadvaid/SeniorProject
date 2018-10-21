package com.ALCverificationtool.services.fileUploadService;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;

public interface FileUploadService {
    void readFile(MultipartFile file, String versionNumber) throws ParserConfigurationException;
}
