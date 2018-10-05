package com.ALCverificationtool.services.keysService;

import com.ALCverificationtool.models.keys;
import com.ALCverificationtool.models.keysRec;
import com.ALCverificationtool.controllers.keys.UploadFileResponse;

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



    public  String path = System.getProperty("user.dir") + "/src/main/java/com/ALCverificationtool/XMLFiles/";

    @Override
    public UploadFileResponse readFile(MultipartFile file) throws ParserConfigurationException {
        String fileName;

        try {

            File newFile = new File(path + file.getOriginalFilename());
            file.transferTo(newFile);
            //test only
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(newFile);

            NodeList nodeList = doc.getElementsByTagName("section");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(node.getTextContent());
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }




        return new UploadFileResponse();
    }
}