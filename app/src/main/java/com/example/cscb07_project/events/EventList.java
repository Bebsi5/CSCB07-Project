package com.example.cscb07_project.events;


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


import com.example.cscb07_project.Admin;
import com.example.cscb07_project.MainActivity;
import com.example.cscb07_project.R;
import com.example.cscb07_project.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    EventAdapter eventAdapter;
    ArrayList<Event> eventList;
    Button addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("EventListOnCreate", "Entering EventList class");

        setContentView(R.layout.activity_event_list);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = mDatabase.child("Users").child(userId);
        Log.d("EventList", "User Reference: " + userRef);
        addEventButton = findViewById(R.id.addEventButton);

        recyclerView = findViewById(R.id.eventList);
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(EventList.this, eventList);
        recyclerView.setAdapter(eventAdapter);

        db = FirebaseDatabase.getInstance().getReference("Events");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(EventList.this));

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + dataSnapshot.toString());

                    String eventId = dataSnapshot.getKey();
                    String eventName = dataSnapshot.child("eventName").getValue(String.class);
                    String eventDetails = dataSnapshot.child("eventDetails").getValue(String.class);
                    String eventDate = dataSnapshot.child("eventDate").getValue(String.class);
                    int participantLimit = dataSnapshot.child("participantLimit").getValue(Integer.class);

                    Event event = new Event(eventId, eventName, eventDetails, eventDate, 0, participantLimit);

                    eventList.add(event);
                }
                eventAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase", error.toException());
            }
        });

        //back button
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(EventList.this, MainActivity.class);

        startActivity(intent);
        finish();
    }
}
