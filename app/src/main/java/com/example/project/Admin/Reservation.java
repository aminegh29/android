package com.example.project.Admin;

import com.google.firebase.Timestamp;

public class Reservation {
    private Timestamp createdAt; // Changed from String to Timestamp
    private String roomName;
    private String userId;
    private String userName;
    private String timeSlot;


    public Reservation() {}

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
}
