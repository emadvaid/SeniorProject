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

    @Column(name="verNum")
    private String rawVerNum;


    public VerRec() {
    }

    public VerRec(String verNum) {
        this.rawVerNum = verNum;

    }

    public String getSafeVersionNumber() {
        // make the raw version number safe to use as a MySQL tables suffix
        String tmp = rawVerNum.replaceAll("\\.", "_");
        return tmp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVerNum() {
        return rawVerNum;
    }

    public void setVerNum(String verNum) {
        this.rawVerNum = verNum;
    }
}
