package com.example.cscb07_project;

public class Event {
    private String eventId, eventName;
    private String eventDetails;
    private Boolean rsvpBool;

    public Event(String eventId, String eventName, Boolean rsvpBool) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.rsvpBool = rsvpBool;
    }
    public Event() {
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

    public Boolean getRsvpBool() {
        return rsvpBool;
    }

    public void setRsvpBool(Boolean rsvpBool) {
        this.rsvpBool = rsvpBool;
    }
}

