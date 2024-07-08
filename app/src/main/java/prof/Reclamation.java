package prof;

public class Reclamation {
    private String id;
    private String user;
    private String description;
    private String reservation;

    // Default constructor for Firebase


    // Constructor with user and description
    public Reclamation(String user, String description , String reservation) {
        this.user = user;
        this.description = description;
        this.reservation=reservation;
    }

    // Getter and setter for id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and setter for user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    // Getter and setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getReservation(){
        return reservation;
    }
    public void setReservation(String reservation){
        this.reservation=reservation;
    }
}


