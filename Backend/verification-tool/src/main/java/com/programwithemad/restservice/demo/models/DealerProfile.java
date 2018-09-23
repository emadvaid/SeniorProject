package com.programwithemad.restservice.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.StringJoiner;

@Entity
public class DealerProfile {

    // the user id of the dealer generated when the auth creds are created
    @Id
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String language1;
    private String language2;


    public DealerProfile(){}

    public DealerProfile(DealerProfile original) {
        this.id = original.id;
        this.firstName = original.firstName;
        this.lastName = original.lastName;
        this.email = original.email;
        this.language1 = original.language1;
        this.language2 = original.language2;
    }

    public DealerProfile(long id,
                         String username, String firstName, String lastName,
                         String email, String password,
                         String language1, String language2) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language1 = language1;
        this.language2 = language2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
