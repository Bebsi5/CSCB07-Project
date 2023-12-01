package com.example.cscb07_project;

// import java.util.HashMap;
// import java.util.ArrayList;

public class POStCompSciMinor extends POStCheckQualify {
    static String name = "Computer Science Minor";
    public POStCompSciMinor() {
        super();
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {
        // for(int i = 0; i < courses.size(); i++) {
        //     String course = courses.get(i);
        //     if(info.getAnswer(course) == (-1)) {
        //         return false;
        //     }
        // }

        if(!(info.courseExists("CSCA08"))) {
            return false;
        }
        if(!(info.courseExists("CSCA48"))) {
            return false;
        }
        if(!(info.courseExists("CSCA67"))) {
            return false;
        }
        if(!(linGroup(info))) {
            return false;
        }
        if(!(calc1Group(info))) {
            return false;
        }

        return true;
    }

    @Override
    public boolean meetsGPARequirements(POStGPAInfo info) {
        return true;
    }

    @Override
    public boolean qualifies(POStBasicInfo basic, POStGPAInfo marks) {
        if(!(hasAllCourses(marks))) {
            return false;
        }
        if(!(creditRequirement(basic))) {
            return false;
        }
        if(!(meetsGPARequirements(marks))) {
            return false;
        }

        return true;
    }
}
