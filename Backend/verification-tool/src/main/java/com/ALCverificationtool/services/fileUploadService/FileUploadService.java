package com.ALCverificationtool.services.fileUploadService;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.ParserConfigurationException;

public interface FileUploadService {
    void readFile(MultipartFile file, String versionNumber) throws ParserConfigurationException;
}
