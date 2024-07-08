package prof;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Admin.Classroom;
import com.example.project.Admin.ClassroomAdapter;
import com.example.project.R;
import com.example.project.RecyclerItemClickListener;
import com.example.project.authentification;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignedRooms extends AppCompatActivity {

    private RecyclerView roomsRecyclerView;
    private ClassroomAdapter adapter;
    private List<Classroom> classroomsList;
    private FirebaseFirestore firestore;
    private SearchView searchView;
    private ImageView backIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_rooms);
        backIcon = findViewById(R.id.backIcon);
        firestore = FirebaseFirestore.getInstance();


        searchView = findViewById(R.id.searchView);
        roomsRecyclerView = findViewById(R.id.roomsRecycleView);

        classroomsList = new ArrayList<>();
        roomsRecyclerView.setHasFixedSize(true);
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ClassroomAdapter(this, classroomsList);
        roomsRecyclerView.setAdapter(adapter);

        loadClassrooms();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });


        roomsRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, roomsRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Classroom selectedClassroom = classroomsList.get(position);
                        Intent intent = new Intent(AssignedRooms.this, Reservation_prof.class);
                        intent.putExtra("roomName", selectedClassroom.getName());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Handle long item click if needed
                    }
                })
        );
    }

    private void loadClassrooms() {
        classroomsList.clear();
        firestore.collection("classrooms").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Classroom classroom = document.toObject(Classroom.class);
                    classroomsList.add(classroom);
                }
                adapter.notifyDataSetChanged();
            } else {
                Log.e("FirestoreError", "Error getting classrooms", task.getException());
            }
        });
    }

    public void out() {
        Intent intent = new Intent(this, Professor.class);
        startActivity(intent);
        finish();
    }
}
