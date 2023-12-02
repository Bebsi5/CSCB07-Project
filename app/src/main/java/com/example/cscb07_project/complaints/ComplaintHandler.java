package com.example.cscb07_project.complaints;

import java.util.ArrayList;
import java.util.List;

public interface ComplaintHandler {
    void submitComplaint(Complaint complaint);
    public void getComplaints(ComplaintsCallBack callback);

}


