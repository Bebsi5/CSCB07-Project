package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cscb07_project.complaints.AdminComplaint;
import com.example.cscb07_project.events.AdminAddEvent;
import com.example.cscb07_project.events.EventList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin extends AppCompatActivity {
    FirebaseAuth auth;
    Button logout;
    FirebaseUser user;
    AppCompatButton rating, complaints, events, eventsAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin);
        logout = findViewById(R.id.logout);
        rating = findViewById(R.id.rating);
        complaints = findViewById(R.id.complaints);
        events = findViewById(R.id.events);
        eventsAll = findViewById(R.id.all_events);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                navigateToPage(LoginActivity.class);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(AdminFeedback.class);
            }
        });

        complaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(AdminComplaint.class);
            }
        });

        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(AdminAddEvent.class);
            }
        });

        eventsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(EventList.class);
            }
        });
    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Admin.class);
        startActivity(intent);
        finish();
    }
}