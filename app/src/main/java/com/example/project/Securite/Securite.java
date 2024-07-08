package com.example.project.Securite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.R;
import com.example.project.authentification;

public class Securite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_securite);
    }


    public void onEditClick(View view) {
        Intent intent = new Intent(this, Securite_edit.class);
        startActivity(intent);
    }


    public void onNotificationClick(View view) {
        Intent intent = new Intent(this, Notification.class);
        startActivity(intent);
        finish();
    }


    public void onComplaintClick(View view) {
        Intent intent = new Intent(this, ComplaintSec.class);
        startActivity(intent);
        finish();

    }


    public void onLogoutClick(View view) {

        Intent intent = new Intent(this, authentification.class);
        startActivity(intent);
        finish();
    }

}

