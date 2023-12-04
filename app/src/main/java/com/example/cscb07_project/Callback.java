package com.example.cscb07_project;

public interface Callback<T> {
    void onSuccess(T data);
    void onFailure(Exception e);
}

