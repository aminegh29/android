package com.example.project.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project.R;

import java.util.List;

public class ComplaintAdapter extends ArrayAdapter<Complaint> {

    public ComplaintAdapter(Context context, List<Complaint> complaints) {
        super(context, 0, complaints);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_complaint, parent, false);
        }

        Complaint complaint = getItem(position);

        TextView userIdTextView = convertView.findViewById(R.id.userIdTextView);
        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.descriptionTextView);

        userIdTextView.setText(complaint.getUserId());
        userNameTextView.setText(complaint.getUserName());
        descriptionTextView.setText(complaint.getDescription());

        return convertView;
    }
}
