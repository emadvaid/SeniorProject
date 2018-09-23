package com.programwithemad.restservice.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    private String username;

    // The user id field
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String password = null;
    private String type;
    private boolean isActive = false;
    private String firstName;
    private String lastName;
    private String email;
    private String language1;
    private String language2;

    public User() {
    }

    public User(User original) {
        this.username = original.username;
        this.password = original.password;
        this.type = original.type;
        this.isActive = original.isActive;
        this.firstName = original.firstName;
        this.lastName = original.lastName;
        this.email = original.email;
        this.language1 = original.language1;
        this.language2 = original.language2;
        password = null;
    }

    public User(String username, String type, boolean isActive,
                String firstName, String lastName, String email,
                String language1, String language2) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language1 = language1;
        this.language2 = language2;
        password = null;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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