package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cscb07_project.events.EventList;
import com.example.cscb07_project.post.POStActivityBasic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Button button;
    TextView userNameTextView, userEmailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        button = findViewById(R.id.logout);
        userNameTextView = findViewById(R.id.userNameTextView);
        userEmailTextView = findViewById(R.id.userEmailTextView);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // User data exists in the database
                        String userName = snapshot.child("name").getValue(String.class);
                        String userEmail = snapshot.child("email").getValue(String.class);

                        // Display user information on the Profile page
                        userNameTextView.setText("Name: " + userName);
                        userEmailTextView.setText("Email: " + userEmail);
                    } else {
                        // Handle the case where user data doesn't exist
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                }
            });
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //write a me

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.action_home) {
                    navigateToPage(MainActivity.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_post) {
                    navigateToPage(POStActivityBasic.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_complaints) {
                    navigateToPage(UserComplaintActivityScreen.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_events) {
                    navigateToPage(EventList.class);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (itemId == R.id.action_profile) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        // Highlight the "Profile" menu item after setting up the listener
        //bottomNavigationView.setSelectedItemId(R.id.action_profile);
    }
    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }

}