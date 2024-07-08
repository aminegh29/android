package prof;

import com.google.firebase.Timestamp;

public class NotificationItem {
    private String message;
    private Timestamp timestamp;

    public NotificationItem() {
        // Required for Firestore's automatic data mapping.
    }

    public NotificationItem(String message, Timestamp timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
