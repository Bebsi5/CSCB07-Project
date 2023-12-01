package com.example.cscb07_project;

public class POStStatsMLDSOther extends POStStatsSpec {
    static String name = "Statistics Specialist \n(Machine Learning and Data Specialist)";
    public POStStatsMLDSOther() {
        super();
    }

    @Override
    public boolean qualifies(POStBasicInfo basic, POStGPAInfo marks) {
        if(!(hasAllCourses(marks))) {
            return false;
        }
        if(!(meetsGPARequirements(marks))) {
            return false;
        }
        if((marks.courseExists("CSCA48"))==false || (meetsMinMark(marks, "CSCA48", 3.0)) == false) {
            return false;
        }

        return true;
    }
}
