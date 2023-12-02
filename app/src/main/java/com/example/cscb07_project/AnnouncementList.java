package com.example.cscb07_project;

import static com.example.cscb07_project.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class AnnouncementList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference database;
    AnnouncementAdapter announcementAdapter;
    FirebaseAuth authentication;
    ArrayList<Announcements> announcementList;
    Button addButton;

    View backButton;

    Users user;
    Boolean adminAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.announcement_list);

        Log.e("announcementlist", "opened announcement list class");

        authentication = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        String userId = authentication.getCurrentUser().getUid();
        DatabaseReference userRef = database.child("Users").child(userId);
        addButton = findViewById(R.id.addButton);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Users.class);
                adminAccess = user.getAdminAccess();
                Log.d("EventList", "Admin access of user is " + adminAccess);

                // Set visibility of addButton based on adminAccess
                if (adminAccess) {
                    addButton.setVisibility(View.VISIBLE);
                } else {
                    addButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AnnouncementList.this, "Error getting admin access status", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });

        recyclerView = findViewById(R.id.announcementList);

        // Initialize contacts
        announcementList = new ArrayList<>();
        // Create adapter passing in the sample user data
        announcementAdapter = new AnnouncementAdapter(this, announcementList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(announcementAdapter);
        // Set layout manager to position the items
        db = FirebaseDatabase.getInstance().getReference("Announcements");
        Log.d("RawData", "database reference:  " + db);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                announcementList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + dataSnapshot.toString());

                    String announcementId = dataSnapshot.getKey();
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String message = dataSnapshot.child("message").getValue(String.class);

                    Announcements announcement = new Announcements(announcementId, title, message);
                    announcementList.add(announcement);

                }
                announcementAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase", error.toException());
            }
        });

        backButton = findViewById(R.id.backButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnnouncementList.this, AddAnnouncement.class));
                Log.e("announcementlist", "add button is clicked");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

}
