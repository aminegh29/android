package com.example.project.Securite;

import com.google.firebase.Timestamp;

public class not {
    private String message;
    private Timestamp timestamp;
    private String userId;
    private String reservationDocId;
    private String ClassroomId;



    // Updated constructor
    public not(String message) {
        this.message = message;
        this.timestamp = timestamp;
        this.userId = userId;

    }



    // Getters for each field
    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getUserId() {
        return userId;
    }
    public void setReservationDocId(String reservationDocId) {
        this.reservationDocId = reservationDocId;
    }


    public String getReservationDocId() {
        return reservationDocId;
    }


    public String getClassroomId() {

        return ClassroomId ;
    }
}
