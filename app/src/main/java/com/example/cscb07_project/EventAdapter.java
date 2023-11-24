package com.example.cscb07_project;

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
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * EventAdapter, is an adapter for a RecyclerView
 * It adapts the list of events to the RecyclerView by creating views for individual items
 * and binding the data to those views
 */
public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    Context context;
    ArrayList<Event> eventList;

    /** Constructor for EventAdapter
     *
     * @param context Holds a reference to the context in which the adapter is used
     * @param eventList  Represents the list of events that the adapter will display
     */
    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    /**
     * view holder for individual items in the RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView eventName, eventId, eventDetails;
        Button rsvpButton;
        CardView mainCard;

        // references to UI elements
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //eventId = itemView.findViewById(R.id.event_id);
            eventName = itemView.findViewById(R.id.event_name);
            rsvpButton = itemView.findViewById(R.id.rsvp_button);
            //eventDetails = itemView.findViewById(R.id.event_details);
            mainCard = itemView.findViewById(R.id.main_card);
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
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View eventListItems = layoutInflater .inflate(R.layout.event_item, parent, false);

        ViewHolder view_holder = new ViewHolder(eventListItems);

        return view_holder;

         */
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        return new ViewHolder(eventView);
    }

    /**
     * Binds data to the views within the ViewHolder for a specific item.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Retrieves the Event object at the given position in the eventList
        Event event = eventList.get(position);

        // sets texts for eventName and rsvpButton based on the event's data
        holder.eventName.setText(event.getEventName());
        holder.rsvpButton.setText(event.getRsvpBool() ? "Confirmed!" : "RSVP");

        // click listeners for the item view mainCard and the rsvpButton
        //  when item view mainCard clicked, navigates to EventDetails class
        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventDetails.class);
                /*intent.putExtra("Title", event.getEventName());
                intent.putExtra("Description", event.getEventDetails());
                intent.putExtra("RSVP Button", event.getRsvpBool() ? "Confirmed!" : "RSVP");*/
                intent.putExtra("Event ID", event.getEventId());

                Log.d("ClickEvent", "Button clicked for event: " + event.getEventName());
                Log.d("ClickEvent", "Button clicked for event: " + event.getEventDetails());

                v.getContext().startActivity(intent);
            }
        });

        // when rsvpButton button clicked, updates the RSVP status in the database and in the UI
        holder.rsvpButton.setOnClickListener(v -> {
            event.setRsvpBool(!event.getRsvpBool());
            holder.rsvpButton.setText(event.getRsvpBool() ? "Confirmed!" : "RSVP");

            Log.d("ClickEvent", "Button clicked for event: " + event.getEventId());
            Log.d("ClickEvent", "Button clicked for event: " + event.getEventName());
            Log.d("ClickEvent", "Button clicked for event: " + event.getRsvpBool());
            updateDatabase(event.getEventId(), event.getRsvpBool());
        });
    }

    // Updates the RSVP status of an event in the Firebase Realtime Database
    private void updateDatabase(String eventId, boolean rsvpStatus) {
        // Gets a reference to the specific event in the database using the event ID
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);

        // Updates the rsvpBool field with the new RSVP status
        eventRef.child("rsvpBool").setValue(rsvpStatus)
                // log success or failure messages.
                .addOnSuccessListener(aVoid -> Log.d("DatabaseUpdate", "RSVP status updated successfully"))
                .addOnFailureListener(e -> Log.e("DatabaseUpdate", "Error updating RSVP status", e));
    }

    //Returns the total number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

