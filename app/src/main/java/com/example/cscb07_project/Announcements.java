package com.example.cscb07_project;

public class Announcements {
    private String announcementId;
    private String title;
    private String message;

    public Announcements(){

    }

    public  Announcements(String announcementId, String title, String message){
        this.title = title;
        this.message = message;
        this.announcementId = announcementId;
    }
    public Announcements(String title, String message){
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getAnnouncementId() {

        return announcementId;
    }

    public void setAnnouncementId(String AnnouncementId) {

        this.announcementId = AnnouncementId;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
