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

/**
 * EventAdapter, is an adapter for a RecyclerView
 * It adapts the list of events to the RecyclerView by creating views for individual items
 * and binding the data to those views
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    Context context;
    ArrayList<Event> eventList;
    RSVPFunctionality rsvpFunctionality;
    boolean adminAccess;
    public EventAdapter(Context context, ArrayList<Event> eventList, boolean adminAccess) {
        this.context = context;
        this.eventList = eventList;
        this.adminAccess = adminAccess;
        this.rsvpFunctionality = new RSVPFunctionality(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView eventName;
        Button rsvpButton, deleteEventButton;
        CardView mainCard;
        Button ratingButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
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
        // Retrieves the Event object at the given position in the eventList
        Event event = eventList.get(position);
        Log.e("eventadapteronbindview", "event id?: " + event);

        holder.eventName.setText(event.getEventName());

        // when the event is clicked, navigates to EventDetails class
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                intent.putExtra("Event ID", event.getEventId());

                Log.d("ClickEvent", "Button clicked for event: " + event.getEventName());
                Log.d("ClickEvent", "Button clicked for event: " + event.getEventDetails());

                v.getContext().startActivity(intent);
            }
        });
        // when the rsvp button is clicked, updates student's rsvp status
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

        // toggling delete button visibility depending on user permissions
//        if (adminAccess) {
//            holder.deleteEventButton.setVisibility(View.VISIBLE);
//            // deletes an event from the database
//            // but does NOT delete the event from the student data
//            /*
//            Ex: Events
//                - id 1 (deleted)
//                - id 2
//
//                Users
//                - student 1
//                    - Events
//                        - id 1: true (still exists for the student)
//                        - id 2: true
//                - student 2
//             */
//            // will cause bugs
//            holder.deleteEventButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(event.getEventId());
//                    eventRef.removeValue();
//                }
//            });
//        } else {
//            holder.deleteEventButton.setVisibility(View.GONE);
//        }
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}