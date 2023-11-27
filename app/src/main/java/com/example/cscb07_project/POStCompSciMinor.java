package com.example.cscb07_project;

import java.util.HashMap;
import java.util.ArrayList;

public class POStCompSciMinor extends POStCheckQualify {

    public POStCompSciMinor() {
        super();
        courses.add("CSCA08");
        courses.add("CSCA48");
        // courses.add("CSCA67");
        // courses.add("MATA67");
        // courses.add("MATA22");
        // courses.add("MATA23");
        // courses.add("MATA30");
        // courses.add("MATA31");
        // courses.add("MATA32");
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {
        // for(int i = 0; i < courses.size(); i++) {
        //     String course = courses.get(i);
        //     if(info.getAnswer(course) == (-1)) {
        //         return false;
        //     }
        // }

        return linGroup(info);
    }

    @Override
    public boolean meetsGPARequirements(POStGPAInfo info) {
        return false;
    }
}
