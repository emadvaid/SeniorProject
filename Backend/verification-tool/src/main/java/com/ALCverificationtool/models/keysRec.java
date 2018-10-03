package com.ALCverificationtool.models;

import javax.persistence.*;

@Entity
@Table(name="keys")
public class keysRec {

    // The key id field
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long keyId = null;

    @Column(unique = true, nullable = false)
    private String keyName;

    private boolean keyModified;
    private boolean keyNew;
    private String keyVariant;
    private String keyNote;
    private String sectionId;
    private String sectionNote;
    private String fileName;
    private String fileNotes;

    public keysRec() {}

    public keysRec(keys original) {
        this.keyName = original.getKeyName();
        this.keyModified = original.getKeyModified();
        this.keyNew = original.getKeyNew();
        this.keyVariant = original.getKeyVariant();
        this.keyNote = original.getKeyNote();
        this.sectionId = original.getSectionId();
        this.sectionNote = original.getSectionNote();
        this.fileName = original.getFileName();
        this.fileNotes = original.getFileNotes();
    }

    public keysRec(keysRec original) {
        this.keyName = original.keyName;
        this.keyModified = original.keyModified;
        this.keyNew = original.keyNew;
        this.keyVariant = original.keyVariant;
        this.keyNote = original.keyNote;
        this.sectionId = original.sectionId;
        this.sectionNote = original.sectionNote;
        this.fileName = original.fileName;
        this.fileNotes = original.fileNotes;
    }

    public keysRec(Long keyId, String keyName, boolean keyModified, boolean keyNew,
                String keyVariant, String keyNote, String sectionId,
                String sectionNote, String fileName, String fileNotes){
        this.keyId = keyId;
        this.keyName = keyName;
        this.keyModified = keyModified;
        this.keyNew = keyNew;
        this.keyVariant = keyVariant;
        this.keyNote = keyNote;
        this.sectionId = sectionId;
        this.sectionNote = sectionNote;
        this.fileName = fileName;
        this.fileNotes = fileNotes;
    }

    public Long getKeyId() {return keyId;}
    public void setKeyId(Long keyId) {this.keyId = keyId;}

    public String getKeyName() {return keyName;}
    public void setKeyName(String keyName) {this.keyName = keyName;}

    public boolean getKeyModified() {return keyModified;}
    public void setKeyModified(boolean keymodified) {this.keyModified = keymodified;}

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
}
