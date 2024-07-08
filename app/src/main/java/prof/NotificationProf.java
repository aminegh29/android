package prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationProf extends AppCompatActivity {

    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter adapter;
    private ImageView backIcon;
    private List<NotificationItem> notificationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_prof);
        backIcon = findViewById(R.id.backIcon);
        recyclerViewNotifications = findViewById(R.id.recyclerViewNotifications);
        notificationList = new ArrayList<>();
        adapter = new NotificationAdapter(notificationList);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotifications.setAdapter(adapter);

        loadNotificationsFromFirestore();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                out();
            }
        });
    }


    private void loadNotificationsFromFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("notificationS")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notificationList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            NotificationItem notification = document.toObject(NotificationItem.class);
                            notificationList.add(notification);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Handle failure
                    }
                });
    }
    public void out() {
        Intent intent = new Intent(this, Professor.class);
        startActivity(intent);
        finish();
    }
}
