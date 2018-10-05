package com.ALCverificationtool.services.keysService;

import com.ALCverificationtool.models.keys;
import com.ALCverificationtool.controllers.keys.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public interface KeyService {
    UploadFileResponse readFile(MultipartFile file) throws ParserConfigurationException;
}
