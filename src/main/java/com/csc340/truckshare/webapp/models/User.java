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

    private String password;

    private String firstName;
    private String lastName;

    public User() {}

    public User(int userId, boolean isAdmin, boolean isBanned,
                String password, String username, String details){
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
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
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    // password hash, to be implemented
    /*public PasswordHash getUserPasswordHash() {
        return this.userPassword.hashCode();
    }*/
    @Override
    public String toString() {
        return this.username + " " + this.password;
    }
}
