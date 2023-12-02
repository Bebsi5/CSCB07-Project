package com.example.cscb07_project.complaints;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ComplaintManager implements ComplaintHandler {

    @Override
    public void submitComplaint(Complaint complaint){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Complaints");
        myRef.push().setValue(complaint);

    }

    public ComplaintManager() {
    }

    @Override
    public void getComplaints(ComplaintsCallBack callback) {  //Just know that this code gets all the complaints
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Complaints");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Complaint> complaintsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Complaint complaint = snapshot.getValue(Complaint.class);
                    complaintsList.add(complaint);
                }
                callback.onComplaintsReceived(complaintsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("ERROR", "random");
            }
        });
    }

    /*

    getAllComplaints(new ComplaintsCallback() {
    @Override
    public void onComplaintsReceived(List<Complaint> complaints) {
        // Code to handle the complaints list
    }
});

     */

}
