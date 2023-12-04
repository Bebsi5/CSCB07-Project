package com.example.cscb07_project.complaints;

import java.util.ArrayList;
import java.util.List;

/*
This class exists to easily extend the complaint class
If I need a new way to submit or get complaints I can just add a new manager class
and implement the methods there which extend complaint Handler.
 */
public interface ComplaintHandler {
    void submitComplaint(Complaint complaint);
    public void getComplaints(ComplaintsCallBack callback);

}


