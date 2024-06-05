package com.csc340.truckshare.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "message")

public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int msgId;

    private int originUserId;
    private int destinationUserId;

    private String payload;
    //private timestamp??

    public Message(){}

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
