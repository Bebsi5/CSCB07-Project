package com.example.cscb07_project;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.cscb07_project.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * AddEvent Class allows users to make and add a announcement
 * */
public class AddAnnouncement extends AppCompatActivity {
    EditText message, title;
    DatabaseReference db;
    Button addAnnouncementButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_announcement);
        Log.e("addannouncement", " announcement class is opened");


        title = findViewById(R.id.announcement_detail_title);
        message = findViewById(R.id.announcement_details);
        addAnnouncementButton = findViewById(R.id.add_announcement_Button);


        // getting "Announcements" reference from the Firebase Database
        db = FirebaseDatabase.getInstance().getReference("Announcements");



        // Log.d is just for debugging purposes
        Log.d("DatabaseReference", "Database reference: " + db.toString());
        // adding a listener on "Add" event button
        addAnnouncementButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String announcementName = String.valueOf(title.getText());
                String announcementInfo = String.valueOf(message.getText());
                Log.d("name", "announcementName " + announcementName);

                if (TextUtils.isEmpty(announcementName) || TextUtils.isEmpty(announcementInfo)) {
                    // Show a Toast or handle the empty fields scenario
                    Toast.makeText(AddAnnouncement.this, "Title or message cannot be empty", Toast.LENGTH_SHORT).show();
                } else {

                    // getting text from user
                    announcementName = title.getText().toString();
                    announcementInfo = message.getText().toString();

                    // call method to add data then open EventList activity
                    addDataToFirebase(announcementName, announcementInfo);
                    Intent intent = new Intent(AddAnnouncement.this, AnnouncementList.class);
                    startActivity(intent);
                }
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
    private void addDataToFirebase(String title, String message) {
        Log.d("AddData", "Adding data to Firebase: " + title + ", " + message);
        // generate a unique key which will be the event ID
        DatabaseReference eventRef = db.push();
        Log.d("AddData", "Announcement ID" + eventRef);

        // checks if the key was generated properly
        // then makes a new Event object, sets it's values, adds it to the db
        // displays a toast on whether data was added successfully or not
        if (eventRef != null) {
            Announcements newAnnouncement = new Announcements(title, message);
            eventRef.setValue(newAnnouncement)
                    .addOnSuccessListener(aVoid -> Log.d("AddData", "Data added successfully"))
                    .addOnFailureListener(e -> Log.e("AddData", "Error adding data to Firebase", e));

            Toast.makeText(AddAnnouncement.this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddAnnouncement.this, "Failed to generate a unique key", Toast.LENGTH_SHORT).show();
        }
    }




}

