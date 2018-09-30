package com.ALCverificationtool.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="resets")
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id = null;

    private long userId;
    private boolean isActive;

    public ResetToken() {
    }

    public ResetToken(long userId, boolean isActive) {
        this.userId = userId;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
