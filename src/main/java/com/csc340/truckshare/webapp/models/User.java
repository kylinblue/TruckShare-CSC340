package com.csc340.truckshare.webapp.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    private boolean isAdmin;
    private boolean isBanned;

    private String username;

    private String userPassword;

    public User() {}

    public User(int userId, boolean isAdmin, boolean isBanned,
                String userPassword, String username, String details){
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.userPassword = userPassword;
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean getUserType() {
        return isAdmin;
    }

    public void setUserType(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void banUser(boolean ban){
        this.isBanned = ban;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // plain text password!
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    // password hash, to be implemented
    /*public PasswordHash getUserPasswordHash() {
        return this.userPassword.hashCode();
    }*/

}
