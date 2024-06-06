package com.csc340.truckshare.webapp.models;
import jakarta.persistence.*;

import java.util.Date;

@Entity // Specifies that the class is an entity and is mapped to a database table
@Table(name = "listing") // Specifies the name of the database table to be used for mapping
public class Listing {

    @Id   // Specifies the primary key of an entity
    @GeneratedValue(strategy = GenerationType.AUTO) // Provides the specification of generation strategies for the primary keys
    private int listingId; // Unique identifier for each listing

    private int userId; // Identifier for the user associated with this listing

    private String username;

    private String title; // Title of the listing

    private String details; // Detailed description of the listing

    @Temporal(TemporalType.DATE) // Specifies that the date should be stored in a specific temporal type (DATE)
    private Date targetDate; // Target date for the listing

    private String status; // Current status of the listing (e.g., active, inactive)

    private byte[] image ;

    // Default constructor
    public Listing() {
    }

    // Parameterized constructor to initialize all fields
    public Listing(int listingId, int userId, String title, String details, Date targetDate, String status) {
        this.listingId = listingId; // Sets the listingId
        this.userId = userId; // Sets the userId
        this.username = username;
        this.title = title; // Sets the title
        this.details = details; // Sets the details
        this.targetDate = targetDate; // Sets the targetDate
        this.status = status; // Sets the status
        this.image = image;
    }

    public int getListingId() {
        return listingId;
    }

    public void setListingId(int listingId) {
        this.listingId = listingId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}