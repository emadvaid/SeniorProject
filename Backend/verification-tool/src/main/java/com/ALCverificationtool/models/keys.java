package com.ALCverificationtool.models;

public class keys {

    private Long keyId;
    private String languageCode;
    private String LanguageVersion;
    private String keyName;
    private boolean keyApproved;
    private boolean keyNew;
    private String keyVariant;
    private String keyNote;
    private String sectionId;
    private String sectionNote;
    private String fileName;
    private String fileNotes;
    private String folderPath;

    public keys() {
    }

    public keys(Long keyId, String languageCode, String languageVersion, String keyName,
                boolean keyApproved, boolean keyNew, String keyVariant, String keyNote,
                String sectionId, String sectionNote, String fileName, String fileNotes,
                String folderPath) {
        this.keyId = keyId;
        this.languageCode = languageCode;
        LanguageVersion = languageVersion;
        this.keyName = keyName;
        this.keyApproved = keyApproved;
        this.keyNew = keyNew;
        this.keyVariant = keyVariant;
        this.keyNote = keyNote;
        this.sectionId = sectionId;
        this.sectionNote = sectionNote;
        this.fileName = fileName;
        this.fileNotes = fileNotes;
        this.folderPath = folderPath;
    }

    public keys(TranslationResourceRec original) {
        this.keyName = original.getKeyName();
        this.keyApproved = original.getKeyApproved();
        this.keyNew = original.getKeyNew();
        this.keyVariant = original.getKeyVariant();
        this.keyNote = original.getKeyNote();
        this.sectionId = original.getSectionId();
        this.sectionNote = original.getSectionNote();
        this.fileName = original.getFileName();
        this.fileNotes = original.getFileNotes();
        this.folderPath = original.getFolderPath();

    }


    public Long getKeyId() {return keyId;}
    public void setKeyId(Long keyId) {this.keyId = keyId;}

    public String getKeyName() {return keyName;}
    public void setKeyName(String keyName) {this.keyName = keyName;}

    public boolean getKeyApproved() {return keyApproved;}
    public void setKeyApproved(boolean keyApproved) {this.keyApproved = keyApproved;}

    public boolean getKeyNew() {return keyNew;}
    public void setKeyNed(boolean keyNew) {this.keyNew = keyNew;}

    public String getKeyVariant() {return keyVariant;}
    public void setKeyVariant(String keyVariant) {this.keyVariant = keyVariant;}

    public String getKeyNote() {return keyNote;}
    public void setKeyNote(String keyNote) {this.keyNote = keyNote;}

    public String getSectionId() {return sectionId;}
    public void setSectionId(String sectionId) {this.sectionId = sectionId;}

    public String getSectionNote() {return sectionNote;}
    public void setSectionNote(String sectionNote) {this.sectionNote = sectionNote;}

    public String getFileName() {return fileName;}
    public void setFileName(String fileName) {this.fileName = fileName;}

    public String getFileNotes() {return fileNotes;}
    public void setFileNotes(String fileNotes) {this.fileNotes = fileNotes;}

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageVersion() {
        return LanguageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        LanguageVersion = languageVersion;
    }

    public boolean isKeyApproved() {
        return keyApproved;
    }

    public boolean isKeyNew() {
        return keyNew;
    }

    public void setKeyNew(boolean keyNew) {
        this.keyNew = keyNew;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
}
