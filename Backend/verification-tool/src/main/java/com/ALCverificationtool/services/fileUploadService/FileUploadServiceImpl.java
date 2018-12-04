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
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            boolean keyApproved = false;
            boolean keyNew = false;

            if (childNode.getNodeName().equals("resource-base")) {
                keyData.setFileName(((Element) childNode).getAttribute("id"));
                keyData.setLanguageCode(((Element) childNode).getAttribute("xml:lang"));
            }

            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("resource-base")) {
                fileNotes = childNode.getTextContent();
                keyData.setFileNotes(fileNotes);
            }
            if (keyData.getFileNotes() == null) {
                keyData.setFileNotes("");
            }

            // Get section name
            if (childNode.getNodeName().equals("section")) {
                sectionName = ((Element) childNode).getAttribute("id");
                keyData.setSectionId(((Element) childNode).getAttribute("id"));
            }
            // Get section notes
            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("section")) {
                sectionNotes = childNode.getTextContent();
                keyData.setSectionNote(sectionNotes);
            }
            if (keyData.getSectionNote() == null) {
                keyData.setSectionNote("");
            }

            // Get translation key attributes
            if (childNode.getNodeName().equals("translation")) {
                String translationKeyApproved = ((Element) childNode).getAttribute("approved");
                if (translationKeyApproved.equals("true")) {
                    keyApproved = true;
                }
                keyData.setKeyApproved(keyApproved);

                String translationKeyNew = ((Element) childNode).getAttribute("new");
                if (translationKeyNew.equals("true")) {
                    keyNew = true;
                }
                keyData.setKeyNew(keyNew);
                keyData.setKeyName(((Element) childNode).getAttribute("key"));
            }
            if (childNode.getNodeName().equals("note") && childNode.getParentNode().getNodeName().equals("translation")) {
                keyData.setKeyNote(childNode.getTextContent());
            }
            if (keyData.getKeyNote() == null) {
                keyData.setKeyNote("");
            }
            if (childNode.getNodeName().equals("variant") && childNode.getParentNode().getNodeName().equals("translation")) {
                keyData.setKeyVariant(childNode.getTextContent());
                keysDao.create(keyData);
                keyData.setKeyNote("");
            }
            visitRecursively(childNode, keyData);
        }
    }
}