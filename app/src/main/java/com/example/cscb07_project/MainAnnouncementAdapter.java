package com.example.cscb07_project;

import static com.example.cscb07_project.R.id;
import static com.example.cscb07_project.R.layout;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAnnouncementAdapter extends RecyclerView.Adapter<MainAnnouncementAdapter.ViewHolder>{
    Context context;
    private ArrayList<Announcements> announcementsList;

    public MainAnnouncementAdapter(Context context, ArrayList<Announcements> announcementsList) {
        this.context = context;
        this.announcementsList = announcementsList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView announceName;
        CardView mainCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            announceName = itemView.findViewById(id.announcement_home_name);
            mainCard = itemView.findViewById(id.main_card);

        }
    }

    @NonNull
    @Override
    public MainAnnouncementAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View announcementView = LayoutInflater.from(parent.getContext()).inflate(layout.announcement_home_item, parent, false);
        return new MainAnnouncementAdapter.ViewHolder(announcementView);
    }


    @Override
    public void onBindViewHolder(@NonNull MainAnnouncementAdapter.ViewHolder holder, int position) {
        Announcements announcement = announcementsList.get(position);

        holder.announceName.setText(announcement.getTitle());

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnnouncementDetails.class);
                intent.putExtra("Announcement ID", announcement.getAnnouncementId());
                Log.d("ClickEvent", "Button clicked for event: " + announcement.getTitle());
                Log.d("ClickEvent", "Button clicked for event: " + announcement.getMessage());

                v.getContext().startActivity(intent);
            }
        });
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return announcementsList.size();
    }
}
