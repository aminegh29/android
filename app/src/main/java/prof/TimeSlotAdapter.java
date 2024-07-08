package prof;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {

    private List<String> timeSlots;
    private Context context;
    private int checkedPosition = -1; // Ajout pour suivre la position cochée

    public TimeSlotAdapter(Context context, List<String> timeSlots) {
        this.context = context;
        this.timeSlots = timeSlots;
    }

    @NonNull
    @Override
    public TimeSlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reservation_time_slot, parent, false);
        return new TimeSlotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeSlotViewHolder holder, int position) {
        String timeSlot = timeSlots.get(position);
        holder.timeSlotTextView.setText(timeSlot);
        holder.timeSlotCheckBox.setChecked(position == checkedPosition);

        // Gestion des clics sur le CheckBox
        holder.timeSlotCheckBox.setOnClickListener(view -> {
            checkedPosition = position;
            notifyDataSetChanged(); // Rafraîchir l'adaptateur pour refléter le changement
        });
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    // Méthode pour obtenir le fuseau horaire sélectionné
    public String getSelectedTimeSlot() {
        return checkedPosition != -1 ? timeSlots.get(checkedPosition) : null;
    }

    public static class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        TextView timeSlotTextView;
        CheckBox timeSlotCheckBox;

        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            timeSlotTextView = itemView.findViewById(R.id.textViewReservationTimeSlot);
            timeSlotCheckBox = itemView.findViewById(R.id.checkBoxReservationTime);
        }
    }
}
