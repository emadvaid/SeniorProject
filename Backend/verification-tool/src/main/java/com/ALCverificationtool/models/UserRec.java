package com.ALCverificationtool.models;

import com.ALCverificationtool.controllers.users.User;

import javax.persistence.*;
import java.util.List;
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

    @ManyToMany()
    @JoinTable()
    List<LangRec> languages;

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
        this.languages = original.getLanguages();
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
        this.languages = original.languages;

        password = null;
    }

    public UserRec(String username, String type, boolean isActive,
                   String firstName, String lastName, String email,
                   List<LangRec> languages) {
        this.username = username;
        this.type = type;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.languages = languages;

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

    public List<LangRec> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LangRec> languages) {
        this.languages = languages;
    }
}