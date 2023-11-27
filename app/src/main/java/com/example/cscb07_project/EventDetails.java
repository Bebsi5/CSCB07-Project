package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * display the details of a specific event, retrieved from the Firebase Realtime Database,
 * and allows the user to update the RSVP status of that event
 */
public class EventDetails extends AppCompatActivity {
    // represents the TextView elements in the event details layout
    TextView retrieveEventName, retrieveEventDetails, retrieveEventId;
    // represents the button that allows the user to update the RSVP status
    Button retrieveEventRSVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the content view of the activity
        setContentView(R.layout.activity_event_details);

        Log.d("EventDetails", "onCreate: Activity started");

        // Initializes the UI elements
        retrieveEventName = findViewById(R.id.eventDetailName);
        retrieveEventDetails = findViewById(R.id.eventDetails);
        retrieveEventRSVP = findViewById(R.id.rsvpButton);

        // Retrieves the event ID passed from the previous activity (EventList) through the intent
        String eventId = getIntent().getStringExtra("Event ID");
        //retrieveEventId.setText(eventId);

        retrieveEventData(eventId);

        // Sets a click listener on the RSVP button to update the RSVP status when clicked
        retrieveEventRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabase(eventId);
            }
        });

        // Logs messages for debugging purposes
        Log.d("EventDetails", "onCreate: End of activity");
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
            //onDataChange method, checks if the snapshot of the data exists,
            // then retrieves the Event object and updates the corresponding TextView
            // and the RSVP button with the event details
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Event event = snapshot.getValue(Event.class);
                    if (event != null) {
                        retrieveEventName.setText(event.getEventName());
                        retrieveEventDetails.setText(event.getEventDetails());
                        retrieveEventRSVP.setText(event.getRsvpBool() ? "Confirmed!" : "RSVP");
                    }
                }
            }

            // onCancelled method, logs an error message if there's an issue
            // reading data from Firebase
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EventDetails", "Error reading data from Firebase", error.toException());
            }
        });
    }

    /** updates the RSVP status of the event in the Firebase Realtime Database
     *
     * @param eventId is the id of the event
     */
    private void updateDatabase(String eventId) {
        // gets reference to "eventId" child key from "Events" key
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);

        // modifies the rsvpBool field based on the current text of the RSVP button
        eventRef.child("rsvpBool").setValue(!retrieveEventRSVP.getText().equals("Confirmed!"))
                .addOnSuccessListener(aVoid -> Log.d("EventDetails", "RSVP status updated successfully"))
                .addOnFailureListener(e -> Log.e("EventDetails", "Error updating RSVP status", e));
    }
}