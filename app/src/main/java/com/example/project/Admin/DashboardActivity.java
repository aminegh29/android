package com.example.project.Admin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {
    private SimpleBarGraphView simpleBarGraphView;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        simpleBarGraphView = findViewById(R.id.simpleBarGraphView);
        firestore = FirebaseFirestore.getInstance();

        loadTotalReservations();
    }

    private void loadTotalReservations() {
        firestore.collection("reservations").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int totalReservations = task.getResult().size();
                Log.d("DashboardActivity", "Total Reservations: " + totalReservations);
                simpleBarGraphView.setTotalReservations(totalReservations);
            } else {
                Log.e("DashboardActivity", "Error fetching reservations", task.getException());
            }
        });
    }
}
