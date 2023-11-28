public class POStStatsMajor extends POStCheckQualify {
    
    public POStStatsMajor() {
        super();
    }

    @Override
    public boolean calc1Group(POStGPAInfo info) {
        return (info.courseExists("MATA30")) || (info.courseExists("MATA31")) || (info.courseExists("MATA32"));
    }
    
    public boolean csGroup(POStGPAInfo info) {
        return (info.courseExists("CSCA08")) || (info.courseExists("CSCA20"));
    }

    @Override
    public boolean hasAllCourses(POStGPAInfo info) {

        if(!(info.courseExists("MATA22"))) {
            return false;
        }
        if(!(calc2Group(info))) {
            return false;
        }
        if(!(calc1Group(info))) {
            return false;
        }
        if(!(csGroup(info))) {
            return false;
        }
        
        return true;
    }

    @Override
    public boolean meetsGPARequirements(POStGPAInfo info) {
        POStGPAInfo courses = new POStGPAInfo();
        
        POStGPAInfo calc1 = new POStGPAInfo();
        calc1.add("MATA30", Double.toString(info.getAnswer("MATA30")));
        calc1.add("MATA31", Double.toString(info.getAnswer("MATA31")));
        calc1.add("MATA32", Double.toString(info.getAnswer("MATA32")));

        POStGPAInfo calc2 = new POStGPAInfo();
        calc1.add("MATA36", Double.toString(info.getAnswer("MATA36")));
        calc1.add("MATA37", Double.toString(info.getAnswer("MATA37")));

        POStGPAInfo cs = new POStGPAInfo();
        calc1.add("CSCA08", Double.toString(info.getAnswer("CSCA08")));
        calc1.add("CSCA20", Double.toString(info.getAnswer("CSCA20")));

        String calc1Course = calc1.getHighestInGroup();
        String calc2Course = calc2.getHighestInGroup();
        String csCourse = cs.getHighestInGroup();

        courses.add(calc1Course, Double.toString(info.getAnswer(calc1Course)));
        courses.add("MATA22", Double.toString(info.getAnswer("MATA22")));
        courses.add(calc2Course, Double.toString(info.getAnswer(calc2Course)));
        courses.add(csCourse, Double.toString(info.getAnswer(csCourse)));
        double average = courses.calculateGPA();
        //System.out.println(average);

        if(average < 2.3) {
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
