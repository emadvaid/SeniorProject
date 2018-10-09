package com.ALCverificationtool.models;

import com.ALCverificationtool.controllers.users.User;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name="user")
public class UserRec {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id = null;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String type;
    private boolean isActive;
    private String firstName;
    private String lastName;
    private String email;
    private String language1;
    private String language2;

    public UserRec() {
    }

    public UserRec(User original){
        this.id = original.getId();
        this.username = original.getUsername();
        this.type = original.getTypeAsStr();
        this.isActive = original.isActive();
        this.firstName = original.getFirstName();
        this.lastName = original.getLastName();
        this.email = original.getEmail();
        this.language1 = original.getLanguage1();
        this.language2 = original.getLanguage2();
        password = null;
    }

    public UserRec(UserRec original) {
        this.id = original.id;
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

    public UserRec(String username, String type, boolean isActive,
                   String firstName, String lastName, String email,
                   String language1, String language2) {
        this.username = username;
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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