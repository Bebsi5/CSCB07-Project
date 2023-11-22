package com.example.cscb07_project;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{
    Context context;
    private ArrayList<Event> eventList;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView eventName, eventId;
        public Button rsvpButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //eventId = itemView.findViewById(R.id.event_id);
            eventName = itemView.findViewById(R.id.eventName);
            rsvpButton = itemView.findViewById(R.id.rsvp_button);
        }
    }

    public EventAdapter(Context context, ArrayList<Event> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View eventView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(eventView);
    }
    /*
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }
     */

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = eventList.get(position);

        //holder.eventId.setText(event.getEventId());
        holder.eventName.setText(event.getEventName());
        holder.rsvpButton.setText(event.getRsvpBool() ? "Confirmed!" : "RSVP");

        holder.rsvpButton.setOnClickListener(v -> {
            event.setRsvpBool(!event.getRsvpBool());
            holder.rsvpButton.setText(event.getRsvpBool() ? "Confirmed!" : "RSVP");

            Log.d("ClickEvent", "Button clicked for event: " + event.getEventId());
            Log.d("ClickEvent", "Button clicked for event: " + event.getEventName());
            Log.d("ClickEvent", "Button clicked for event: " + event.getRsvpBool());
            updateDatabase(event.getEventId(), event.getRsvpBool());
        });
    }

    private void updateDatabase(String eventId, boolean rsvpStatus) {
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events").child(eventId);

        eventRef.child("rsvpBool").setValue(rsvpStatus)
                .addOnSuccessListener(aVoid -> Log.d("DatabaseUpdate", "RSVP status updated successfully"))
                .addOnFailureListener(e -> Log.e("DatabaseUpdate", "Error updating RSVP status", e));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}

