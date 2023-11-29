package com.example.cscb07_project;

import static com.example.cscb07_project.R.*;

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


    public AnnouncementAdapter(AnnouncementList context, ArrayList<Announcements> announcementList) {
        this.context = context;
        this.announcementsList = announcementsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, announcementId;
        public TextView message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //eventId = itemView.findViewById(R.id.event_id);
            title = itemView.findViewById(id.title);
            message = itemView.findViewById(id.message);

        }
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View announcementView = LayoutInflater.from(parent.getContext()).inflate(layout.item, parent, false);
        return new AnnouncementAdapter.ViewHolder(announcementView);
    }


    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        Announcements announcement = announcementsList.get(position);

        //holder.eventId.setText(event.getEventId());
        holder.title.setText(announcement.getAnnouncementTitle());
        holder.message.setText(announcement.getAnnouncementMessage());

    }


    @Override
    public int getItemCount() {
        return announcementsList.size();
    }
}

