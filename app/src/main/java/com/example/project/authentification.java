package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Admin.Admin;
import com.example.project.Securite.Securite;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.material.button.MaterialButton;

import prof.Professor;

public class authentification extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authentification);

        mAuth = FirebaseAuth.getInstance();

        EditText editTextEmail = findViewById(R.id.emailEditText);
        EditText editTextPassword = findViewById(R.id.passwordEditText);

        MaterialButton loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve user input
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Check credentials against Firebase Authentication
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(authentification.this, task -> {
                            if (task.isSuccessful()) {
                                // Authentication successful
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    // Check user role
                                    checkUserRole(user.getUid());
                                }
                            } else {
                                editTextPassword.setError("Authentication failed");
                            }
                        });
            }
        });
    }

    private void checkUserRole(String userId) {
        // Ensure that "users" matches the name of your collection in Cloud Firestore
        DocumentReference userDocRef = FirebaseFirestore.getInstance().collection("users").document(userId);

        userDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();

                if (document.exists()) {
                    String userRole = document.getString("role");

                    if ("admin".equals(userRole)) {
                        // Redirect to Admin activity
                        startActivity(new Intent(authentification.this, Admin.class));
                    } else {
                        boolean isSecurite = document.getBoolean("securite");
                        boolean isProfessor = document.getBoolean("professor");

                        if (isSecurite) {
                            // Redirect to Securite activity
                            startActivity(new Intent(authentification.this, Securite.class));
                        }else if (isProfessor) {
                            // Redirect to Securite activity
                            startActivity(new Intent(authentification.this, Professor.class));
                        } else {
                            // Handle other user roles or scenarios
                            // You can add additional conditions here if needed
                            Toast.makeText(authentification.this, "Authentication successful for a regular user", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
}
