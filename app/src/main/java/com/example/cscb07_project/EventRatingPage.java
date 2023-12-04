package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cscb07_project.events.EventList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventRatingPage extends AppCompatActivity {

    Button reviewSub, backButton; // Button that links to the submission Button
    TextView eventTitle;
    EditText reviewInput;//Text fields for header and review input field
    RatingBar score; // Rating Bar
    FirebaseAuth auth;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rating_page);
        reviewSub = findViewById(R.id.event_review_sub);
        backButton = findViewById(R.id.rating_back_button);
        eventTitle = findViewById(R.id.Event_Title);
        reviewInput = findViewById(R.id.Event_Review_Text);
        score = findViewById(R.id.ratingBar);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Ratings");

        Intent intent = getIntent();
        String EventId = intent.getStringExtra("Event ID");// Getting event ID/name
        String EventName = intent.getStringExtra("Event Name");
        eventTitle.setText("Submit a rating for: " + EventName);

        reviewSub.setOnClickListener(new View.OnClickListener() { //Setting event listener for sub button
            @Override
            public void onClick(View view) {
                String input_text = reviewInput.getText().toString();
                double user_score = ((double) score.getRating());
                if(input_text.isEmpty() || user_score == 0.0){
                    Toast.makeText(getApplicationContext(),"Please Add Feedback or Add a score.",Toast.LENGTH_SHORT).show();
                } else {

                    Log.e("eventratingpage", "user_score" + user_score);
                    Log.e("eventratingpage", "input_text" + input_text);
                    Ratings review = new Ratings(user_score, input_text, EventId, auth.getCurrentUser().getUid());
                    database.push().setValue(review); // Storing the rating in the data base
                    reviewInput.setText("");
                    Toast.makeText(EventRatingPage.this, "Review Submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() { // Back Button, Goes back to the event List
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventRatingPage.this, EventList.class);
                startActivity(intent);
                finish();
            }
        });
    }
}