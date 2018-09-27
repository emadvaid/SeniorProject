package com.ALCverificationtool.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class UserAuthentication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID accessToken;

    private Long userId;
    private String type;

    public UserAuthentication() {
    }

    public UserAuthentication(Long userId, String type) {
        this.userId = userId;
        this.type = type;
        this.accessToken = null;
    }

    public UserAuthentication(Long id, UUID accessToken, String type) {
            this.userId = id;
            this.accessToken = accessToken;
        this.type = type;
    }

    public UserAuthentication(UserRec authCredentials) {
        this.accessToken = null;
        this.userId = authCredentials.getId();
        this.type = authCredentials.getType();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UUID getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(UUID accessToken) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
