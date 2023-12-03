package com.example.cscb07_project;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginModel {
    interface LoginListener {
        void onLoginSuccess(boolean isAdmin);

        void onLoginFailure(String errorMessage);
    }

    void signInWithEmailAndPassword(String email, String password, FirebaseAuth mAuth, LoginListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if (currentUser != null) {
                            String userId = currentUser.getUid();
                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Boolean isAdmin = dataSnapshot.child("adminAccess").getValue(Boolean.class);
                                    listener.onLoginSuccess(isAdmin != null && isAdmin);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    listener.onLoginFailure("Database read failed: " + databaseError.getMessage());
                                }
                            });
                        }
                    } else {
                        listener.onLoginFailure("Authentication failed.");
                    }
                });
    }
}

