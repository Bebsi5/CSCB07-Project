package com.example.cscb07_project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AnnouncementDetails extends AppCompatActivity {

    TextView retrieveAnnouncementTitle, retrieveAnnouncementMessage;
    String retrieveAnnouncementId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_details);



        // Initializes the UI elements
        retrieveAnnouncementTitle = findViewById(R.id.announcementDetailTitle);
        retrieveAnnouncementMessage = findViewById(R.id.announcementDetails);


        // Retrieves the event ID passed from the previous activity (AnnouncementList) through the intent
        retrieveAnnouncementId = getIntent().getStringExtra("Announcement ID");
        retrieveEventData(retrieveAnnouncementId);






        // back button
        View backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    /** Retrieves announcement data from the Firebase Realtime Database based on the provided event ID
     *
     * @param announcementId is the event data from the Firebase Realtime Database
     */
    private void retrieveEventData(String announcementId) {
        // gets reference to "eventId" child key from "Events" key
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Announcements").child(announcementId);

        // listeners to changes to db and displays the UI based on db
        eventRef.addValueEventListener(new ValueEventListener() {
            // onDataChange method, checks if the snapshot of the data exists,
            // then retrieves the Event object and updates the corresponding ids with
            // the event data
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Announcements announcements = snapshot.getValue(Announcements.class);
                    if (announcements != null) {
                        retrieveAnnouncementTitle.setText(announcements.getTitle());
                        retrieveAnnouncementMessage.setText(announcements.getMessage());
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
