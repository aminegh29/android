package prof;

public class NotificationP {

        private String message;
        private String userId;
        private com.google.firebase.Timestamp timestamp;

        public NotificationP() {
            // Firebase requiert un constructeur par défaut
        }

        public NotificationP(String message, String userId, com.google.firebase.Timestamp timestamp) {
            this.message = message;
            this.userId = userId;
            this.timestamp = timestamp;
        }

        // Getters et setters pour chaque champ
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getUserId() {
            return userId;        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public com.google.firebase.Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(com.google.firebase.Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }

