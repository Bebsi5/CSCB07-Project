package com.example.cscb07_project.complaints;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07_project.R;
import com.example.cscb07_project.EventRatingPage;
import com.example.cscb07_project.events.Event;
import com.example.cscb07_project.events.EventAdapter;
import com.example.cscb07_project.events.EventDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * This will be for my Complaint Adapter inside AdminComplaint
 * This is to dynamically add complaints for the admin to see.
 */
public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder>{
    Context context;
    ArrayList<Complaint> complaintList;
    DatabaseReference db;

    // constructor
    public ComplaintAdapter(Context context, ArrayList<Complaint> complaintList) {
        this.context = context;
        this.complaintList = complaintList;
    }

    /**
     * view holder for individual items in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView username,complaintField;
        CardView mainCard;

        // references to UI elements
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.complaint_item_name);
            mainCard = itemView.findViewById(R.id.admin_complaint_layout);
            complaintField = itemView.findViewById(R.id.complaint_item_text);
        }
    }

    /**
     * Inflates the layout for each item view. It creates and returns a new instance of the ViewHolder
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return
     */
    @NonNull
    @Override
    public ComplaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View complaintView = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item, parent, false);
        return new ComplaintAdapter.ViewHolder(complaintView);
    }

    /**
     * Binds data to the views within the ViewHolder for a specific item.
     */
    @Override
    public void onBindViewHolder(@NonNull ComplaintAdapter.ViewHolder holder, int position) {
        // Retrieves the Event object at the given position in the eventList
        Complaint complaint = complaintList.get(position);
        // sets texts for eventName based on the event's data
        holder.username.setText(complaint.getUsername() + ": ");
        holder.complaintField.setText(complaint.getComplaint());
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return complaintList.size();
    }
}