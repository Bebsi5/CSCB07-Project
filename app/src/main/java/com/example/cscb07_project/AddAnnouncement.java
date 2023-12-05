package com.example.cscb07_project;
import android.content.Intent;
import android.os.Bundle;
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

        title = findViewById(R.id.announcement_detail_title);
        message = findViewById(R.id.announcement_details);
        addAnnouncementButton = findViewById(R.id.add_announcement_button);

        // getting "Announcements" reference from the Firebase Database
        db = FirebaseDatabase.getInstance().getReference("Announcements");
        // Log.d is just for debugging purposes
        Log.d("DatabaseReference", "Database reference: " + db.toString());
        // adding a listener on "Add" event button
        addAnnouncementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from user
                String announcementName = title.getText().toString();
                String announcementInfo = message.getText().toString();

                if(announcementName.isEmpty() || announcementInfo.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Add Title And Details", Toast.LENGTH_SHORT).show();
                } else {
                    // call method to add data then open EventList activity
                    addDataToFirebase(announcementName, announcementInfo);
                    onBackPressed();
                }

            }
        });

        // backbutton
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    // addDataToFirebase adds data to db
    private void addDataToFirebase(String title, String message) {
        Log.d("AddData", "Adding data to Firebase: " + title + ", " + message);
        // generate a unique key which will be the event ID
        DatabaseReference announcementRef = db.push();
        Log.d("AddData", "Announcement ID" + announcementRef);

        // checks if the key was generated properly
        // then makes a new Event object, sets it's values, adds it to the db
        // displays a toast on whether data was added successfully or not
        if (announcementRef != null) {
            Announcements newAnnouncement = new Announcements(title, message);
            announcementRef.setValue(newAnnouncement)
                    .addOnSuccessListener(aVoid -> Log.d("AddData", "Data added successfully"))
                    .addOnFailureListener(e -> Log.e("AddData", "Error adding data to Firebase", e));

            Toast.makeText(AddAnnouncement.this, "Data added", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddAnnouncement.this, "Failed to generate a unique key", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(AddAnnouncement.this, AnnouncementList.class);
        startActivity(intent);
        finish();
    }
}

