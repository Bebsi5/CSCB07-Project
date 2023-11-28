package com.example.cscb07_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>{
    Context context;
    private ArrayList<Announcements> announcementsList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, announcementId;
        public TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //eventId = itemView.findViewById(R.id.event_id);
            title = itemView.findViewById(R.id.title);
            message = itemView.findViewById(R.id.message);
        }
    }

    public AnnouncementAdapter(Context context, ArrayList<Announcements> announcementsList) {
        this.context = context;
        this.announcementsList = announcementsList;
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View announcementView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new AnnouncementAdapter.ViewHolder(announcementView);
    }
    /*
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }
     */

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        Announcements announcement = announcementsList.get(position);

        //holder.eventId.setText(event.getEventId());
        holder.title.setText(announcement.getAnnouncementTitle());
        holder.message.setText(announcement.getAnnouncementMessage());


    }

    private void updateDatabase(String announcementId, String title, String message) {
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Announcements").child(announcementId);

        eventRef.child("title").setValue(this.title)
                .addOnSuccessListener(aVoid -> Log.d("DatabaseUpdate", "Announcement title set successfully"))
                .addOnFailureListener(e -> Log.e("DatabaseUpdate", "Error sending announcement", e));

        eventRef.child("message").setValue(this.message)
                .addOnSuccessListener(aVoid -> Log.d("DatabaseUpdate", "Announcement sent successfully"))
                .addOnFailureListener(e -> Log.e("DatabaseUpdate", "Error sending announcement", e));

    }

    @Override
    public int getItemCount() {
        return announcementsList.size();
    }
}

