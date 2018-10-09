package com.ALCverificationtool.models;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="resets")
public class ResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id = null;

    private UUID userId;
    private boolean isActive;

    public ResetToken() {
    }

    public ResetToken(UUID userId, boolean isActive) {
        this.userId = userId;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
