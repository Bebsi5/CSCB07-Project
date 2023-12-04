package com.example.cscb07_project.complaints;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cscb07_project.R;
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

        Button delete;

        // references to UI elements
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.complaint_item_name);
            mainCard = itemView.findViewById(R.id.admin_complaint_layout);
            complaintField = itemView.findViewById(R.id.complaint_item_text);
            delete = itemView.findViewById(R.id.complaint_delete);
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
        // Retrieves the Complaint object at the given position in the ComplaintList
        Complaint complaint = complaintList.get(position);
        holder.username.setText(complaint.getUsername());
        holder.complaintField.setText(complaint.getComplaint()); // Just setting my textfields to match the complaint
        holder.delete.setOnClickListener(new View.OnClickListener() { //Adding Logic for delete Button
            @Override
            public void onClick(View view) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Complaints");
            ref.child(complaint.getKey()).removeValue();
            complaintList.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            Toast.makeText(context.getApplicationContext(), "Complaint Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return complaintList.size();
    }
}