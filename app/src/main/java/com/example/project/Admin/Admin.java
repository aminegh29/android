package com.example.project.Admin;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;
import com.example.project.authentification;

public class Admin extends AppCompatActivity {

    TextView textView;
    TextView roomsTextView ;

    TextView out , res , complainte , dash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        textView = findViewById(R.id.users);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUser();
            }
        });
        out = findViewById(R.id.out);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                backhome();
            }
        });
        roomsTextView=findViewById(R.id.Rooms);
        roomsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRooms();
            }
        });
        res=findViewById(R.id.reservations);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                res();
            }
        });
        complainte= findViewById(R.id.complainte);
        complainte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                com();
            }
        });
        dash= findViewById(R.id.Dashboard);
        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ds();
            }
        });


    }

    public void openUser(){
        Intent intent = new Intent(this, AdminUser.class);
        startActivity(intent);
    }
    public void  backhome(){
        Intent intent = new Intent(this, authentification.class);
        startActivity(intent);
    }
    public void  openRooms(){
        Intent intent = new Intent(this, admin_classrooms.class);
        startActivity(intent);
    }
    public void  res(){
        Intent intent = new Intent(this, ReservationActivity.class);
        startActivity(intent);
    }
    public void  com(){
        Intent intent = new Intent(this, ComplaintActivity.class);
        startActivity(intent);
    }
    public void  ds(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }


}
