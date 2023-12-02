package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import androidx.navigation.ui.AppBarConfiguration;

import com.example.cscb07_project.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseAuth auth;
    Button button;
    FirebaseUser user;
    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        button = findViewById(R.id.logout);
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_home) {
                    return true;
                } else if (itemId == R.id.action_post) {
                    return true;
                } else if (itemId == R.id.action_complaints) {
                    return true;
                } else if (itemId == R.id.action_events) {
                    return true;
                } else if (itemId == R.id.action_profile) {
                    navigateToPage(Profile.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }
            }
        });



    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }


}