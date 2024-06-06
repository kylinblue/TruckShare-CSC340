package com.csc340.truckshare.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "message")

public class Message {

    // Primary key annotation, with auto-generated value
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int msgId;

    private int originUserId; // ID of the user who sent the message
    private int destinationUserId;// ID of the user who is the recipient of the message

    private String payload; // The content of the message

    // Default constructor
    public Message(){}

    // Parameterized constructor
    public Message(int msgId, int originUserId, int destinationUserId, String payload) {
        this.msgId = msgId;
        this.originUserId = originUserId;
        this.destinationUserId = destinationUserId;
        this.payload = payload;
    }

    public int getMsgId() {
        return msgId;
    }

    public int getOriginUserId() {
        return originUserId;
    }

    public void setOriginUserId(int originUserId) {
        this.originUserId = originUserId;
    }

    public int getDestinationUserId() {
        return destinationUserId;
    }

    public void setDestinationUserId(int destinationUserId) {
        this.destinationUserId = destinationUserId;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
