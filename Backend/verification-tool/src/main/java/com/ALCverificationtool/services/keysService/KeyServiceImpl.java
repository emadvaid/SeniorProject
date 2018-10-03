package com.ALCverificationtool.services.keysService;

import com.ALCverificationtool.models.keys;
import com.ALCverificationtool.models.keysRec;
import com.ALCverificationtool.controllers.keys.UploadFileResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

@Service
public class KeyServiceImpl implements KeyService {
    @Override
    public UploadFileResponse readFile(String fileName) throws ParserConfigurationException {
        try {
            //test only
            fileName = "/Users/benja/Desktop/ButtonText.xml";

            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

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