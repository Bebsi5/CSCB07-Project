package com.example.cscb07_project.complaints;

import java.util.List;

public interface ComplaintsCallBack {
    void onComplaintsReceived(List<Complaint> complaints);
}
