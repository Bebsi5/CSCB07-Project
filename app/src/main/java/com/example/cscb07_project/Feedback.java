package com.example.cscb07_project;

public class Feedback {
    private String review;
    private int score;
    private String username;

    private String event;

    public Feedback() {
        // Required empty public constructor for Firebase
    }

    public Feedback(String review, int score, String username, String event) {
        this.review = review;
        this.score = score;
        this.username = username;
        this.event = event;
    }

    public String getReview() {
        return review;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    public String getEvent() {
        return event;
    }
}
