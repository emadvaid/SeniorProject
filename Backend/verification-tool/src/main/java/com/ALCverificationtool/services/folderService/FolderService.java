package com.ALCverificationtool.services.folderService;

import com.ALCverificationtool.models.keysRec;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.List;

public interface FolderService {
    void createFolder();

    List<keysRec> getKeys();

    void createXMLFile() throws ParserConfigurationException, TransformerException;
}
