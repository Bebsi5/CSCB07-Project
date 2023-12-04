package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cscb07_project.complaints.Complaint;
import com.example.cscb07_project.complaints.ComplaintHandler;
import com.example.cscb07_project.complaints.ComplaintManager;
import com.example.cscb07_project.databinding.ActivityUserComplaintScreenBinding;
import com.example.cscb07_project.events.EventList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserComplaintActivityScreen extends AppCompatActivity {

    private ActivityUserComplaintScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference usersRef = database.getReference("Users");
        binding = ActivityUserComplaintScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.complaintSubmissionButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                String complaintText = binding.complaintInput.getText().toString();
                if (complaintText.isEmpty()) { //Checking whether the user submitted an empty complaint
                    Toast.makeText(UserComplaintActivityScreen.this, "Please Input A Complaint First", Toast.LENGTH_SHORT).show();
                } else {
                    usersRef.child(user.getUid()).child("name").addListenerForSingleValueEvent(new ValueEventListener() { // ALL THIS IS JUST TO GET THE NAME FIELD
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String username = dataSnapshot.getValue(String.class);
                            Complaint complaint = new Complaint(username, complaintText, user.getUid());// Creating a new complaint instance with all the values
                            ComplaintHandler manager;
                            manager = new ComplaintManager();
                            Log.d("TESTING COMPLAINT SUBMISSION", username + " " + complaintText + " " + user.getUid());
                            manager.submitComplaint(complaint); //We pass on the complaint to the manager so it can submit it
                            Toast.makeText(UserComplaintActivityScreen.this, "Complaint Submitted", Toast.LENGTH_SHORT).show();
                            binding.complaintInput.setText(""); //After a confirmation Toast, reset the input text field

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("TESTING COMPLAINT SUBMISSION", "FAILED");
                            Toast.makeText(UserComplaintActivityScreen.this, "Error Complaint Could Not Be Submitted", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        } );

        binding.complaintBack.setOnClickListener(new View.OnClickListener() { //Back Button, when pressed go to previous screen
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

}