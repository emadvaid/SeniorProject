package com.ALCverificationtool.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", columnDefinition = "BINARY(16)")
    private int id = 0;

    @Column(name = "userName")
    private String userName;

    @Column(name = "keyName")
    private String keyName;

    @Column(name = "variant")
    private String variant;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "language")
    private String language;

    @Column(name = "version")
    private String version;

    public Logs() {
    }

    public Logs(String userName) {
        this.userName = userName;
    }

    public int getID() { return id;}

    public void setId(int id) { this.id = id;}

    public String getUserName() { return userName;}

    public void setUserName(String userName) { this.userName = userName;}

    public String getKeyName() { return keyName;}

    public void setKeyName(String keyName) { this.keyName = keyName;}

    public String getVariant() { return variant; }

    public void setVariant(String variant) { this.variant = variant;}

    public String getFileName() { return fileName; }

    public void setFileName(String fileName) { this.fileName = fileName;}

    public String getLanguage() { return language; }

    public void setLanguage(String language) { this.language = language;}

    public String getVersion() { return version; }

    public void setVersion(String version) { this.version = version; }

}
