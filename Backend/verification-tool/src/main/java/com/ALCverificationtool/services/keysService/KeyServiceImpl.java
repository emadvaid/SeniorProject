package com.ALCverificationtool.services.keysService;


import com.ALCverificationtool.models.keysRec;
import com.ALCverificationtool.controllers.keys.UploadFileResponse;
import com.ALCverificationtool.dao.keys.KeysRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

@Service
public class KeyServiceImpl implements KeyService {
    private final KeysRepository keysDao;

    @Autowired
    public KeyServiceImpl (KeysRepository keysDao) {
        this.keysDao = keysDao;
    }
    public  String path = System.getProperty("user.dir") + "/src/main/java/com/ALCverificationtool/XMLFiles/";

    @Override
    public UploadFileResponse readFile(MultipartFile file) throws ParserConfigurationException {
        keysRec keysRec = new keysRec();

        try {

            File newFile = new File(path + file.getOriginalFilename());
            file.transferTo(newFile);
            //test only
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(newFile);

            //Get the file name
            doc.getDocumentElement().normalize();
            String fileName = doc.getDocumentElement().getAttribute("id");
            System.out.println("File Name: " + fileName);
            keysRec.setFileName(fileName);

            //Get the file notes
            NodeList test = doc.getElementsByTagName("note");
            test.item(0).getTextContent();
            String fileNotes = test.item(0).getTextContent().toString();
            System.out.println("File Notes: " + fileNotes);
            keysRec.setFileNotes(fileNotes);


            NodeList sectionNodeList = doc.getElementsByTagName("section");
            for (int i = 0; i < sectionNodeList.getLength(); i++) {
                Node sectionNode = sectionNodeList.item(i);

                if (sectionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sectionElement = (Element) sectionNode;
                    //Get the section name
                    String sectionName = sectionElement.getAttribute("id");
                    System.out.println("Section Name: " + sectionName);
                    keysRec.setSectionId(sectionName);

                    //Get the section note
                    String sectionNotes = sectionElement.getElementsByTagName("note").item(0).getTextContent();
                    System.out.println("Section Notes: " + sectionNotes);
                    keysRec.setKeyNote(sectionNotes);

                    NodeList translationNodeList = ((Element) sectionNode).getElementsByTagName("translation");
                    for (int j = 0; j < translationNodeList.getLength(); j++) {
                        Node translationNode = translationNodeList.item(j);

                        if (translationNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element translationElement = (Element) translationNode;
                            //Get the translation key name
                            String translationKey = translationElement.getAttribute("key");
                            System.out.println("Translation Key: " + translationKey);
                            keysRec.setKeyName(translationKey);

                            //Get new key variable
                            String translationKeyNew = translationElement.getAttribute("new");
                            boolean keyNew = false;
                            if (translationKeyNew.equals("true")) {
                                keyNew = true;
                            }
                            System.out.println("Translation Key New: " + keyNew);
                            keysRec.setKeyNew(keyNew);

                            //Get modified key variable
                            String translationKeyModified = translationElement.getAttribute("modified");
                            boolean keyModified = false;
                            if (translationKeyModified.equals("true")) {
                                keyModified = true;
                            }
                            System.out.println("Translation Key Modified: " + keyModified);
                            keysRec.setKeyModified(keyModified);

                            //Get translation key notes
                            /*
                            String translationNote = translationElement.getElementsByTagName("note").item(0).getTextContent();
                            System.out.println("Translation Note: " + translationNote);
                            keysRec.setKeyNote(translationNote);
                            */
                            NodeList notesList = ((Element) translationNode).getElementsByTagName("note");
                            String translationNotes = "";
                            for (int n = 0; n < notesList.getLength(); n++) {
                                translationNotes += notesList.item(n).getTextContent();
                            }
                            System.out.println("Notes: " + translationNotes);
                            keysRec.setKeyNote(translationNotes);

                            //Get translation variant
                            String translationVariant = translationElement.getElementsByTagName("variant").item(0).getTextContent();
                            System.out.println("Translation Variant: " + translationVariant);
                            keysRec.setKeyVariant(translationVariant);

                            keysDao.save(keysRec);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        return new UploadFileResponse();
    }
}