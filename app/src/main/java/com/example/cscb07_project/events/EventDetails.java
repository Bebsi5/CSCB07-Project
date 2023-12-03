package com.example.cscb07_project.events;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.cscb07_project.R;
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
    TextView retrieveEventName, retrieveEventDetails,
            retrieveEventDate, retrieveEventParLim;
    Button retrieveEventRSVP;
    String retrieveEventId;
    RSVPFunctionality rsvpFunctionality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);


        rsvpFunctionality = new RSVPFunctionality(this);


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
                rsvpFunctionality.updatingRSVPStatus(retrieveEventId);
            }
        });




        // back button
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
            }
        });
    }
}
