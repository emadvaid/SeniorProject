package com.ALCverificationtool.services.fileUploadService;


import com.ALCverificationtool.dao.version.VersionRepository;
import com.ALCverificationtool.models.TranslationResourceRec;
import com.ALCverificationtool.dao.keys.KeysRepository;

import com.ALCverificationtool.models.VerRec;
import com.ALCverificationtool.services.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final KeysRepository keysDao;
    private final VersionRepository verDao;

    @Autowired
    public FileUploadServiceImpl(
            KeysRepository keysDao,
            VersionRepository verDao
    ) {
        this.keysDao = keysDao;
        this.verDao = verDao;
    }

    public String path = System.getProperty("user.dir") + "/src/main/java/com/ALCverificationtool/XMLFiles/";

    @Override
    public void readFile(MultipartFile file, String versionNumber) throws ParserConfigurationException {

        // first get the version record for this version number
        Optional<VerRec> verRecOpt = verDao.findByRawVerNum(versionNumber);

        if (!verRecOpt.isPresent()) {
            throw new ServiceException("unknown version number");
        }

        TranslationResourceRec transResRec = new TranslationResourceRec();

        transResRec.setLanguageVersion(verRecOpt.get().getSafeVersionNumber());
        try {


            File newFile = new File(path + file.getOriginalFilename());
            file.transferTo(newFile);

            //test only
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(newFile);

            visitRecursively(doc, transResRec);
//
//            //Get the file name
//            doc.getDocumentElement().normalize();
//            String fileName = doc.getDocumentElement().getAttribute("id");
//            transResRec.setFileName(fileName);
//
//            //Get the file language
//            doc.getDocumentElement().normalize();
//            String langCode = doc.getDocumentElement().getAttribute("xml:lang");
//            transResRec.setLanguageCode(langCode);
//
//            //Get the file notes
//            NodeList test = doc.getElementsByTagName("note");
//            test.item(0).getTextContent();
//            String fileNotes = test.item(0).getTextContent();
//            transResRec.setFileNotes(fileNotes);
//
//
//            NodeList sectionNodeList = doc.getElementsByTagName("section");
//            for (int i = 0; i < sectionNodeList.getLength(); i++) {
//                Node sectionNode = sectionNodeList.item(i);
//
//                if (sectionNode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element sectionElement = (Element) sectionNode;
//                    //Get the section name
//                    String sectionName = sectionElement.getAttribute("id");
//                    transResRec.setSectionId(sectionName);
//
//                    //Get the section note
//                    String sectionNotes = "";
//                    //Check to see if notes are empty, if they are not, then copy into the sectionNotes
//                    if (sectionElement.getElementsByTagName("note").item(0) != null) {
//                        sectionNotes = sectionElement.getElementsByTagName("note").item(0).getTextContent();
//                    }
//                    transResRec.setSectionNote(sectionNotes);
//
//                    NodeList translationNodeList = ((Element) sectionNode).getElementsByTagName("translation");
//                    for (int j = 0; j < translationNodeList.getLength(); j++) {
//                        Node translationNode = translationNodeList.item(j);
//
//                        if (translationNode.getNodeType() == Node.ELEMENT_NODE) {
//                            Element translationElement = (Element) translationNode;
//                            //Get the translation key name
//                            String translationKey = translationElement.getAttribute("key");
//                            transResRec.setKeyName(translationKey);
//
//                            //Get new key variable
//                            String translationKeyNew = translationElement.getAttribute("new");
//                            boolean keyNew = false;
//                            if (translationKeyNew.equals("true")) {
//                                keyNew = true;
//                            }
//                            transResRec.setKeyNew(keyNew);
//
//                            //Get modified key variable
//                            String translationKeyApproved = translationElement.getAttribute("approved");
//                            boolean keyApproved = false;
//                            if (translationKeyApproved.equals("true")) {
//                                keyApproved = true;
//                            }
//                            transResRec.setKeyApproved(keyApproved);
//
//                            NodeList notesList = ((Element) translationNode).getElementsByTagName("note");
//                            String translationNotes = "";
//                            for (int n = 0; n < notesList.getLength(); n++) {
//                                translationNotes += " " + notesList.item(n).getTextContent();
//                            }
//                            transResRec.setKeyNote(translationNotes);
//
//
//                            //Check to see if the value of the files notes is the same as the key notes or the
//                            //section notes.
//                            if (fileNotes.equals(translationNotes) || fileNotes.equals(sectionNotes)) {
//                                transResRec.setFileNotes("");
//                            }
////                            transResRec.setFileNotes(fileNotes);
////                            transResRec.setSectionNote(sectionNotes);
//
//                            //Get translation variant
//                            String translationVariant = translationElement.getElementsByTagName("variant").item(0).getTextContent();
//                            transResRec.setKeyVariant(translationVariant);
//
//                            keysDao.create(transResRec);
//                        }
//                    }
//                }
//            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void visitRecursively(Node node, TranslationResourceRec keyData) {

        NodeList list = node.getChildNodes();
        String fileName = "";
        String fileNotes = "";
        String language = "";
        String sectionName = "";
        String sectionNotes = "";
        String keyName = "";
        String keyVariant = "";
        String keyNote = "";
        //keyData.setFileNotes("");
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            boolean keyApproved = false;
            boolean keyNew = false;

            if (childNode.getNodeName().equals("resource-base")) {
                //System.out.println("File Name: " + ((Element) childNode).getAttribute("id"));
//                System.out.println("File Name: " + transResRec.getFileName());
                fileName = (((Element) childNode).getAttribute("id"));
                keyData.setFileName(((Element) childNode).getAttribute("id"));
                //System.out.println("Language: " + ((Element) childNode).getAttribute("xml:lang"));
                language = (((Element) childNode).getAttribute("xml:lang"));
                keyData.setLanguageCode(((Element) childNode).getAttribute("xml:lang"));
//                System.out.println("Language: " + transResRec.getLanguageCode());
//                System.out.println("Version Number: " + transResRec.getLanguageVersion());
            }

            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("resource-base")) {
                fileNotes = childNode.getTextContent();
                keyData.setFileNotes(fileNotes);
//                System.out.println("File Notes: " + transResRec.getFileNotes());
            }
            if (keyData.getFileNotes() == null) {
                keyData.setFileNotes("");
            }

            if (childNode.getNodeName().equals("section")) {
                //System.out.println("\tSection Name: " + ((Element) childNode).getAttribute("id"));
                sectionName = ((Element) childNode).getAttribute("id");
                keyData.setSectionId(((Element) childNode).getAttribute("id"));
//                System.out.println("\tSection Name: " + transResRec.getSectionId());
            }
            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("section")) {
                sectionNotes = childNode.getTextContent();
                keyData.setSectionNote(sectionNotes);
//                System.out.println("\tSection Notes: " + transResRec.getSectionNote());
            }

            if (childNode.getNodeName().equals("translation")) {
//                boolean keyApproved = false;
                String translationKeyApproved = ((Element) childNode).getAttribute("approved");
                //if (((Element) childNode).getAttribute("approved").equals("true")) {
                if (translationKeyApproved.equals("true")) {
                    keyApproved = true;
                }
                //System.out.println("\t\tTranslation approved: " + ((Element) childNode).getAttribute("approved"));
                keyData.setKeyApproved(keyApproved);
//                System.out.println("\t\tTranslation approved: " + transResRec.getApproved());

//                boolean keyNew = false;
                String translationKeyNew = ((Element) childNode).getAttribute("new");
                if (translationKeyNew.equals("true")) {
                    keyNew = true;
                }
                keyData.setKeyNew(keyNew);
                //System.out.println("\t\tTranslation new: " + ((Element) childNode).getAttribute("new"));
//                System.out.println("\t\tTranslation new: " + transResRec.getKeyNew());
                //System.out.println("\t\tTranslation key name: " + ((Element) childNode).getAttribute("key"));
                keyName = ((Element) childNode).getAttribute("key");
                keyData.setKeyName(((Element) childNode).getAttribute("key"));
//                System.out.println("\t\tTranslation key name: " + transResRec.getKeyName());
            }
            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("translation")) {
                //System.out.println("\t\tKey Notes: " + childNode.getTextContent());
                keyNote = childNode.getTextContent();
                keyData.setKeyNote(childNode.getTextContent());
//                System.out.println("\t\tKey Notes: " + transResRec.getKeyNote());
            }
            if (childNode.getNodeName().equals("variant") && childNode.getParentNode().getNodeName().equals("translation")) {
                //System.out.println("\t\tTranslation Variant: " + childNode.getTextContent());
                //System.out.println();
//                keyVariant = childNode.getTextContent();
                keyData.setKeyVariant(childNode.getTextContent());
//                System.out.println("\t\tTranslation Variant: " + transResRec.getKeyVariant());
//                transResRec.setFileName(fileName);
//                transResRec.setFileNotes(fileNotes);
//                transResRec.setLanguageCode(language);
//                transResRec.setSectionNote(sectionNotes);
//                transResRec.setSectionId(sectionName);
//                transResRec.setKeyName(keyName);
//                transResRec.setKeyNote(keyNote);
//                transResRec.setKeyNew(keyNew);
//                transResRec.setKeyApproved(keyApproved);
                keysDao.create(keyData);
            }
            visitRecursively(childNode, keyData);
        }
    }
}