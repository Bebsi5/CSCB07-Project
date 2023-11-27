package com.example.cscb07_project;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class POStCheckQualify {

    public POStCheckQualify() {

    }

    public boolean creditRequirement(POStBasicInfo info) {
        return (info.getAnswer("Select how many credits you have completed currently:")).equals("4.0 or more");
    }

    public boolean calc1Group(POStGPAInfo info) {
        // System.out.println(info.getAnswer("MATA30"));
        // System.out.println(info.getAnswer("MATA31"));
        return (info.courseExists("MATA30")) || (info.courseExists("MATA31"));
    }

    public boolean calc2Group(POStGPAInfo info) {
        // System.out.println(info.getAnswer("MATA37"));
        // System.out.println(info.getAnswer("MATA36"));
        return (info.courseExists("MATA37")) || (info.courseExists("MATA36"));
    }

    public boolean linGroup(POStGPAInfo info) {
        // System.out.println(info.getAnswer("MATA22"));
        // System.out.println(info.getAnswer("MATA23"));
        return (info.courseExists("MATA22")) || (info.courseExists("MATA23"));
    }

    public boolean meetsMinMark(POStGPAInfo info, String course, double min) {
        if(info.courseExists(course)) {
            if(info.getAnswer(course) >= min) {
                return true;
            }
        }

        return false;
    }

    abstract public boolean hasAllCourses(POStGPAInfo info);
    abstract public boolean meetsGPARequirements(POStGPAInfo info);
    abstract public boolean qualifies(POStBasicInfo basic, POStGPAInfo marks);
}
