package com.example.cscb07_project.complaints;

import java.util.Date;
public class Complaint {
    String username;
    String complaint;
    Date date;

    public Complaint(String username, String complaint,Date date){
        this.username = username;
        this.complaint = complaint;
        this.date = date;
    }


}
