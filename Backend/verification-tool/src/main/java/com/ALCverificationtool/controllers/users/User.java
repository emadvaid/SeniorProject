package com.ALCverificationtool.controllers.users;

import com.ALCverificationtool.models.LangRec;
import com.ALCverificationtool.models.UserRec;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class User {

    private String username;
    private UUID id;
    private String typeAsStr;
    private boolean isActive = true;
    private String firstName;
    private String lastName;
    private String email;
    List<LangRec> languages;


    public User() {
    }

    public User(UserRec original) {
        this.id = original.getId();
        this.username = original.getUsername();
        this.typeAsStr = original.getType();
        this.isActive = original.isActive();
        this.firstName = original.getFirstName();
        this.lastName = original.getLastName();
        this.email = original.getEmail();
        this.languages = original.getLanguages();
    }

    public User(Optional<UserRec> originalOpt) {
        if(originalOpt.isPresent()) {
            UserRec original = originalOpt.get();
            this.id = original.getId();
            this.username = original.getUsername();
            this.typeAsStr = original.getType();
            this.isActive = original.isActive();
            this.firstName = original.getFirstName();
            this.lastName = original.getLastName();
            this.email = original.getEmail();
            this.languages = original.getLanguages();
        }
    }

    public User(String username, UUID id, String typeAsStr,
                boolean isActive, String firstName,
                String lastName, String email,
                List<LangRec> languages) {
        this.username = username;
        this.id = id;
        this.typeAsStr = typeAsStr;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.languages = languages;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTypeAsStr() {
        return typeAsStr;
    }

    public void setTypeAsStr(String typeAsStr) {
        this.typeAsStr = typeAsStr;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LangRec> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LangRec> languages) {
        this.languages = languages;
    }
}
