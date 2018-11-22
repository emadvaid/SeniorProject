package com.ALCverificationtool.services.ExportFilesService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.models.TranslationResourceRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.jdom.*;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ExportFilesServiceImpl implements ExportFilesService {
    private final KeysRepository keysDao;

    @Autowired
    public ExportFilesServiceImpl(
            KeysRepository keysDao
    ) {
        this.keysDao = keysDao;
    }


    @Override
    public void createFolder(String language, String versionNumber) {
        //Create export folder on user Desktop
        String homeFolder = System.getProperty("user.home");
        Paths.get(homeFolder, "Desktop", "Export Files/" + versionNumber + "/" + language + "/").toFile().mkdirs();
    }

    @Override
    public void createXMLFile(String language, String versionNumber) throws ParserConfigurationException, TransformerException, IOException {
        String tmp = versionNumber.replaceAll("\\.", "_");
        String tableName = language + "_" + tmp;

        List<TranslationResourceRec> keys = keysDao.getKeys(tableName);
        //Return if table is empty
        if (keys.size() < 1) {
            System.out.println("empty table");
            return;
        }

        String previousFileName = keys.get(0).getFileName();
        String previousNotes = "";
        String previousID = keys.get(0).getFileName();


        int i = 0;
        while (i < keys.size()) {
            String currentFileName = keys.get(i).getFileName();

            Namespace ns = Namespace.getNamespace("http://www.controlj.com/rbase1.0");
            Element root = new Element("resource-base", ns);
            root.setAttribute(new Attribute("id", currentFileName));
            root.setAttribute(new Attribute("version", "1.0"));
            root.setAttribute(new Attribute("lang", language, Namespace.XML_NAMESPACE));

            Document doc = new Document(root);

            //File notes
            String fileNotesDB;
            fileNotesDB = keys.get(i).getFileNotes();
            if (fileNotesDB.length() == 0) {
                fileNotesDB = "";
            }
            if (!previousNotes.equals(fileNotesDB)) {
                root.addContent(new Element("note", root.getNamespace()).setText(fileNotesDB));
                previousNotes = fileNotesDB;
            }

            while (i < keys.size() && keys.get(i).getFileName().equals(previousFileName)) {
                //Create new section root
                //Element section = new Element("section", root.getNamespace());

                //Section ID
                String sectionDB = keys.get(i).getSectionId();
                //Create new section root
                if (!previousID.equals(sectionDB)) {
                    previousID = sectionDB;
                    root.addContent(new Element("section", root.getNamespace()).setAttribute("id", sectionDB));
                }

                //Translation key, approved, and new
                String keyNameDB = keys.get(i).getKeyName();
                boolean keyApprovedDB = keys.get(i).getApproved();
                boolean keyNewDB = keys.get(i).getKeyNew();
                Element translationKey = new Element("translation", root.getNamespace());
                translationKey.setAttribute("key", keyNameDB);
                translationKey.setAttribute("approved", String.valueOf(keyApprovedDB));
                translationKey.setAttribute("new", String.valueOf(keyNewDB));
                //Key variant values
                String keyVariantDB = keys.get(i).getKeyVariant();
                translationKey.addContent(new Element("variant", root.getNamespace()).setText(keyVariantDB));
                //Key note
                String keyNoteDB = keys.get(i).getKeyNote();
                if (!keyNoteDB.equals("")) {
                    translationKey.addContent(new Element("note", root.getNamespace()).setText(keyNoteDB));
                }

                //Copy key to end of current section
                List sections = doc.getRootElement().getChildren();
                Element lastSection = (Element) sections.get(sections.size() - 1);
                lastSection.addContent(translationKey);

                //Save to XML File
                XMLOutputter xmlOutputer = new XMLOutputter();
                String desktopPath = System.getProperty("user.home") + "/Desktop";
                desktopPath = desktopPath + "/Export Files/" + versionNumber + "/" + language + "/" + keys.get(0).getFileName() + ".xml";
                // write the XML File with a nice formatting and alignment
                xmlOutputer.setFormat(Format.getPrettyFormat());
                xmlOutputer.output(doc, new FileWriter(desktopPath));
                i++;
            }
        }
    }
}