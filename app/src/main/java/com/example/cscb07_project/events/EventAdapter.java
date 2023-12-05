package com.example.cscb07_project.events;

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

import com.example.cscb07_project.EventRatingPage;
import com.example.cscb07_project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    Context context;
    ArrayList<Event> eventList;
    RSVPFunctionality rsvpFunctionality;

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
        this.rsvpFunctionality = new RSVPFunctionality(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventName, eventDetails;
        Button rsvpButton, deleteEventButton;
        CardView mainCard;
        Button ratingButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventDetails = itemView.findViewById(R.id.event_details);
            rsvpButton = itemView.findViewById(R.id.rsvp_button);
            deleteEventButton = itemView.findViewById(R.id.delete_event_button);
            mainCard = itemView.findViewById(R.id.main_card);
            ratingButton = itemView.findViewById(R.id.event_rating_button);
        }
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
        holder.rsvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rsvpFunctionality.updatingRSVPStatus(event.getEventId());
            }
        });

        holder.ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), EventRatingPage.class);
                intent.putExtra("Event ID", event.getEventId());
                intent.putExtra("Event Name", event.getEventName());
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}