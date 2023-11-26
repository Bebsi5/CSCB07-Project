package com.example.cscb07_project;

import java.util.ArrayList;

public class POStQuestions {
    private ArrayList<String> questions;

    public void Questions() {
        questions = new ArrayList<String>();
    }

    public void addQuestion(String question) {
        questions.add(question);
    }

    public void removeQuestion(String question) {
        questions.remove(question);
    }

    public void modifyQuestion(String oldQuestion, String newQuestion) {
        questions.set(getIndex(oldQuestion), newQuestion);
    }

    public void deleteQuestions() {
        questions.clear();
    }

    public int getIndex(String question) {
        return questions.indexOf(question);
    }
}
