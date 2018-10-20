package com.ALCverificationtool.controllers.ExportFiles;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.services.folderService.FolderService;
import com.ALCverificationtool.services.keysService.KeyService;
import com.ALCverificationtool.models.keysRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
public class ExportFilesController {
    @Autowired
    private FolderService folderService;
    @Autowired
    private KeyService keyService;

    @CrossOrigin
    @GetMapping("/exportFile")
    public void exportFile() throws TransformerException, ParserConfigurationException {
        //Create empty export folder
        folderService.createFolder();

        //Get keys
        List<keysRec> keyList = this.folderService.getKeys();

        folderService.createXMLFile();
        for (int i = 0; i < keyList.size(); i++) {

            System.out.println(keyList.get(i).getKeyName());
        }

    }
}
