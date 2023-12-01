package com.example.cscb07_project;

public class POStStatsSpec extends POStCheckQualify {
    static String name = "Statistics Specialist \n(Quantitative Finance or Statistical Science)";
    public POStStatsSpec() {
        super();
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {

        if(!(info.courseExists("CSCA08"))) {
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
        
        POStGPAInfo courses = new POStGPAInfo();
        courses.add("CSCA48", Double.toString(info.getAnswer("CSCA08")));
        courses.add("CSCA67", Double.toString(info.getAnswer("CSCA67")));
        courses.add("MATA22", Double.toString(info.getAnswer("MATA22")));
        courses.add("MATA31", Double.toString(info.getAnswer("MATA31")));
        courses.add("MATA37", Double.toString(info.getAnswer("MATA37")));
        double average = courses.calculateGPA();
        //System.out.println(average);

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

        return true;
    }

}
