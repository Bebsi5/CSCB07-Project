package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EventList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference db;
    EventAdapter eventAdapter;
    ArrayList<Event> eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_list);

        recyclerView = findViewById(R.id.eventList);

        // Initialize contacts
        eventList = new ArrayList<>();
        // Create adapter passing in the sample user data
        eventAdapter = new EventAdapter(this, eventList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(eventAdapter);
        // Set layout manager to position the items
        db = FirebaseDatabase.getInstance().getReference("Events");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.d("RawData", "Raw Data: " + dataSnapshot.toString());

                    String eventId = dataSnapshot.getKey();
                    String eventName = dataSnapshot.child("eventName").getValue(String.class);
                    Boolean rsvpBool = dataSnapshot.child("rsvpBool").getValue(Boolean.class);

                    Event event = new Event(eventId, eventName, rsvpBool);
                    eventList.add(event);

                    //Event event = dataSnapshot.getValue(Event.class);
                    /*if (eventId != null && eventName != null && rsvpBool != null) {
                        Log.d("EventId", "Event ID: " + event.getEventId());
                        Log.d("EventName", "Event Name: " + event.getEventName());
                        Log.d("RSVPStatus", "Event Status: " + event.getRsvpBool());
                    } else {
                        Log.e("EventError", "Event is null for some data in Firebase");
                    }*/
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase", error.toException());
            }
        });

    }
}
