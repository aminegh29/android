package com.example.project.Securite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Admin.Admin;
import com.example.project.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {

    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter adapter;
    private List<not> notificationList;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        notificationList = new ArrayList<>();

        adapter = new NotificationAdapter(this, notificationList);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotifications.setAdapter(adapter);

        // Ajouter une notification de test à la liste
        notificationList.add(new not("Notification de test"));

        // Informer l'adapter que les données ont changé
        adapter.notifyDataSetChanged();

        // Charger les vraies notifications depuis Firestore
        loadNotificationsFromFirestore();
    }



    private void loadNotificationsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notifications")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notificationList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String message = document.getString("message");
                            Timestamp timestamp = document.getTimestamp("timestamp");
                            String userId = document.getString("userId");
                            not notification = new not(message);
                            notificationList.add(notification);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Log d'erreur
                    }
                });
    }
    public void onBackClick(View view) {
        Intent intent = new Intent(this, Securite.class);
        startActivity(intent);
        finish();
    }


}
