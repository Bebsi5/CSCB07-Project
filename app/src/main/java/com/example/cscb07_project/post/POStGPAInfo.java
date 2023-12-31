package com.example.cscb07_project.post;

import android.util.Log;

import java.util.HashMap;

public class POStGPAInfo extends POStQuestionnaire {

    private HashMap<String, Double> GPAInfo;
    private static final String TAG = POStGPAInfo.class.getName();

    public POStGPAInfo() {
        GPAInfo = new HashMap<String, Double>();
    }

    public void add(String question, String answer) {
        if(answer.equals("Did not take")) {
            return;
        }

        GPAInfo.put(question, Double.parseDouble(answer));
    }

    @Override
    public void remove(String question) {
        GPAInfo.remove(question);
    }

    @Override
    public void modifyQuestion(String oldQuestion, String newQuestion) {
        double tempValue = GPAInfo.get(oldQuestion);
        if(GPAInfo.get(oldQuestion)==null) {
            tempValue = 0;
        }
        GPAInfo.remove(oldQuestion);
        GPAInfo.put(newQuestion, tempValue);
    }

    @Override
    public void modifyAnswer(String question, String newAnswer) {
        GPAInfo.replace(question, Double.parseDouble(newAnswer));
    }

    public double getAnswer(String question) {
        if(GPAInfo.get(question)==null) {
            return -1;
        }
        else {
            return GPAInfo.get(question);
        }
    }

    @Override
    public void deleteAll() {
        GPAInfo.clear();
    }

    @Override
    public int getLength() {
        return GPAInfo.size();
    }
    
    @Override
    public void printMap() {
        for (String question : GPAInfo.keySet()) {
            //System.out.println("key: " + question + " value: " + GPAInfo.get(question));
            Log.d(TAG, "key: " + question + "\n--value: " + GPAInfo.get(question));
        }
    }

    //method calculateGPA logic should be correct: tested Nov 26
    public double calculateGPA() {
        double total = 0;
        int noTakeCount = 0;

        for (double i : GPAInfo.values()) {
            if(i != (-1)) {
                total += i;
            }
            else {
                noTakeCount++;
            }
        }

        return total/(GPAInfo.size() - noTakeCount);
    }

    //method courseExists tested Nov 26
    //warning areas for testing: !=-1, method getAnswer when null
    public boolean courseExists(String course) {
        return ((getAnswer(course))!=(-1));
    }

    public String getHighestInGroup() {
        String highest = "";
        double max = 0;
        
        for (String i : GPAInfo.keySet()) {
            if(getAnswer(i) < max) {
                max = getAnswer(i);
                highest = i;
            }
        }

        return highest;
    }
}
