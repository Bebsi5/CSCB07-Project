package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AddEvent Class allows users to make and add a new event
 */
public class AddEvent extends AppCompatActivity {
    EditText eventDetails, eventName;
    DatabaseReference db;
    Button addEventButton;

    /**
     * Setting up UI
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        // UI components
        // connecting ids to input fields for user to add stuff
        eventName = findViewById(R.id.eventDetailName);
        eventDetails = findViewById(R.id.eventDetails);
        // button that users click to add event
        addEventButton = findViewById(R.id.addEventButton);

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

                // shows a toast that asks user to write data if the event fields are empty
                // o/w adding data to database then creates an intent to start the EventList activity
                if (TextUtils.isEmpty(name) && TextUtils.isEmpty(details)) {
                    Toast.makeText(AddEvent.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDataToFirebase(name, details);
                    Intent intent = new Intent(AddEvent.this, EventList.class);
                    startActivity(intent);
                }
            }
        });
    }

    /** addDataToFirebase adds data to db
     *
     * @param name
     * @param details
     */
    private void addDataToFirebase(String name, String details) {
        Log.d("AddData", "Adding data to Firebase: " + name + ", " + details);
        // generate a unique key which will be the event ID
        DatabaseReference eventRef = db.push();
        Log.d("AddData", "Event ID" + eventRef);

        // checks if the key was generated properly
        // then makes a new Event object, sets it's values, adds it to the db
        // displays a toast on whether data was added successfully or not
        if (eventRef != null) {
            Event newEvent = new Event(name, details, false);
            eventRef.setValue(newEvent)
                    .addOnSuccessListener(aVoid -> Log.d("AddData", "Data added successfully"))
                    .addOnFailureListener(e -> Log.e("AddData", "Error adding data to Firebase", e));

            Toast.makeText(AddEvent.this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddEvent.this, "Failed to generate a unique key", Toast.LENGTH_SHORT).show();
        }
    }
}