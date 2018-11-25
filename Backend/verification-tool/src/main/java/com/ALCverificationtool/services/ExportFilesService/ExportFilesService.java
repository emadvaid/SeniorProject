package com.ALCverificationtool.services.ExportFilesService;

import com.ALCverificationtool.models.TranslationResourceRec;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface ExportFilesService {
   // void createFolder();

   // List<TranslationResourceRec> getKeys();

    void createFolder(String language, String versionNumber);

    void createXMLFile(String language, String versionNumber) throws ParserConfigurationException, TransformerException, IOException;
}