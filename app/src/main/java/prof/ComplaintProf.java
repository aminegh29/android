package prof;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ComplaintProf extends AppCompatActivity {

    private static final String TAG = "ComplaintProf";
    private EditText editTextComplaint;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ImageView backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_prof);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        editTextComplaint = findViewById(R.id.editTextComplaint);
        backIcon = findViewById(R.id.backIcon);
        MaterialButton btnAdd = findViewById(R.id.btnadd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveComplaintToFirestore();
            }
        });
    }


    public void onBackClick(View view) {
        Intent intent = new Intent(this, Professor.class);
        startActivity(intent);
        finish();
    }

    private void saveComplaintToFirestore() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            final String userId = currentUser.getUid();
            final String description = editTextComplaint.getText().toString().trim();

            if (description.isEmpty()) {
                Toast.makeText(ComplaintProf.this, "Complaint text is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fetch the user's name from Firestore users collection
            db.collection("users").document(userId).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String userName = task.getResult().getString("nom"); // Assuming the field is called "name"
                    if (userName != null) {
                        // Create a new complaint Map to store in Firestore
                        Map<String, Object> complaint = new HashMap<>();
                        complaint.put("userId", userId);
                        complaint.put("userName", userName);
                        complaint.put("description", description);

                        // Add a new document with a generated ID to the complaints collection
                        db.collection("complaints").add(complaint)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "Complaint added with ID: " + documentReference.getId());
                                        Toast.makeText(ComplaintProf.this, "Complaint saved successfully", Toast.LENGTH_SHORT).show();
                                        editTextComplaint.setText(""); // Clear the input
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding complaint", e);
                                        Toast.makeText(ComplaintProf.this, "Failed to save complaint", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ComplaintProf.this, "User name is not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.w(TAG, "Error getting user document", task.getException());
                    Toast.makeText(ComplaintProf.this, "Error fetching user details", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "User is not signed in", Toast.LENGTH_SHORT).show();
        }
    }
}
