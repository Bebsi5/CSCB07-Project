package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cscb07_project.complaints.Complaint;
import com.example.cscb07_project.complaints.ComplaintManager;
import com.example.cscb07_project.databinding.ActivityUserComplaintScreenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserComplaintActivityScreen extends AppCompatActivity {

    private ActivityUserComplaintScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("complaints");
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();

        String username = user.getDisplayName();

        binding = ActivityUserComplaintScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.complaintSubmissionButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                String complaintText = binding.complaintInput.getText().toString();
                Complaint complaint = new Complaint(username,complaintText);
                complaint.setComplaintManager(new ComplaintManager());
                complaint.manager.submitComplaint(complaint);

            }
        } );


    }

}