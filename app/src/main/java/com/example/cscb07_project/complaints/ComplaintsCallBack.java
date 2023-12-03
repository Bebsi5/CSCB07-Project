package com.example.cscb07_project.complaints;

import java.util.ArrayList;
import java.util.List;

public interface ComplaintsCallBack {
    void onComplaintsReceived(ArrayList<Complaint> complaints);
}
