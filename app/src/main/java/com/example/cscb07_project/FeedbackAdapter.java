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

        holder.textReview.setText("Review: " + feedback.getReview());
        holder.textScore.setText("Score: " + feedback.getScore());
        holder.textUsername.setText("Username: " + feedback.getUsername());
        holder.textEvent.setText("Event: " + feedback.getEvent());
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