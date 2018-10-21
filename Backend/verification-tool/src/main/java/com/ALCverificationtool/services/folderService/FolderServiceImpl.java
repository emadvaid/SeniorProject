package com.ALCverificationtool.services.folderService;

import com.ALCverificationtool.dao.keys.KeysRepository;
import com.ALCverificationtool.models.keysRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@Service
public class FolderServiceImpl implements FolderService {
    private final KeysRepository keysDao;

    @Autowired
    public FolderServiceImpl(
            KeysRepository keysDao
    ) {
        this.keysDao = keysDao;
    }


    @Override
    public void createFolder() {
        //Create export folder on user Desktop
        String homeFolder = System.getProperty("user.home");
        Paths.get(homeFolder, "Desktop", "Export Files").toFile().mkdir();
    }

    @Override
    public List<keysRec> getKeys() {
        List<keysRec> results = keysDao.findAll();
        return results;
    }

    @Override
    public void createXMLFile() throws ParserConfigurationException, TransformerException {
        List<keysRec> keys = getKeys();

//        //Save all the files names into a new list
//        ArrayList<String> fileNames = new ArrayList<>();
//        fileNames.add("");
//        for (int i = 0; i < keys.size(); i++) {
//            String temp = keys.get(i).getFileName();
//            if (fileNames.get(i) != temp) {
//                fileNames.add(temp);
//            }
//        }
//        String previousFileName = fileNames.get(0);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.newDocument();

        Element resourceBase = doc.createElement("resource-base");
        doc.appendChild(resourceBase);

//        ListIterator<keysRec> iterator = keys.listIterator();
//       String previousFileName = keys.get(0).getFileName();
//        String currentFileName = "";
//        String previonsSectionName = keys.get(0).getSectionId();
//        String currentSectionName = "";
//        do {
//            currentFileName = iterator.next().getFileName();
//
//            if (currentFileName == iterator.previous().getFileName()) {
//                //XML header
//                resourceBase.setAttribute("id", currentFileName); //File name
//                resourceBase.setAttribute("version", "1.0");
//                resourceBase.setAttribute("xml:lang", "en");
//                resourceBase.setAttribute("xmlns", "http://www.controlj.com/rbase1.0");
//
//                for (int i = 0; i < keys.size(); i++) {
//                    if (currentFileName == previousFileName) {
//                        //File notes
//                        String fileNotesDB = "";
//                        fileNotesDB = keys.get(i).getFileNotes();
//                        Element fileNotes = doc.createElement("note");
//                        fileNotes.appendChild(doc.createTextNode(fileNotesDB));
//                        resourceBase.appendChild(fileNotes);
//
//                        //Section ID
//                        currentSectionName = keys.get(i).getSectionId();
//                        Element section = doc.createElement("section");
//                        section.setAttribute("id", currentSectionName);
//                        resourceBase.appendChild(section);
//
//                        //Section Note
//                        String sectionNoteDB = keys.get(i).getSectionNote();
//                        //sectionNoteDB = keys.get(i).getSectionNote();
//                        if (sectionNoteDB != null) {
//                            Element sectionNote = doc.createElement("note");
//                            sectionNote.appendChild(doc.createTextNode(sectionNoteDB));
//                            section.appendChild(sectionNote);
//                        }
//                        //Translation key, approved, and new
//                        String keyNameDB = "";
//                        keyNameDB = keys.get(i).getKeyName();
//                        boolean keyApprovedDB = false;
//                        boolean keyNewDB = false;
//                        keyApprovedDB = keys.get(i).getApproved();
//                        keyNewDB = keys.get(i).getApproved();
//                        Element translationKey = doc.createElement("translation");
//                        translationKey.setAttribute("key", keyNameDB);
//                        translationKey.setAttribute("approved", String.valueOf(keyApprovedDB));
//                        translationKey.setAttribute("new", String.valueOf(keyNewDB));
//                        section.appendChild(translationKey);
//                        //Key note
//                        String keyNoteDB = "";
//                        keyNoteDB = keys.get(i).getKeyNote();
//                        Element translationNote = doc.createElement("note");
//                        translationNote.appendChild(doc.createTextNode(keyNoteDB));
//                        translationKey.appendChild(translationNote);
//                        //Key variant
//                        String keyVariantDB = "";
//                        keyVariantDB = keys.get(i).getKeyVariant();
//                        Element translationVariant = doc.createElement("variant");
//                        translationVariant.appendChild(doc.createTextNode(keyVariantDB));
//                        translationKey.appendChild(translationVariant);
//                        System.out.println(i);
//                        previousFileName = keys.get(i).getFileName();
//                    }
//                }
//            }
//        } while (iterator.hasNext());


//        //XML header
//        resourceBase.setAttribute("id", "ButtonText"); //File name
//        resourceBase.setAttribute("version", "1.0");
//        resourceBase.setAttribute("xml:lang", "en");
//        resourceBase.setAttribute("xmlns", "http://www.controlj.com/rbase1.0");

        String previousFileName = "";
        String previousNotes = "";
        String previousID = "";
        for (int i = 0; i < keys.size(); i++) {
                //XML header
                resourceBase.setAttribute("id", keys.get(i).getFileName()); //File name
                resourceBase.setAttribute("version", "1.0");
                resourceBase.setAttribute("xml:lang", "en");
                resourceBase.setAttribute("xmlns", "http://www.controlj.com/rbase1.0");



            //File notes
            String fileNotesDB = "";
            fileNotesDB = keys.get(i).getFileNotes();
            Element fileNotes = doc.createElement("note");
            if (!previousNotes.equals(fileNotesDB)) {
                fileNotes.appendChild(doc.createTextNode(fileNotesDB));
                resourceBase.appendChild(fileNotes);
                previousNotes = fileNotesDB;
            }

            //Section ID
            String sectionDB = "";
            sectionDB = keys.get(i).getSectionId();
            Element section = doc.createElement("section");
            if (!previousID.equals(sectionDB)) {
                section.setAttribute("id", sectionDB);
                resourceBase.appendChild(section);
                previousID = sectionDB;

                //Section Note
                String sectionNoteDB = keys.get(i).getSectionNote();
                //sectionNoteDB = keys.get(i).getSectionNote();
                if (sectionNoteDB != null) {
                    Element sectionNote = doc.createElement("note");
                    sectionNote.appendChild(doc.createTextNode(sectionNoteDB));
                    section.appendChild(sectionNote);
                }

                for (int j = i; j < keys.size(); j++) {
                    //Translation key, approved, and new
                    String keyNameDB = "";
                    keyNameDB = keys.get(i).getKeyName();
                    boolean keyApprovedDB = false;
                    boolean keyNewDB = false;
                    keyApprovedDB = keys.get(i).getApproved();
                    keyNewDB = keys.get(i).getApproved();
                    Element translationKey = doc.createElement("translation");
                    translationKey.setAttribute("key", keyNameDB);
                    translationKey.setAttribute("approved", String.valueOf(keyApprovedDB));
                    translationKey.setAttribute("new", String.valueOf(keyNewDB));
                    section.appendChild(translationKey);
                    //Key note
                    String keyNoteDB = "";
                    keyNoteDB = keys.get(i).getKeyNote();
                    Element translationNote = doc.createElement("note");
                    if (!keyNoteDB.equals("")) {
                        translationNote.appendChild(doc.createTextNode(keyNoteDB));
                        translationKey.appendChild(translationNote);
                    }
                    //Key variant
                    String keyVariantDB = "";
                    keyVariantDB = keys.get(i).getKeyVariant();
                    Element translationVariant = doc.createElement("variant");
                    translationVariant.appendChild(doc.createTextNode(keyVariantDB));
                    translationKey.appendChild(translationVariant);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("/Users/benja/Desktop/Export Files/test.xml"));

            transformer.transform(source, result);

            System.out.println("File created");



//            //Section Note
//            String sectionNoteDB = keys.get(i).getSectionNote();
//            //sectionNoteDB = keys.get(i).getSectionNote();
//            if (sectionNoteDB != null) {
//                Element sectionNote = doc.createElement("note");
//                sectionNote.appendChild(doc.createTextNode(sectionNoteDB));
//                section.appendChild(sectionNote);
//            }

//            //Translation key, approved, and new
//            String keyNameDB = "";
//            keyNameDB = keys.get(i).getKeyName();
//            boolean keyApprovedDB = false;
//            boolean keyNewDB = false;
//            keyApprovedDB = keys.get(i).getApproved();
//            keyNewDB = keys.get(i).getApproved();
//            Element translationKey = doc.createElement("translation");
//            translationKey.setAttribute("key", keyNameDB);
//            translationKey.setAttribute("approved", String.valueOf(keyApprovedDB));
//            translationKey.setAttribute("new", String.valueOf(keyNewDB));
//            section.appendChild(translationKey);
//            //Key note
//            String keyNoteDB = "";
//            keyNoteDB = keys.get(i).getKeyNote();
//            Element translationNote = doc.createElement("note");
//            translationNote.appendChild(doc.createTextNode(keyNoteDB));
//            translationKey.appendChild(translationNote);
//            //Key variant
//            String keyVariantDB = "";
//            keyVariantDB = keys.get(i).getKeyVariant();
//            Element translationVariant = doc.createElement("variant");
//            translationVariant.appendChild(doc.createTextNode(keyVariantDB));
//            translationKey.appendChild(translationVariant);
        }

//        TransformerFactory transformerFactory = TransformerFactory.newInstance();
//        Transformer transformer = transformerFactory.newTransformer();
//        DOMSource source = new DOMSource(doc);
//
//        StreamResult result = new StreamResult(new File("/Users/benja/Desktop/Export Files/test.xml"));
//
//        transformer.transform(source, result);
//
//        System.out.println("File created");
    }
}
