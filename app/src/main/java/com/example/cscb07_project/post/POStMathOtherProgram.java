package com.example.cscb07_project.post;

public class POStMathOtherProgram extends POStCheckQualify {
    public POStMathOtherProgram() {
        super();
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {

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
