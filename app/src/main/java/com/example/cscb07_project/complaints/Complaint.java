package com.example.cscb07_project.complaints;

import java.util.Date;
public class Complaint {
    String username;
    String complaint;
    Date date;

    ComplaintManager manager;
    public Complaint(String username, String complaint,Date date){
        this.username = username;
        this.complaint = complaint;
        this.date = date;
    }

    public void setComplaintManager(ComplaintManager manager){
        this.manager = manager;
    }


}
