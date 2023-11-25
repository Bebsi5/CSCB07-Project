package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
    EventAdapter eventAdapter;
    ArrayList<Event> eventList;
    Button addEventButton;

    /** onCreate sets up the UI based on the Firebase database and handles click events.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflates the layout for this activity as defined by activity_event_list.xml
        setContentView(R.layout.activity_event_list);

        // connecting the widget recyclerView based on eventList
        recyclerView = findViewById(R.id.eventList);
        // initializing eventList so it holds an array of Events
        eventList = new ArrayList<>();
        // making instance of EventAdapter
        eventAdapter = new EventAdapter(this, eventList);
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
                    Boolean rsvpBool = dataSnapshot.child("rsvpBool").getValue(Boolean.class);

                    //Event event = dataSnapshot.getValue(Event.class);
                    /*if (eventId != null && eventName != null && rsvpBool != null) {
                        Log.d("EventId", "Event ID: " + event.getEventId());
                        Log.d("EventName", "Event Name: " + event.getEventName());
                        Log.d("RSVPStatus", "Event Status: " + event.getRsvpBool());
                    } else {
                        Log.e("EventError", "Event is null for some data in Firebase");
                    }*/

                    Event event = new Event(eventId, eventName, eventDetails, rsvpBool);
                    eventList.add(event);
                }
                eventAdapter.notifyDataSetChanged();
            }

            // need to implement whenever using ValueEventListener
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error reading data from Firebase", error.toException());
                throw error.toException();
            }
        });

        // click event that opens up AddEvent class when addEventButton button is clicked
        addEventButton = (Button) findViewById(R.id.addEventButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventList.this, AddEvent.class);
                startActivity(intent);
            }
        });
    }
}
