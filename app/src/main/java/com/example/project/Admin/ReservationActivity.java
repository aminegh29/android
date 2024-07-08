package com.example.project.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {
    private RecyclerView reservationRecycleView;
    private ReservationAdapter reservationAdapter;
    private ArrayList<Reservation> reservationList;
    private FirebaseFirestore firestore;
    private static final int ADD_RESERVATION_REQUEST=1;
@Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reservation);
    firestore = FirebaseFirestore.getInstance();
    reservationRecycleView= findViewById(R.id.reservationRecycleView);
    reservationList=new ArrayList<>();
    reservationAdapter=new ReservationAdapter(reservationList);
    reservationRecycleView.setLayoutManager((new LinearLayoutManager(this)));
    reservationRecycleView.setAdapter(reservationAdapter);

    loadreservationList();
}
    private void loadreservationList() {
        reservationList.clear();

        firestore.collection("reservations").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Reservation reservation = document.toObject(Reservation.class);
                    reservationList.add(reservation);
                }
                reservationAdapter.notifyDataSetChanged();
            } else {
                // Handle the error...
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_RESERVATION_REQUEST && resultCode == RESULT_OK) {
            // Rafraîchir la liste des utilisateurs après avoir ajouté un utilisateur avec succès
            loadreservationList();
        }
    }

    public void onBackClick(View view) {

        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
        finish();
    }
}