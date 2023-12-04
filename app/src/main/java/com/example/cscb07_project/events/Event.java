package com.example.cscb07_project.events;

public class Event {
    private String eventId, eventName, eventDetails, eventDate;
    private int participants, participantLimit;

    public Event() {
    }

    public Event(String eventId, String eventName, String eventDetails, String eventDate,
                 int participants, int participantLimit) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventDate = eventDate;
        this.participants = participants;
        this.participantLimit = participantLimit;
    }

    public Event(String eventName, String eventDetails, String eventDate,
                 int participantLimit, int participants) {
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.eventDate = eventDate;
        this.participantLimit = participantLimit;
        this.participants = participants;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public int getParticipantLimit() {
        return participantLimit;
    }

    public void setParticipantLimit(int participantLimit) {
        this.participantLimit = participantLimit;
    }

}


