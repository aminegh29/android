package prof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.authentification;

public class Professor extends AppCompatActivity {
TextView Toolbar, out, complainte,Rooms , not;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor);

        // Récupérez la Toolbar
        Toolbar = findViewById(R.id.toolbar);
        out = findViewById(R.id.out);
        complainte = findViewById(R.id.complainte);

        Rooms= findViewById(R.id.Rooms);
        not= findViewById(R.id.not);

        Toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEdit();

            }
        });
        Rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rom();

            }
        });
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outProf();
            }
        });
        complainte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com();
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                not();
            }
        });
    }
    public void openEdit(){
        Intent intent = new Intent(this , ProfessorEdit.class);
        startActivity(intent);
        finish();

    }
    public void Rom(){
        Intent intent = new Intent(this , AssignedRooms.class);
        startActivity(intent);
        finish();

    }  public void outProf(){
        Intent intent = new Intent(this , authentification.class);
        startActivity(intent);
        finish();

    }
    public void com(){
        Intent intent = new Intent(this , ComplaintProf.class);
        startActivity(intent);
        finish();

    }

    public void not(){
        Intent intent = new Intent(this , NotificationProf.class);
        startActivity(intent);
        finish();

    }


}

