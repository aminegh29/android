package com.example.project.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.RecyclerItemClickListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class admin_classrooms extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassroomAdapter adapter;
    private List<Classroom> classroomsList;
    private FirebaseFirestore firestore;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_classrooms);

        firestore = FirebaseFirestore.getInstance();

        recyclerView = findViewById(R.id.roomsRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        classroomsList = new ArrayList<>();
        adapter = new ClassroomAdapter(this, classroomsList);
        recyclerView.setAdapter(adapter);

        loadClassrooms();



        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

    private void loadClassrooms() {
        firestore.collection("classrooms").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                classroomsList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Classroom classroom = document.toObject(Classroom.class);
                    classroomsList.add(classroom);
                }
                adapter.notifyDataSetChanged();
            } else {
                Log.e("admin_classrooms", "Error getting documents: ", task.getException());
            }
        });
    }

    public void onBackClick(View view) {
        onBackPressed();
    }
}
