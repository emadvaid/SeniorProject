package com.ALCverificationtool.models;

import javax.persistence.*;

@Entity
@Table(name="resets")
public class ResetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    long userId;
    boolean isUsed;

    public ResetEntity() {
    }

    public ResetEntity(long userId, boolean isUsed) {
        this.userId = userId;
        this.isUsed = isUsed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}
