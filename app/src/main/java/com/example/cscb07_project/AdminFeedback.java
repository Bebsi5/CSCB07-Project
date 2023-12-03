// AdminFeedback.java
package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class AdminFeedback extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FeedbackAdapter feedbackAdapter;
    private List<Feedback> feedbackList;
    private TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feedback);

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.back);
        feedbackList = new ArrayList<>();
        feedbackAdapter = new FeedbackAdapter(feedbackList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(feedbackAdapter);

        DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("FirebaseData", "Number of events: " + snapshot.getChildrenCount());
                feedbackList.clear();

                AtomicInteger counter = new AtomicInteger((int) snapshot.getChildrenCount());

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    String eventId = childSnapshot.getKey();
                    String event = childSnapshot.child("eventName").getValue(String.class);
                    Log.d("FirebaseData", "Number of events: " + eventId);

                    DatabaseReference feedbackRef = FirebaseDatabase.getInstance().getReference("Ratings");
                    Query query = feedbackRef.orderByChild("eventID").equalTo(eventId);

                    CompletableFuture<Void> userTasks = new CompletableFuture<>();

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Log.d("FirebaseData", "Number of reviews: " + snapshot.getChildrenCount());
                            Log.d("FirebaseData", "current event: " + query);

                            List<CompletableFuture<Void>> userRefTasks = new ArrayList<>();

                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String review = dataSnapshot.child("review").getValue(String.class);
                                int score = dataSnapshot.child("score").getValue(Integer.class);
                                String userId = dataSnapshot.child("username").getValue(String.class);

                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                CompletableFuture<Void> userRefTask = new CompletableFuture<>();

                                userRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String username = snapshot.child("name").getValue(String.class);
                                        Feedback feedback = new Feedback(review, score, username, event);
                                        feedbackList.add(feedback);

                                        userRefTask.complete(null);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        userRefTask.completeExceptionally(error.toException());
                                    }
                                });

                                userRefTasks.add(userRefTask);
                            }

                            CompletableFuture<Void> allOf = CompletableFuture.allOf(userRefTasks.toArray(new CompletableFuture[0]));

                            allOf.whenComplete((result, exception) -> {
                                if (exception != null) {
                                    Log.e("FirebaseData", "Error fetching user data", exception);
                                }

                                userTasks.complete(null);
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            userTasks.completeExceptionally(error.toException());
                        }
                    });

                    userTasks.whenComplete((result, exception) -> {
                        if (exception != null) {
                            Log.e("FirebaseData", "Error completing tasks", exception);
                        }

                        // Check if all feedback queries are complete
                        if (counter.decrementAndGet() == 0) {
                            feedbackAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(Admin.class);
            }
        });

    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }

}