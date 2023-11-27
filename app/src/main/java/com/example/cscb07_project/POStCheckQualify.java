package com.example.cscb07_project;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class POStCheckQualify {

    ArrayList<String> courses;

    public boolean creditRequirement(POStBasicInfo info) {
        return (info.getAnswer("Select how many credits you have completed currently:")).equals("4.0 or more");
    }

    abstract public boolean hasAllCourses(POStGPAInfo info);
    abstract public boolean meetsGPARequirements(POStGPAInfo marks);
}
