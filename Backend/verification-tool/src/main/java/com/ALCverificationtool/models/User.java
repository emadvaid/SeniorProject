package com.ALCverificationtool.models;

public class User {

    private String username;
    private Long id;
    private String typeAsStr;
    private boolean isActive = true;
    private String firstName;
    private String lastName;
    private String email;
    private String language1;
    private String language2;

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
        this.language1 = original.getLanguage1();
        this.language2 = original.getLanguage2();
    }

    public User(String username, Long id, String typeAsStr,
                boolean isActive, String firstName,
                String lastName, String email,
                String language1, String language2) {
        this.username = username;
        this.id = id;
        this.typeAsStr = typeAsStr;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language1 = language1;
        this.language2 = language2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getLanguage1() {
        return language1;
    }

    public void setLanguage1(String language1) {
        this.language1 = language1;
    }

    public String getLanguage2() {
        return language2;
    }

    public void setLanguage2(String language2) {
        this.language2 = language2;
    }
}
