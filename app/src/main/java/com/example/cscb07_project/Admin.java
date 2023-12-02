package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Admin extends AppCompatActivity {
    FirebaseAuth auth;
    Button logout;
    FirebaseUser user;
    AppCompatButton rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_admin);
        logout = findViewById(R.id.logout);
        rating = findViewById(R.id.rating);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                navigateToPage(Login.class);
            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(AdminFeedback.class);
            }
        });
    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }
}