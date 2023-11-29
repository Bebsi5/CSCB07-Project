package com.example.cscb07_project;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AddEvent Class allows users to make and add a new event
 */
public class AddEvent extends AppCompatActivity {
    EditText eventDetails, eventName, eventDate, participantLimit;
    DatabaseReference db;
    Button addEventButton;

    /**
     * Setting up UI
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        // UI components
        // connecting ids to input fields for user to add stuff
        eventName = findViewById(R.id.event_detail_name);
        eventDetails = findViewById(R.id.event_details);
        eventDate = findViewById(R.id.event_date);
        participantLimit = findViewById(R.id.participant_limit);
        // button that users click to add event
        addEventButton = findViewById(R.id.add_event_Button);

        // getting "Events" reference from the Firebase Database
        db = FirebaseDatabase.getInstance().getReference("Events");
        // Log.d is just for debugging purposes
        Log.d("DatabaseReference", "Database reference: " + db.toString());
        // adding a listener on "Add" event button
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from user
                String name = eventName.getText().toString();
                String details = eventDetails.getText().toString();
                int limit = Integer.parseInt(participantLimit.getText().toString());
                String date = eventDate.getText().toString();

                addDataToFirebase(name, details, date, limit);
                Intent intent = new Intent(AddEvent.this, EventList.class);
                startActivity(intent);
            }
        });

        View backButton = findViewById(R.id.backButton);
        // Set an OnClickListener for the back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /*addDataToFirebase adds data to db*/
    private void addDataToFirebase(String name, String details, String date, int limit) {
        Log.d("AddData", "Adding data to Firebase: " + name + ", " + details + ", " + date + ", " + limit);
        // generate a unique key which will be the event ID
        DatabaseReference eventRef = db.push();
        Log.d("AddData", "Event ID" + eventRef);

        // checks if the key was generated properly
        // then makes a new Event object, sets it's values, adds it to the db
        // displays a toast on whether data was added successfully or not
        if (eventRef != null) {
            Event newEvent = new Event(name, details, date, limit, 0);
            eventRef.setValue(newEvent)
                    .addOnSuccessListener(aVoid -> Log.d("AddData", "Data added successfully"))
                    .addOnFailureListener(e -> Log.e("AddData", "Error adding data to Firebase", e));

            Toast.makeText(AddEvent.this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddEvent.this, "Failed to generate a unique key", Toast.LENGTH_SHORT).show();
        }
    }
}