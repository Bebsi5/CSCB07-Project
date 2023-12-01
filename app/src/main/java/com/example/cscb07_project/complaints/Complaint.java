package com.example.cscb07_project.complaints;

import java.util.Date;
public class Complaint {
    private String username;
    private String complaint;
    String ID;

    public ComplaintHandler manager;
    public Complaint(String username, String complaint, String ID){
        this.username = username;
        this.complaint = complaint;
        this.ID = ID;
    }

    public void setComplaintManager(ComplaintHandler manager){
        this.manager = manager;
    }

    public String getUsername() {
        return username;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getID() {
        return ID;
    }
}
