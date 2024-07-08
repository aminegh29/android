package prof;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class ClassroomViewHolder extends RecyclerView.ViewHolder {

    private TextView buildingTextView;
    private TextView floorTextView;
    private TextView numberTextView;
    private TextView typeTextView;
    private CheckBox checkBox;

    public ClassroomViewHolder(@NonNull View itemView) {
        super(itemView);

        buildingTextView = itemView.findViewById(R.id.buildingTextView);
        floorTextView = itemView.findViewById(R.id.floorTextView);
        numberTextView = itemView.findViewById(R.id.numberTextView);
        typeTextView = itemView.findViewById(R.id.typeTextView);

    }

    public void setDetails(String building, String floor, String number, String type) {
        buildingTextView.setText("Building: " + building);
        floorTextView.setText("Floor: " + floor);
        numberTextView.setText("Number: " + number);
        typeTextView.setText("Type: " + type);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }
}
