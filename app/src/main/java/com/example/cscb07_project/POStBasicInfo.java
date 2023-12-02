package com.example.cscb07_project;

import android.util.Log;

import java.util.HashMap;

public class POStBasicInfo extends POStQuestionnaire {

    private HashMap<String, String> basicQuestions;
    private static final String TAG = POStBasicInfo.class.getName();

    public POStBasicInfo() {
        basicQuestions = new HashMap<String, String>();
    }

    public void add(String question, String answer) {
        basicQuestions.put(question, answer);
    }

    @Override
    public void remove(String question) {
        basicQuestions.remove(question);
    }

    @Override
    public void modifyQuestion(String oldQuestion, String newQuestion) {
        String tempValue = getAnswer(oldQuestion);
        basicQuestions.remove(oldQuestion);
        basicQuestions.put(newQuestion, tempValue);
    }

    @Override
    public void modifyAnswer(String question, String oldAnswer, String newAnswer) {
        basicQuestions.replace(question, newAnswer);
    }

    public String getAnswer(String question) { return basicQuestions.get(question);
    }

    @Override
    public void deleteAll() {
        basicQuestions.clear();
    }

    @Override
    public int getLength() {
        return basicQuestions.size();
    }

    @Override
    public void printMap() {
        for (String question : basicQuestions.keySet()) {
            //System.out.println("key: " + question + " value: " + basicQuestions.get(question));
            Log.d(TAG, "key: " + question + "\n--value: " + basicQuestions.get(question));
        }
    }
}
