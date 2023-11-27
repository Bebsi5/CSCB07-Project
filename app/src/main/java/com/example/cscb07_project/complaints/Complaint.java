package com.example.cscb07_project.complaints;

import java.util.Date;
public class Complaint {
    private String username;
    private String complaint;
    Date date;

    public ComplaintHandler manager;
    public Complaint(String username, String complaint){
        this.username = username;
        this.complaint = complaint;
    }

    public void setComplaintManager(ComplaintHandler manager){
        this.manager = manager;
    }


}
