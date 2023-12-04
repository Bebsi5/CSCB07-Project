package com.example.cscb07_project.events;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.cscb07_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * AddEvent Class allows users to make and add a new event
 */
public class AdminAddEvent extends AppCompatActivity {
    EditText eventDetails, eventName, eventDate, participantLimit;
    DatabaseReference db;
    Button addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);

        eventName = findViewById(R.id.event_detail_name);
        eventDetails = findViewById(R.id.event_details);
        eventDate = findViewById(R.id.event_date);
        participantLimit = findViewById(R.id.participant_limit);
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
                String participantLimitText = participantLimit.getText().toString();
                String date = eventDate.getText().toString();

                // Check if any of the fields is empty
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(participantLimitText) || TextUtils.isEmpty(date)) {
                    // Display a message or handle the empty fields as needed
                    Toast.makeText(AdminAddEvent.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse participant limit after validating it is not empty
                int limit = Integer.parseInt(participantLimitText);

                // call method to add data then open EventList activity
                addDataToFirebase(name, details, date, limit);
                Intent intent = new Intent(AdminAddEvent.this, EventList.class);
                startActivity(intent);
            }
        });

        // backbutton
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // addDataToFirebase adds data to db
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

            Toast.makeText(AdminAddEvent.this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdminAddEvent.this, "Failed to generate a unique key", Toast.LENGTH_SHORT).show();
        }
    }
}