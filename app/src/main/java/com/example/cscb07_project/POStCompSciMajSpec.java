package com.example.cscb07_project;

public class POStCompSciMajSpec extends POStCheckQualify {
    static String name = "Computer Science Major/Specialist";
    public POStCompSciMajSpec() {
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
        double mingpa = 1.7;
        POStGPAInfo courses = new POStGPAInfo();
        courses.add("CSCA48", Double.toString(info.getAnswer("CSCA48")));
        courses.add("CSCA67", Double.toString(info.getAnswer("CSCA67")));
        courses.add("MATA22", Double.toString(info.getAnswer("MATA22")));
        courses.add("MATA31", Double.toString(info.getAnswer("MATA31")));
        courses.add("MATA37", Double.toString(info.getAnswer("MATA37")));
        double average = courses.calculateGPA();

        if(meetsMinMark(info, "CSCA67", mingpa)) {
            counter++;
        }
        if(meetsMinMark(info, "MATA22", mingpa)) {
            counter++;
        }
        if(meetsMinMark(info, "MATA37", mingpa)) {
            counter++;
        }

        if(!(meetsMinMark(info, "CSCA48", 3.0))) {
            return false;
        }

        if(counter<2) {
            return false;
        }

        if(average < 2.5) {
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
        if(!(creditRequirement(basic))) {
            return false;
        }

        return true;
    }
}
