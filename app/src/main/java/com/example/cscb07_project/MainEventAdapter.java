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

import com.example.cscb07_project.events.Event;
import com.example.cscb07_project.events.EventAdapter;
import com.example.cscb07_project.events.EventDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainEventAdapter extends RecyclerView.Adapter<MainEventAdapter.ViewHolder>{
    Context context;
    ArrayList<Event> eventList;

    public MainEventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView eventName, eventDetails;
        CardView mainCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_home_name);
            eventDetails = itemView.findViewById(R.id.event_home_details);
            mainCard = itemView.findViewById(R.id.main_card);


        }
    }

    @NonNull
    @Override
    public MainEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_home_item, parent, false);
        return new MainEventAdapter.ViewHolder(eventView);
    }


    @Override
    public void onBindViewHolder(@NonNull MainEventAdapter.ViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.eventName.setText(event.getEventName());
        holder.eventDetails.setText(event.getEventDetails());

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                intent.putExtra("Event ID", event.getEventId());
                v.getContext().startActivity(intent);
            }
        });
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
