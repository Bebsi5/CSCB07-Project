package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EventRatingPage extends AppCompatActivity {

    Button reviewSub; // Button that links to the submission Button
    TextView eventTitle, reviewInput; //Text fields for header and review input field
    RatingBar score; // Rating Bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_rating_page);
        reviewSub = findViewById(R.id.event_review_sub);
        eventTitle = findViewById(R.id.Event_Title);
        reviewInput = findViewById(R.id.Event_Review_Text);
        score = findViewById(R.id.ratingBar);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Ratings");

        Intent intent = getIntent();
        String EventId = intent.getStringExtra("Event ID"); // Getting event ID/name

        eventTitle.setOnClickListener(new View.OnClickListener() { //Setting event listener for sub button
            @Override
            public void onClick(View view) {
                String input_text = reviewInput.getText().toString();
                double user_score = ((double) score.getRating());
                Ratings review = new Ratings(user_score, input_text,EventId,auth.getCurrentUser().getUid());
                database.push().setValue(review); // Storing the rating in the data base
            }
        });


    }
}