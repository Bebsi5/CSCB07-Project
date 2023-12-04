package com.example.cscb07_project.complaints;

import java.util.ArrayList;
import java.util.List;
/*
This is just to implement the class to retrieve all the complaints from the database
 */
public interface ComplaintsCallBack {
    void onComplaintsReceived(ArrayList<Complaint> complaints);
}
