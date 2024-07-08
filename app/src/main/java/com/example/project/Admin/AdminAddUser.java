package com.example.project.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.authentification;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminAddUser extends AppCompatActivity {

    private Button ajouterUtilisateurButton;
    private EditText nomEditText, prenomEditText, emailEditText, textphoneEditText, PasswordEditText;
    private CheckBox securiteCheckBox, professorCheckBox;

    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;

    // Assuming "users" is the name of your collection in Firestore
    private static final String COLLECTION_NAME = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_user);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ajouterUtilisateurButton = findViewById(R.id.btnadd);
        nomEditText = findViewById(R.id.nomEditText);
        prenomEditText = findViewById(R.id.prenomEditText);
        emailEditText = findViewById(R.id.emailEditText);
        textphoneEditText = findViewById(R.id.textphoneEditText);
        PasswordEditText = findViewById(R.id.PasswordEditText);
        securiteCheckBox = findViewById(R.id.securiteCheckBox);
        professorCheckBox = findViewById(R.id.professorCheckBox);

        ajouterUtilisateurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterUtilisateur();
            }
        });
    }

    private void ajouterUtilisateur() {
        String nomUtilisateur = nomEditText.getText().toString().trim();
        String prenomUtilisateur = prenomEditText.getText().toString().trim();
        String emailUtilisateur = emailEditText.getText().toString().trim();
        String phoneUtilisateur = textphoneEditText.getText().toString().trim();
        String passwordUtilisateur = PasswordEditText.getText().toString().trim();
        boolean isSecurite = securiteCheckBox.isChecked();
        boolean isProfessor = professorCheckBox.isChecked();

        if (!nomUtilisateur.isEmpty() && !prenomUtilisateur.isEmpty() && !emailUtilisateur.isEmpty() && !passwordUtilisateur.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(emailUtilisateur, passwordUtilisateur)
                    .addOnSuccessListener(authResult -> {
                        // L'utilisateur a été créé avec succès dans Firebase Authentication
                        String uid = authResult.getUser().getUid();

                        // Ajoute l'utilisateur à la collection Firestore "users" avec l'UID comme ID
                        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(uid);

                        // Crée un objet Utilisateur
                        Utilisateur utilisateur = new Utilisateur(uid, nomUtilisateur, prenomUtilisateur, emailUtilisateur, phoneUtilisateur, isSecurite, isProfessor);
                        utilisateur.setPassword(passwordUtilisateur);

                        documentReference.set(utilisateur)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(AdminAddUser.this, "Utilisateur ajouté avec succès", Toast.LENGTH_SHORT).show();

                                    // Ajoutez des logs pour le débogage
                                    Log.d("TAG", "Utilisateur créé avec succès dans Firebase Auth et Firestore");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("TAG", "Erreur lors de l'ajout de l'utilisateur à Firestore: " + e.getMessage());
                                    Toast.makeText(AdminAddUser.this, "Erreur lors de l'ajout de l'utilisateur", Toast.LENGTH_SHORT).show();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Log.e("TAG", "Erreur lors de la création de l'utilisateur dans Firebase Auth: " + e.getMessage());
                        Toast.makeText(AdminAddUser.this, "Erreur lors de la création de l'utilisateur", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackClick(View view) {

        Intent intent = new Intent(this, AdminUser.class);
        startActivity(intent);
        finish();
    }
}
