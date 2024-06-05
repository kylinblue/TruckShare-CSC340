package com.csc340.truckshare.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "conversation")

public class Conv {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int convId;

    private int userId;

    private String title;

    private boolean supportNeeded;

    public Conv() {
    }

    public Conv(int convId, int userId,
                String title, Boolean supportNeeded) {
        this.convId = convId;
        this.userId = userId;
        this.title = title;
        this.supportNeeded = supportNeeded;
    }

    public int getConvId() {
        return convId;
    }

    public void setConvId(int convId) {
        this.convId = convId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSupportNeeded() {
        return supportNeeded;
    }

    public void setSupportNeeded(boolean supportNeeded) {
        this.supportNeeded = supportNeeded;
    }
}