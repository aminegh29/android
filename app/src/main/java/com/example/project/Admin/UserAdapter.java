package com.example.project.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<Utilisateur> userList;

    public UserAdapter(ArrayList<Utilisateur> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Utilisateur user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView nomTextView;
        private TextView prenomTextView;
        private TextView emailTextView;
        private TextView roleTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            prenomTextView = itemView.findViewById(R.id.prenomTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            roleTextView = itemView.findViewById(R.id.roleTextView);
        }

        public void bind(Utilisateur user) {
            nomTextView.setText(user.getNom());
            prenomTextView.setText(user.getPrenom());
            emailTextView.setText(user.getEmail());

            // Build and set the roles string
            StringBuilder roles = new StringBuilder();
            if (user.isSecurite()) {
                roles.append("Sécurité ");
            }
            if (user.isProfessor()) {
                roles.append("Professeur ");
            }
            roleTextView.setText(roles.toString());
        }
    }
}
