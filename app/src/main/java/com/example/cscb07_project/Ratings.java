package com.example.cscb07_project;

/*
This class will only be used to store information
A rating will include a score, eventID and username

 */
public class Ratings {
    double score;
    String eventID;
    String username;
    String review;
    public Ratings(){
    }

    public Ratings(double score, String review, String eventID, String Username){
        this.score = score;
        this.eventID = eventID;
        this.username = Username;
        this.review = review;
    }

    public double getScore() {
        return score;
    }

    public String getEventID() {
        return eventID;
    }

    public String getUsername() {
        return username;
    }

    public String getReview() {
        return review;
    }


}
