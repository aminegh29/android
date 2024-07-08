package com.example.project.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ComplaintActivity extends AppCompatActivity {

    private ListView listViewComplaints;
    private List<String> complaintsDescriptions = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        listViewComplaints = findViewById(R.id.list_view_complaints);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, complaintsDescriptions);
        listViewComplaints.setAdapter(adapter);

        fetchComplaints();
    }

    private void fetchComplaints() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("complaints")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        complaintsDescriptions.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Complaint complaint = document.toObject(Complaint.class);
                            // Concatenate the user info and description
                            String complaintInfo = "User ID: " + complaint.getUserId()
                                    + "\nUser Name: " + complaint.getUserName()
                                    + "\nDescription: " + complaint.getDescription();
                            complaintsDescriptions.add(complaintInfo);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle failure
                    }
                });
    }
    public void onBackClick(View view) {

        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
        finish();
    }

}
