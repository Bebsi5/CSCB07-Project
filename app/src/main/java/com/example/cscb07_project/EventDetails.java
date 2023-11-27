package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * display the details of a specific event, retrieved from the Firebase Realtime Database,
 * and allows the user to update their RSVP status of that event
 */
public class EventDetails extends AppCompatActivity {
    // represents the TextView elements in the event details layout
    TextView retrieveEventName, retrieveEventDetails,
            retrieveEventDate, retrieveEventParLim;
    // represents the button that allows the user to update the RSVP status
    Button retrieveEventRSVP;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    String retrieveEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the content view of the activity
        setContentView(R.layout.activity_event_details);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initializes the UI elements
        retrieveEventName = findViewById(R.id.eventDetailName);
        retrieveEventDetails = findViewById(R.id.eventDetails);
        retrieveEventRSVP = findViewById(R.id.rsvpButton);
        retrieveEventDate = findViewById(R.id.eventDate);
        retrieveEventParLim = findViewById(R.id.participantLimit);

        // Retrieves the event ID passed from the previous activity (EventList) through the intent
        retrieveEventId = getIntent().getStringExtra("Event ID");
        retrieveEventData(retrieveEventId);

        // Sets a click listener on the RSVP button to update the RSVP status when clicked
        retrieveEventRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatingRSVPStatus(retrieveEventId);
            }
        });


        // back button
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /** Retrieves event data from the Firebase Realtime Database based on the provided event ID
     *
     * @param eventId is the event data from the Firebase Realtime Database
     */
    private void retrieveEventData(String eventId) {
        // gets reference to "eventId" child key from "Events" key
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);

        // listeners to changes to db and displays the UI based on db
        eventRef.addValueEventListener(new ValueEventListener() {
            // onDataChange method, checks if the snapshot of the data exists,
            // then retrieves the Event object and updates the corresponding ids with
            // the event data
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        retrieveEventName.setText(event.getEventName());
                        retrieveEventDetails.setText(event.getEventDetails());
                        retrieveEventDate.setText(event.getEventDate());
                        retrieveEventParLim.setText(String.valueOf(event.getParticipantLimit()));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EventDetails", "Error reading data from Firebase", error.toException());
                throw error.toException();
            }
        });
    }

    /* In db check to see if there is room available for user to rsvp.

    If yes, set event to true for the user
            increment the current participants for the event
            open up EventList class
            show a toast that it was successful.

    If no, show a toast that there's no room to rsvp.
    */
    private void rsvpForEvent(String eventId) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference eventRef = mDatabase.child("Events").child(eventId);
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                if (event != null) {
                    int currentParticipants = event.getParticipants();
                    int participantLimit = event.getParticipantLimit();

                    if (currentParticipants < participantLimit || participantLimit == 0) {
                        eventRef.child("participants").setValue(currentParticipants + 1);
                        DatabaseReference userEventRef = mDatabase.child("Users").
                                child(userId).child("Events").child(eventId);
                        userEventRef.setValue(true);

                        Intent intent = new Intent(EventDetails.this, EventList.class);
                        startActivity(intent);
                        Toast.makeText(EventDetails.this, "RSVP successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EventDetails.this, "Event is full. Cannot RSVP.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventDetails.this, "Error getting event details.", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });
    }

    /*
    Does some null checks and checks if it's true that the user has RSVP'ed for the event.

    If yes, remove the event from the users info in db
            decrement the current participants for the event
            show a toast that the user has opted out.

    If no, call to function rsvpForEvent
            (which will handle changing the user's rsvp status to true).
     */
    private void updatingRSVPStatus(String eventId) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userEventRef = mDatabase.child("Users").
                child(userId).child("Events").child(eventId);
        userEventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue(Boolean.class) != null && snapshot.getValue(Boolean.class)) {
                    userEventRef.removeValue();
                    mDatabase.child("Events").child(eventId).
                            child("participants").
                            addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Long currentParticipants = dataSnapshot.getValue(Long.class);
                            if (currentParticipants != null && currentParticipants > 0) {
                                mDatabase.child("Events").child(eventId).
                                        child("participants").
                                        setValue(currentParticipants - 1);
                                Toast.makeText(EventDetails.this,
                                        "You have opted out", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(EventDetails.this, "Error with updating attendance", Toast.LENGTH_SHORT).show();
                            throw error.toException();
                        }
                    });
                } else {
                    rsvpForEvent(eventId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EventDetails.this, "Error getting RSVP status", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });
    }

}