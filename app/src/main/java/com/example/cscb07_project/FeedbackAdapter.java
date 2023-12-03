package com.example.cscb07_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    private List<Feedback> feedbackList;

    public FeedbackAdapter(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feedback feedback = feedbackList.get(position);

        // Check if the current event name is different from the previous one
        if (position == 0 || feedback.getEvent() == null || !feedback.getEvent().equals(feedbackList.get(position - 1).getEvent())) {
            // Display the event name when it changes
            holder.textEvent.setText(feedback.getEvent());
        } else {
            // Hide the event name if it's the same as the previous one
            holder.textEvent.setVisibility(View.GONE);
        }

        holder.textReview.setText("Review: " + feedback.getReview());
        holder.textScore.setText("Score: " + feedback.getScore());
        holder.textUsername.setText("Username: " + feedback.getUsername());

    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textReview;
        public TextView textScore;
        public TextView textUsername;
        public TextView textEvent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textReview = itemView.findViewById(R.id.textReview);
            textScore = itemView.findViewById(R.id.textScore);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEvent = itemView.findViewById(R.id.textEvent);
        }
    }
}