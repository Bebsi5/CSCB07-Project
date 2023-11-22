package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cscb07_project.complaints.Complaint;
import com.example.cscb07_project.databinding.ActivityUserComplaintScreenBinding;

public class UserComplaintActivityScreen extends AppCompatActivity {

    private ActivityUserComplaintScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserComplaintScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.complaintSubmissionButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                String complaintText = binding.complaintInput.getText().toString();
                complaintSub(complaintText);
            }
        } );


    }

    public void complaintSub(String complaintText){
        //Complaint complaint = new Complaint(user:username, complaint:complaintText, Date:date);
        // Save it down
    }
}