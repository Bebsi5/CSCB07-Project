package com.example.cscb07_project;

public abstract class POStQuestionnaire {
    abstract public void add(String question, String answer);
    abstract public void remove(String question);
    abstract public void modifyQuestion(String oldQuestion, String newQuestion);
    abstract public void modifyAnswer(String question, String oldAnswer, String newAnswer);
    abstract public void deleteAll();
    abstract public int getLength();
    abstract public void printMap();
}
