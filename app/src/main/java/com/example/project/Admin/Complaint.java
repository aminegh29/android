package com.example.project.Admin;

public class Complaint {
    private String userId;
    private String userName;
    private String description;

    // No-argument constructor is required
    public Complaint() {}

    // Getters and setters for all fields
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Other methods, if any
}
