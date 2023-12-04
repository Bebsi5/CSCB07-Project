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


import com.example.cscb07_project.Admin;
import com.example.cscb07_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        db = FirebaseDatabase.getInstance().getReference("Events");
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                String details = eventDetails.getText().toString();
                String participantLimitText = participantLimit.getText().toString();
                String date = eventDate.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(participantLimitText) || TextUtils.isEmpty(date)) {
                    Toast.makeText(AdminAddEvent.this, "All fields should be filled.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int limit = Integer.parseInt(participantLimitText);

                addDataToFirebase(name, details, date, limit);
                Intent intent = new Intent(AdminAddEvent.this, EventList.class);
                startActivity(intent);
                finish();
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

    private void addDataToFirebase(String name, String details, String date, int limit) {
        DatabaseReference eventRef = db.push();

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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AdminAddEvent.this, Admin.class);
        startActivity(intent);
        finish();
    }
}