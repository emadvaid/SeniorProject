package com.ALCverificationtool.models;


import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "langRec")
public class LangRec {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id = null;
    private String langName;
    private String langCode;
    private String username;

    public LangRec() {
    }

    public LangRec(String langName, String langCode) {
        this.langName = langName;
        this.langCode = langCode;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLangName() {
        return langName;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }
}