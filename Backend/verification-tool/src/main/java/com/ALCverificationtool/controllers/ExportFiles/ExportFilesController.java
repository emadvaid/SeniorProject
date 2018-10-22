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
//        int lastCurrentFileID = 0;
//        int currentFileID = 0;
//        String previousFileName = keyList.get(0).getFileName();
//        for (int i = 0; i < keyList.size(); i++) {
//            if (previousFileName.equals(keyList.get(i).getFileName())) {
//                currentFileID++;
//                previousFileName = keyList.get(i).getFileName();
//            }
//        }
//        System.out.println(currentFileID);
//        System.out.println(previousFileName);
//        System.out.println(previousFileName.equals(keyList.get(9).getFileName()));
        folderService.createXMLFile();
    }
}
