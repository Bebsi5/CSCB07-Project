package com.example.cscb07_project.events;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RSVPFunctionality {

    private Context context;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public RSVPFunctionality(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    /* In db check to see if there is room available for user to rsvp.

    If yes, set event to true for the user
            increment the current participants for the event
            open up EventList class
            show a toast that it was successful.

    If no, show a toast that there's no room to rsvp.
    */
    private void rsvpForEvent(String eventId) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference eventRef = mDatabase.child("Events").child(eventId);
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                if (event != null) {
                    int currentParticipants = event.getParticipants();
                    int participantLimit = event.getParticipantLimit();

                    if (currentParticipants < participantLimit || participantLimit == 0) {
                        eventRef.child("participants").setValue(currentParticipants + 1);
                        DatabaseReference userEventRef = mDatabase.child("Users").
                                child(userId).child("Events").child(eventId);
                        userEventRef.setValue(true);

                        Toast.makeText(context, "RSVP successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Event is full. Cannot RSVP.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error getting event details.", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });
    }

    /*
    Does some null checks and checks if it's true that the user has RSVP'ed for the event.

    If yes, remove the event from the users info in db
            decrement the current participants for the event
            show a toast that the user has opted out.

    If no, call to function rsvpForEvent
            (which will handle changing the user's rsvp status to true).
     */
    protected void updatingRSVPStatus(String eventId) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference userEventRef = mDatabase.child("Users").
                child(userId).child("Events").child(eventId);
        userEventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue(boolean.class) != null && snapshot.getValue(Boolean.class)) {
                    userEventRef.removeValue();
                    mDatabase.child("Events").child(eventId).
                            child("participants").
                            addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Long currentParticipants = dataSnapshot.getValue(Long.class);
                                    if (currentParticipants != null && currentParticipants > 0) {
                                        mDatabase.child("Events").child(eventId).
                                                child("participants").
                                                setValue(currentParticipants - 1);
                                        Toast.makeText(context,
                                                "You have opted out", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(context, "Error with updating attendance", Toast.LENGTH_SHORT).show();
                                    throw error.toException();
                                }
                            });
                } else {
                    rsvpForEvent(eventId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Error getting RSVP status", Toast.LENGTH_SHORT).show();
                throw error.toException();
            }
        });
    }
}
