package prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Admin.Utilisateur;
import com.example.project.R;
import com.example.project.authentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfessorEdit extends AppCompatActivity {
    private EditText nameEditText, passEditText, phoneEditText;
    private TextView emailViewText;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_edit);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.nameEditText);
        passEditText = findViewById(R.id.passEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        emailViewText = findViewById(R.id.emailViewText);

        // Load user data when the activity is created
        loadUserData();

        // Set OnClickListener for the done_icon in each EditText
        setDoneIconClickListener(nameEditText);
        setDoneIconClickListener(passEditText);
        setDoneIconClickListener(phoneEditText);
    }

    private void setDoneIconClickListener(final EditText editText) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.done_icon, 0);
        editText.setOnClickListener(v -> saveUserData());
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = firestore.collection("users").document(user.getUid());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        Utilisateur utilisateur = document.toObject(Utilisateur.class);
                        if (utilisateur != null) {
                            nameEditText.setText(utilisateur.getNom());
                            passEditText.setText(utilisateur.getPassword());
                            phoneEditText.setText(utilisateur.getPhone());

                            // Set email in the emailViewText
                            emailViewText.setText(""+ user.getEmail());
                        }
                    } else {
                        Toast.makeText(ProfessorEdit.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfessorEdit.this, "Error getting document: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            DocumentReference userRef = firestore.collection("users").document(user.getUid());

            String newName = nameEditText.getText().toString().trim();
            String newPass = passEditText.getText().toString().trim();
            String newPhone = phoneEditText.getText().toString().trim();

            Map<String, Object> updates = new HashMap<>();
            updates.put("nom", newName);
            updates.put("phone", newPhone);
            updates.put("password", newPass);

            userRef.update(updates)
                    .addOnSuccessListener(aVoid -> {
                        user.updatePassword(newPass)
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfessorEdit.this, "Modifications enregistrées avec succès", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ProfessorEdit.this, Professor.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(ProfessorEdit.this, "Erreur lors de la mise à jour du mot de passe", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .addOnFailureListener(e -> Toast.makeText(ProfessorEdit.this, "Erreur lors de l'enregistrement des modifications", Toast.LENGTH_SHORT).show());
        }
    }

    public void onCancelClick(View view) {
        Intent intent = new Intent(this, Professor.class);
        startActivity(intent);
        finish();
    }

    public void onLogoutClick(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, authentification.class);
        startActivity(intent);
        finish();
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, Professor.class);
        startActivity(intent);
        finish();
    }
}
