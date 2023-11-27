package com.example.cscb07_project;

public class POStCompSciOtherProgram extends POStCheckQualify {

    public POStCompSciOtherProgram() {
        super();
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {

        if(!(info.courseExists("CSCA48"))) {
            return false;
        }
        if(!(info.courseExists("CSCA67"))) {
            return false;
        }
        if(!(info.courseExists("MATA22"))) {
            return false;
        }
        if(!(info.courseExists("MATA31"))) {
            return false;
        }
        if(!(info.courseExists("MATA37"))) {
            return false;
        }

        return true;
    }

    @Override
    public boolean meetsGPARequirements(POStGPAInfo info) {
        int counter = 0;
        double mingpa = 3.7;

        if(meetsMinMark(info, "CSCA67", mingpa)) {
            counter++;
        }
        if(meetsMinMark(info, "MATA31", mingpa)) {
            counter++;
        }

        if(counter!=2) {
            return false;
        }

        return true;
    }

    @Override
    public boolean qualifies(POStBasicInfo basic, POStGPAInfo marks) {
        if(!(hasAllCourses(marks))) {
            return false;
        }
        if(!(meetsGPARequirements(marks))) {
            return false;
        }

        return true;
    }

}
