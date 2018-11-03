package com.ALCverificationtool.controllers.exportFiles;


import com.ALCverificationtool.services.fileUploadService.FileUploadService;
import com.ALCverificationtool.services.ExportFilesService.ExportFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

@RestController
public class ExportFilesController {
    @Autowired
    private ExportFilesService exportFilesService;
    @Autowired
    private FileUploadService fileUploadService;

    @CrossOrigin
    @PostMapping(value="/exportFile")
    public void exportFile(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) throws TransformerException, ParserConfigurationException {
        //Create empty export folders
        exportFilesService.createFolder(language);

       exportFilesService.createXMLFile(language, versionNumber);
    }
}