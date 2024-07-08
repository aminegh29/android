package com.example.project.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AdminUser extends AppCompatActivity {

    private Button ajouterUtilisateurButton;
    private RecyclerView userRecyclerView;
    private UserAdapter userAdapter;
    private ArrayList<Utilisateur> userList;

    private FirebaseFirestore firestore;

    private static final int ADD_USER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_users);

        firestore = FirebaseFirestore.getInstance();

        ajouterUtilisateurButton = findViewById(R.id.ajouterUtilisateurButton);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        userList = new ArrayList<>();
        userAdapter = new UserAdapter(userList);

        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(userAdapter);

        ajouterUtilisateurButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminUser.this, AdminAddUser.class);
                startActivityForResult(intent, ADD_USER_REQUEST);
            }
        });

        // Chargez la liste des utilisateurs depuis Firestore
        loadUserList();
    }

    private void loadUserList() {
        // Effacez la liste actuelle pour éviter les duplications lors de l'actualisation
        userList.clear();

        // Récupérez les utilisateurs depuis Firestore
        firestore.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Utilisateur utilisateur = document.toObject(Utilisateur.class);
                            userList.add(utilisateur);
                        }
                        // Mettez à jour l'adaptateur avec la nouvelle liste d'utilisateurs
                        userAdapter.notifyDataSetChanged();
                    } else {
                        // Gérez les erreurs ici
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_USER_REQUEST && resultCode == RESULT_OK) {
            // Rafraîchir la liste des utilisateurs après avoir ajouté un utilisateur avec succès
            loadUserList();
        }
    }
    public void onBackClick(View view) {

        Intent intent = new Intent(this, Admin.class);
        startActivity(intent);
        finish();
    }
}
