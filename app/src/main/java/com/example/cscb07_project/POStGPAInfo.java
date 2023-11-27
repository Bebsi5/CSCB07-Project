package com.example.cscb07_project;

import java.util.HashMap;

public class POStGPAInfo extends POStQuestionnaire {

    private HashMap<String, Double> GPAInfo;

    public POStGPAInfo() {
        GPAInfo = new HashMap<String, Double>();
    }

    public void add(String question, String answer) {
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
    public void modifyAnswer(String question, String oldAnswer, String newAnswer) {
        GPAInfo.replace(question, Double.parseDouble(newAnswer));
    }

    public double getAnswer(String question) {
        if(GPAInfo.get(question)==null) {
            return 0;
        }
        else {
            return GPAInfo.get(question);
        }
    }

    @Override
    public void deleteAll() {
        GPAInfo.clear();
    }
}
