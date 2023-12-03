package com.example.cscb07_project.complaints;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.cscb07_project.R;
import com.example.cscb07_project.complaints.Complaint;
import com.example.cscb07_project.complaints.ComplaintAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminComplaint extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    ComplaintAdapter complaintAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_complaint);

        // connecting the widget recyclerView based on complaintList
        recyclerView = findViewById(R.id.complaintList);
        // initializing eventList so it holds an array of Events
//        complaintsList = new ArrayList<>();
//        complaintAdapter = new ComplaintAdapter(this, complaintsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ComplaintManager manager = new ComplaintManager();
        manager.getComplaints(new ComplaintsCallBack() {
            public void onComplaintsReceived(ArrayList<Complaint> complaints) {
                complaintAdapter = new ComplaintAdapter(AdminComplaint.this, complaints);
                recyclerView.setAdapter(complaintAdapter);
            }
        });


    }

}