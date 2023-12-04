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


/**
 * EventList Class displays all the events in the database in a list view
 */
public class EventList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    EventAdapter eventAdapter;
    ArrayList<Event> eventList;
    Button addEventButton;
    Boolean adminAccess;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflates the layout for this activity as defined by activity_event_list.xml
        setContentView(R.layout.activity_event_list);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = mDatabase.child("Users").child(userId);
        Log.d("EventList", "User Reference: " + userRef);
        addEventButton = (Button) findViewById(R.id.addEventButton);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Users.class);
                adminAccess = user.getAdminAccess();
                Log.d("EventList", "Admin access of user is " + adminAccess);

                if(adminAccess){
                    addEventButton.setVisibility(View.VISIBLE);
                }else{
                    addEventButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventList.this, "Error getting admin access status", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });

        // connecting the widget recyclerView based on eventList
        recyclerView = findViewById(R.id.eventList);
        // initializing eventList so it holds an array of Events
        eventList = new ArrayList<>();
        // making instance of EventAdapter
        eventAdapter = new EventAdapter(this, eventList, adminAccess);
        recyclerView.setAdapter(eventAdapter);

        // getting "Events" reference from the Firebase Database
        db = FirebaseDatabase.getInstance().getReference("Events");
        // just for RecyclerView. basically for the UI
        recyclerView.setHasFixedSize(true);
        // layout. also for UI
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // adding a listener to update data of 'eventList' whenever the db changes.
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + dataSnapshot.toString());


                    // stuff to get the data from db
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


            // need to implement whenever using ValueEventListener
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase", error.toException());
            }
        });

        // click event that opens up AddEvent class when addEventButton button is clicked

        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventList.this, AddEvent.class);
                startActivity(intent);
            }
        });

        //back button
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventList.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
