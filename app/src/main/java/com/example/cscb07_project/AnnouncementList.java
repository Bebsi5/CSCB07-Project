package com.example.cscb07_project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnnouncementList {
    RecyclerView recyclerView;
    DatabaseReference db;
    AnnouncementAdapter annoucementAdapter;
    ArrayList<Announcements> announcementList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_list);

        recyclerView = findViewById(R.id.announcementList);

        // Initialize contacts
        announcementList = new ArrayList<>();
        // Create adapter passing in the sample user data
        annoucementAdapter = new AnnouncementAdapter(this, announcementList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(annoucementAdapter);
        // Set layout manager to position the items
        db = FirebaseDatabase.getInstance().getReference("Events");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                announcementList.clear();
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
