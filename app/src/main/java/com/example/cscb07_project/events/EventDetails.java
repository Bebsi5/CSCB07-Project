package com.example.cscb07_project.events;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.cscb07_project.Admin;
import com.example.cscb07_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        retrieveEventName = findViewById(R.id.eventDetailName);
        retrieveEventDetails = findViewById(R.id.eventDetails);
        retrieveEventRSVP = findViewById(R.id.rsvpButton);
        retrieveEventDate = findViewById(R.id.eventDate);
        retrieveEventParLim = findViewById(R.id.participantLimit);

        retrieveEventId = getIntent().getStringExtra("Event ID");
        retrieveEventData(retrieveEventId);

        rsvpFunctionality = new RSVPFunctionality(this);

        retrieveEventRSVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rsvpFunctionality.updatingRSVPStatus(retrieveEventId);
            }
        });

        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void retrieveEventData(String eventId) {
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);

        eventRef.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onBackPressed(){
        finish();
    }
}
