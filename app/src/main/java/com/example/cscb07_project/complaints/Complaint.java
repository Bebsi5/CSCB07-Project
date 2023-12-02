package com.example.cscb07_project.complaints;

import java.util.Date;
public class Complaint {
    public String username;
    public String complaint;
    public String id;

//    public ComplaintHandler manager;
    public Complaint(String username, String complaint, String id){
        this.username = username;
        this.complaint = complaint;
        this.id = id;
    }

    public Complaint(){

    }

    public String getUsername() {
        return username;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getId() {
        return id;
    }

//    public ComplaintHandler getManager() {
//        return manager;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public void setId(String id) {
        this.id = id;
    }
}
