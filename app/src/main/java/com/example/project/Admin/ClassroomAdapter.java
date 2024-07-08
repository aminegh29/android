package com.example.project.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends RecyclerView.Adapter<ClassroomAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Classroom> classroomsList;
    private List<Classroom> classroomsListFull; // For filtering

    public ClassroomAdapter(Context context, List<Classroom> classroomsList) {
        this.context = context;
        this.classroomsList = classroomsList;
        this.classroomsListFull = new ArrayList<>(classroomsList); // Copy for filtering
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_classroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Classroom classroom = classroomsList.get(position);

        // Set data to the views in the ViewHolder
        holder.textBuilding.setText(context.getString(R.string.building_text, classroom.getBuilding()));
        holder.textFloor.setText(context.getString(R.string.floor_text, classroom.getFloor()));
        holder.textNumber.setText(context.getString(R.string.number_text, classroom.getNumber()));
        holder.textType.setText(context.getString(R.string.type_text, classroom.getType()));
    }

    @Override
    public int getItemCount() {
        return classroomsList.size();
    }

    @Override
    public Filter getFilter() {
        return classroomFilter;
    }

    private Filter classroomFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Classroom> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(classroomsListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Classroom classroom : classroomsListFull) {
                    if (classroom.getBuilding().toLowerCase().contains(filterPattern)
                            || classroom.getFloor().toLowerCase().contains(filterPattern)
                            || classroom.getNumber().toLowerCase().contains(filterPattern)
                            || classroom.getType().toLowerCase().contains(filterPattern)) {
                        filteredList.add(classroom);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            classroomsList.clear();
            classroomsList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void updateData(List<Classroom> newData) {
        classroomsList.clear();
        classroomsList.addAll(newData);
        classroomsListFull.clear();
        classroomsListFull.addAll(newData);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textBuilding;
        TextView textFloor;
        TextView textNumber;
        TextView textType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textBuilding = itemView.findViewById(R.id.buildingTextView);
            textFloor = itemView.findViewById(R.id.floorTextView);
            textNumber = itemView.findViewById(R.id.numberTextView);
            textType = itemView.findViewById(R.id.typeTextView);
        }
    }
}
