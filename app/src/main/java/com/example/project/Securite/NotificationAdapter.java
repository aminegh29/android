package com.example.project.Securite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private final List<not> notificationList;
    private final Context context;

    public NotificationAdapter(Context context, List<not> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        not notification = notificationList.get(position);
        holder.bind(notification, context);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewNotification;
        private final Button confirmOpeningButton;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNotification = itemView.findViewById(R.id.textViewNotification);
            confirmOpeningButton = itemView.findViewById(R.id.buttonConfirmOpening);
        }

        public void bind(final not notification, Context context) {
            textViewNotification.setText(notification.getMessage());
            confirmOpeningButton.setOnClickListener(view -> confirmOpening(notification, context));
        }

        private void confirmOpening(not notification, Context context) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null) {

                db.collection("users").document(currentUser.getUid()).get()
                        .addOnSuccessListener(userDoc -> {
                            if (userDoc.exists()) {
                                String userName = userDoc.getString("nom");
                                String prenom= userDoc.getString("prenom");

                                String message = "The security " + userName + " " + prenom + " has opened the classroom.";

                                // Now create a notification with this message
                                createNotification(message, db);
                            } else {
                                Log.e("NotificationAdapter", "User document does not exist.");
                            }
                        })
                        .addOnFailureListener(e -> {
                            Log.e("NotificationAdapter", "Error fetching user document", e);
                        });
            } else {
                Log.e("NotificationAdapter", "Current user is null, cannot post notification.");
            }
        }

        private void createNotification(String message, FirebaseFirestore db) {
            Map<String, Object> docData = new HashMap<>();
            docData.put("message", message);

            db.collection("notificationS").add(docData) // Ensure the collection name is correct
                    .addOnSuccessListener(documentReference -> {
                        Log.d("NotificationAdapter", "DocumentSnapshot written with ID: " + documentReference.getId());
                    })
                    .addOnFailureListener(e -> {
                        Log.w("NotificationAdapter", "Error adding document", e);
                    });
        }





    }
}
