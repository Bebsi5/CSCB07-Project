package com.example.cscb07_project;

import static com.example.cscb07_project.R.*;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>{
    Context context;
    private ArrayList<Announcements> announcementsList;

    CardView mainCard;


    public AnnouncementAdapter(AnnouncementList context, ArrayList<Announcements> announcementsList) {
        this.context = context;
        this.announcementsList = announcementsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title, announcementId;
        Button readButton, deleteAnnouncementButton;
        public TextView message;
        CardView mainCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            readButton = itemView.findViewById(R.id.read_button);
            deleteAnnouncementButton = itemView.findViewById(R.id.delete_announcement_button);
            mainCard = itemView.findViewById(R.id.main_card);


        }
    }

    @NonNull
    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View announcementView = LayoutInflater.from(parent.getContext()).inflate(layout.announcement_item, parent, false);
        return new AnnouncementAdapter.ViewHolder(announcementView);
    }


    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder holder, int position) {
        Announcements announcement = announcementsList.get(position);

        //holder.eventId.setText(event.getEventId());
        holder.title.setText(announcement.getAnnouncementTitle());
        holder.message.setText(announcement.getAnnouncementMessage());
        holder.readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnnouncementDetails.class);
                intent.putExtra("Event ID", announcement.getAnnouncementId());
                Log.d("ClickEvent", "Button clicked for event: " + announcement.getAnnouncementTitle());
                Log.d("ClickEvent", "Button clicked for event: " + announcement.getAnnouncementMessage());

                v.getContext().startActivity(intent);
            }
        });
                holder.deleteAnnouncementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Announcement").child(announcement.getAnnouncementId());
                     eventRef.removeValue();
                }
            });

        }

                //Returns the total number of items in the RecyclerView
        @Override
        public int getItemCount() {
            return announcementsList.size();
        }
    }
