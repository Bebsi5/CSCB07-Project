package com.example.cscb07_project;

import java.util.HashMap;
import java.util.ArrayList;

public class POStCompSciMinor extends POStCheckQualify {

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {
        for(int i = 0; i < courses.size(); i++) {
            String course = courses.get(i);
            if(info.getAnswer(course) == (-1)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean meetsGPARequirements(POStGPAInfo marks) {
        return false;
    }
}
