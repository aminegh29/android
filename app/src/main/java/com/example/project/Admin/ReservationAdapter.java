package com.example.project.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder> {
    private ArrayList<Reservation> reservationList;

    // Constructor
    public ReservationAdapter(ArrayList<Reservation> reservationList) {
        this.reservationList = reservationList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ReservationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false);
        return new ReservationViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ReservationViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the contents of the view with that element
        Reservation reservation = reservationList.get(position);
        holder.bind(reservation);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reservationList.size();
    }

    // Provide a reference to the views for each data item
    public static class ReservationViewHolder extends RecyclerView.ViewHolder {
        // Each data item is just a string in this case
        private TextView timeSlotTextView;
        private TextView userNameTextView;
        private TextView roomNameTextView;

        public ReservationViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            timeSlotTextView = itemView.findViewById(R.id.textViewTimeSlot);
            userNameTextView = itemView.findViewById(R.id.textViewUserName);
            roomNameTextView = itemView.findViewById(R.id.textViewRoomName);
        }

        public void bind(Reservation reservation) {
            // Set the values for each item
            timeSlotTextView.setText(reservation.getTimeSlot());
            userNameTextView.setText(reservation.getUserName());
            roomNameTextView.setText(reservation.getRoomName());
        }
    }
}
