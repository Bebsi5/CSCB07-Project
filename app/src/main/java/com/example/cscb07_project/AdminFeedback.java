// AdminFeedback.java
package com.example.cscb07_project;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminFeedback extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> feedbackList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);

        recyclerView = findViewById(R.id.recyclerView);
        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedbackList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(feedbackAdapter);

       

        DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("Ratings");
        feedbackRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseData", "Number of reviews: " + snapshot.getChildrenCount());
                feedbackList.clear();

                // Use a counter to keep track of completed user data queries
                AtomicInteger counter = new AtomicInteger((int) snapshot.getChildrenCount());

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String review = dataSnapshot.child("review").getValue(String.class);
                    int score = dataSnapshot.child("score").getValue(Integer.class);
                    String userId = dataSnapshot.child("username").getValue(String.class);
                    String eventId = dataSnapshot.child("eventID").getValue(String.class);

                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                    userRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            String username = userSnapshot.child("name").getValue(String.class);

                            DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);
                            eventRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot eventSnapshot) {
                                    String event = eventSnapshot.child("eventName").getValue(String.class);
                                    Feedback feedback = new Feedback(review, score, username, event);
                                    feedbackList.add(feedback);

                                    // Check if all user data queries are complete
                                    if (counter.decrementAndGet() == 0) {
                                        feedbackAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle errors if needed
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors if needed
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });
    }


}