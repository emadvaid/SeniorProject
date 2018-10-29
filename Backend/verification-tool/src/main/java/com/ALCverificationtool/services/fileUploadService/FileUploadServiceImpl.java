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
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
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

    public  String path = System.getProperty("user.dir") + "/src/main/java/com/ALCverificationtool/XMLFiles/";

    @Override
    public void readFile(MultipartFile file, String versionNumber) throws ParserConfigurationException {

        // first get the version record for this version number
        Optional<VerRec> verRecOpt = verDao.findByRawVerNum(versionNumber);

        if(!verRecOpt.isPresent()) {
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

            //Get the file name
            doc.getDocumentElement().normalize();
            String fileName = doc.getDocumentElement().getAttribute("id");
            transResRec.setFileName(fileName);

            //Get the file language
            doc.getDocumentElement().normalize();
            String langCode = doc.getDocumentElement().getAttribute("xml:lang");
            transResRec.setLanguageCode(langCode);

            //Get the file notes
            NodeList test = doc.getElementsByTagName("note");
            test.item(0).getTextContent();
            String fileNotes = test.item(0).getTextContent();
            transResRec.setFileNotes(fileNotes);


            NodeList sectionNodeList = doc.getElementsByTagName("section");
            for (int i = 0; i < sectionNodeList.getLength(); i++) {
                Node sectionNode = sectionNodeList.item(i);

                if (sectionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element sectionElement = (Element) sectionNode;
                    //Get the section name
                    String sectionName = sectionElement.getAttribute("id");
                    transResRec.setSectionId(sectionName);

                    //Get the section note
                    String sectionNotes = "";
                    //Check to see if notes are empty, if they are not, then copy into the sectionNotes
                    if (sectionElement.getElementsByTagName("note").item(0) != null) {
                        sectionNotes = sectionElement.getElementsByTagName("note").item(0).getTextContent();
                    }
                    transResRec.setSectionNote(sectionNotes);

                    NodeList translationNodeList = ((Element) sectionNode).getElementsByTagName("translation");
                    for (int j = 0; j < translationNodeList.getLength(); j++) {
                        Node translationNode = translationNodeList.item(j);

                        if (translationNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element translationElement = (Element) translationNode;
                            //Get the translation key name
                            String translationKey = translationElement.getAttribute("key");
                            transResRec.setKeyName(translationKey);

                            //Get new key variable
                            String translationKeyNew = translationElement.getAttribute("new");
                            boolean keyNew = false;
                            if (translationKeyNew.equals("true")) {
                                keyNew = true;
                            }
                            transResRec.setKeyNew(keyNew);

                            //Get modified key variable
                            String translationKeyApproved = translationElement.getAttribute("approved");
                            boolean keyApproved = false;
                            if (translationKeyApproved.equals("true")) {
                                keyApproved = true;
                            }
                            transResRec.setKeyApproved(keyApproved);

                            NodeList notesList = ((Element) translationNode).getElementsByTagName("note");
                            String translationNotes = "";
                            for (int n = 0; n < notesList.getLength(); n++) {
                                translationNotes += " " + notesList.item(n).getTextContent();
                            }
                            transResRec.setKeyNote(translationNotes);


                            //Check to see if the value of the files notes is the same as the key notes or the
                            //section notes.
                            if (fileNotes.equals(translationNotes) || fileNotes.equals(sectionNotes)) {
                                transResRec.setFileNotes("");
                            }
//                            transResRec.setFileNotes(fileNotes);
//                            transResRec.setSectionNote(sectionNotes);

                            //Get translation variant
                            String translationVariant = translationElement.getElementsByTagName("variant").item(0).getTextContent();
                            transResRec.setKeyVariant(translationVariant);

                            keysDao.create(transResRec);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}