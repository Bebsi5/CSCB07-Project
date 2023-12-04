package com.example.cscb07_project;

public class Announcements {
    private String announcementId;
    private String title;
    private String message;


    public  Announcements(String announcementId, String title, String message){
        this.title = title;
        this.message = message;
        this.announcementId = announcementId;
    }
    public Announcements(String title, String message){
        this.title = title;

        this.message = message;
    }

    public String getAnnouncementId() {

        return announcementId;
    }

    public void setAnnouncementId(String AnnouncementId) {

        this.announcementId = AnnouncementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
