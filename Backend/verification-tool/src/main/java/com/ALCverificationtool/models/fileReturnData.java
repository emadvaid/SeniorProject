package com.ALCverificationtool.models;
import org.springframework.web.multipart.MultipartFile;

public class fileReturnData {

    private MultipartFile[] data;
    private String[] paths;

    public MultipartFile[] getData(){
        return data;
    }

    public  String[] getPaths(){
        return paths;
    }
}
