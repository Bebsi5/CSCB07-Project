package com.example.cscb07_project;

/**
 * Event model class.
 * Basically the information each event has
 */
public class Event {
    private String eventId, eventName, eventDetails;
    private Boolean rsvpBool;

    public Event() {
    }

    public Event(String eventName, String eventDetails, Boolean rsvpBool) {
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.rsvpBool = rsvpBool;
    }

    public Event(String eventId, String eventName, String eventDetails, Boolean rsvpBool) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDetails = eventDetails;
        this.rsvpBool = rsvpBool;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
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