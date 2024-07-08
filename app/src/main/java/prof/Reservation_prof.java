package prof;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.Admin.Utilisateur;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Reservation_prof extends AppCompatActivity {

    private ImageView backIcon;
    private RecyclerView recyclerViewTimeSlots;
    private Button reserveButton;
    private TimeSlotAdapter timeSlotAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth firebaseAuth;
    private String roomName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_prof);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        backIcon = findViewById(R.id.backIcon);
        recyclerViewTimeSlots = findViewById(R.id.roomsRecycleView);
        reserveButton = findViewById(R.id.reserveButton);

        setupTimeSlotsRecyclerView();

        backIcon.setOnClickListener(v -> finish());

        roomName = getIntent().getStringExtra("roomName");

        reserveButton.setOnClickListener(v -> {
            String selectedTimeSlot = timeSlotAdapter.getSelectedTimeSlot();
            if (selectedTimeSlot == null) {
                Log.e("Reservation", "No time slot selected.");
                return;
            }
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
            if (currentUser != null) {
                fetchUserNameAndConfirmReservation(currentUser, roomName, selectedTimeSlot);
            } else {
                Log.e("Reservation", "User is not logged in.");
            }
        });
    }


    private void setupTimeSlotsRecyclerView() {
        List<String> timeSlots = Arrays.asList(
                "8:30-9:30", "9:30-10:30", "10:30-11:30", "11:30-12:30",
                "14:30-15:30", "15:30-16:30", "16:30-17:30", "17:30-18:30");
        timeSlotAdapter = new TimeSlotAdapter(this, timeSlots);
        recyclerViewTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTimeSlots.setAdapter(timeSlotAdapter);
    }

    private void fetchUserNameAndConfirmReservation(FirebaseUser currentUser, String roomName, String timeSlot) {
        final String userId = currentUser.getUid();
        db.collection("users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            Utilisateur user = documentSnapshot.toObject(Utilisateur.class);
            if (user != null) {
                String fullName = user.getPrenom() + " " + user.getNom();
                storeNotification(fullName, roomName, timeSlot, userId);
                confirmReservation(fullName, roomName, timeSlot, userId);
            } else {
                Log.e("Reservation", "User document is null.");
            }
        }).addOnFailureListener(e -> Log.e("Reservation", "Error fetching user document", e));
    }

    private void confirmReservation(String userName, String roomName, String timeSlot, String userId) {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("roomName", roomName);
        reservation.put("timeSlot", timeSlot);
        reservation.put("userName", userName);
        reservation.put("userId", userId);
        reservation.put("createdAt", Timestamp.now());

        db.collection("reservations").add(reservation)
                .addOnSuccessListener(documentReference -> Log.d("Reservation", "Reservation confirmed with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.e("Reservation", "Error confirming reservation", e));
    }

    private void storeNotification(String userName, String roomName, String timeSlot, String userId) {
        String notificationMessage = "The professor " + userName + " has demanded the opening of " + roomName + " at " + timeSlot;

        // Log the notification details before attempting to save
        Log.d("NotificationDebug", "Attempting to save notification:");
        Log.d("NotificationDebug", "User Name: " + userName);
        Log.d("NotificationDebug", "Room Name: " + roomName);
        Log.d("NotificationDebug", "Time Slot: " + timeSlot);
        Log.d("NotificationDebug", "User ID: " + userId);
        Log.d("NotificationDebug", "Message: " + notificationMessage);

        NotificationP notification = new NotificationP(notificationMessage, userId, Timestamp.now());

        db.collection("notifications").add(notification)
                .addOnSuccessListener(documentReference -> {
                    Log.d("NotificationDebug", "Notification saved with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("NotificationDebug", "Error saving notification", e);
                });
    }


    public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
        private List<String> timeSlots;
        private Context context;
        private int checkedPosition = -1;

        public TimeSlotAdapter(Context context, List<String> timeSlots) {
            this.context = context;
            this.timeSlots = timeSlots;
        }

        @NonNull
        @Override
        public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_reservation_time_slot, parent, false);
            return new TimeSlotViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, @SuppressLint("RecyclerView") int position) {
            String timeSlot = timeSlots.get(position);
            holder.timeSlotTextView.setText(timeSlot);
            holder.timeSlotCheckBox.setChecked(position == checkedPosition);
            holder.timeSlotCheckBox.setOnClickListener(view -> {
                checkedPosition = position;
                notifyDataSetChanged();
            });
        }

        @Override
        public int getItemCount() {
            return timeSlots.size();
        }

        public String getSelectedTimeSlot() {
            return checkedPosition != -1 ? timeSlots.get(checkedPosition) : null;
        }

        public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
            TextView timeSlotTextView;
            CheckBox timeSlotCheckBox;

            public TimeSlotViewHolder(View itemView) {
                super(itemView);
                timeSlotTextView = itemView.findViewById(R.id.textViewReservationTimeSlot);
                timeSlotCheckBox = itemView.findViewById(R.id.checkBoxReservationTime);
            }
        }
    }
}
