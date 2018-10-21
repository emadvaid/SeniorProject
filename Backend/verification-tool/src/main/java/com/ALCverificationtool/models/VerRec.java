package com.ALCverificationtool.models;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "VerRec")
public class VerRec {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")

    private UUID id = null;
    private String verNum;


    public VerRec() {
    }

    public VerRec(String verNum, UUID verCode) {
        this.verNum = verNum;

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVerNum() {
        return verNum;
    }

    public void setVerNum(String verNum) {
        this.verNum = verNum;
    }
}
