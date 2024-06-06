package com.csc340.truckshare.webapp.models;

import jakarta.persistence.*;

// Entity annotation marks this class as a JPA entity
@Entity
// Specifies the table name in the database
@Table(name = "conversation")

public class Conv {

    // Primary key annotation, with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int convId;

    private int sourceUserId; // ID of the user who initiated the conversation
    private int targetUserId; // ID of the user who is the target of the conversation

    private String title; // Title of the conversation

    private boolean supportNeeded; // Flag indicating if support is needed

    // Default constructor
    public Conv() {
    }

    public Conv(int convId, int userId,
                String title, Boolean supportNeeded) {
        this.convId = convId;
        this.sourceUserId = userId;
        this.targetUserId = targetUserId;
        this.title = title;
        this.supportNeeded = supportNeeded;
    }

    public int getConvId() {
        return convId;
    }

    public void setConvId(int convId) {
        this.convId = convId;
    }

    public int getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(int sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public int getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(int targetUserId) {
        this.targetUserId = targetUserId;
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