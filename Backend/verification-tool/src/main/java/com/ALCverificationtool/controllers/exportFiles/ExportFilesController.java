package com.ALCverificationtool.controllers.exportFiles;

import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.services.ExportFilesService.ExportFilesService;
import com.ALCverificationtool.services.languageService.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@RestController
public class ExportFilesController {
    @Autowired
    private ExportFilesService exportFilesService;
    @Autowired
    private LanguageService languageService;


    @CrossOrigin
    @PostMapping(value="/exportFile")
    public void exportFile(
            @RequestParam(value="language") String language,
            @RequestParam(value="versionNumber") String versionNumber
    ) throws TransformerException, ParserConfigurationException, IOException {
        //Create empty export folders
        exportFilesService.createFolder(language, versionNumber);

       exportFilesService.createXMLFile(language, versionNumber);
    }

    @CrossOrigin
    @PostMapping(value="/exportAllLanguages")
    public void exportAllLanguages(
            @RequestParam(value="versionNumber") String versionNumber) throws TransformerException, ParserConfigurationException, IOException {
        List<LangRec> languages = this.languageService.getLang();
        for (int i = 0; i < languages.size(); i++) {
            System.out.println(languages.get(i).getLangCode());
            exportFilesService.createFolder(languages.get(i).getLangCode(), versionNumber);
            exportFilesService.createXMLFile(languages.get(i).getLangCode(), versionNumber);
        }
    }
}