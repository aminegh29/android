package com.example.project.Securite;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ComplaintSec extends AppCompatActivity {

    private static final String TAG = "ComplaintSec";
    private EditText editTextComplaint;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
            private ImageView backIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_sec);
        backIcon = findViewById(R.id.backIcon);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        editTextComplaint = findViewById(R.id.editTextComplaint);

        findViewById(R.id.btnadd).setOnClickListener(view -> saveComplaintToFirestore());
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }

    private void saveComplaintToFirestore() {
        final FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User is not signed in", Toast.LENGTH_SHORT).show();
            return;
        }

        final String userId = currentUser.getUid();
        final String description = editTextComplaint.getText().toString().trim();

        if (description.isEmpty()) {
            Toast.makeText(ComplaintSec.this, "Complaint text is empty", Toast.LENGTH_SHORT).show();
            return;
        }


        db.collection("users").document(userId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userName = task.getResult().getString("nom");
                if (userName != null) {
                    Map<String, Object> complaint = new HashMap<>();
                    complaint.put("userId", userId);
                    complaint.put("userName", userName);
                    complaint.put("description", description);

                    db.collection("complaints").add(complaint)
                            .addOnSuccessListener(documentReference -> {
                                Log.d(TAG, "Complaint added with ID: " + documentReference.getId());
                                Toast.makeText(ComplaintSec.this, "Complaint saved successfully", Toast.LENGTH_SHORT).show();
                                editTextComplaint.setText(""); // Clear the input
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Failed to add complaint", e);
                                Toast.makeText(ComplaintSec.this, "Failed to save complaint", Toast.LENGTH_SHORT).show();
                            });
                } else {
                    Toast.makeText(ComplaintSec.this, "User name not found in database", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e(TAG, "Failed to get user data", task.getException());
                Toast.makeText(ComplaintSec.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void out() {
        Intent intent = new Intent(this, Securite.class);
        startActivity(intent);
        finish();
    }
}
