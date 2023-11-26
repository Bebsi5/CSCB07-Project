package com.example.cscb07_project;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicInfo extends Questionnaire {

    private HashMap<String, String> basicQuestions;

    public BasicInfo() {
        basicQuestions = new HashMap<String, String>();
    }

    public void add(String question, String answer) {
        basicQuestions.put(question, answer);
    }

    @Override
    public void remove(String question, String answer) {
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

    @Override
    public String getAnswer(String question) {
        return basicQuestions.get(question);
    }

    @Override
    public void deleteAll() {
        basicQuestions.clear();
    }
}
