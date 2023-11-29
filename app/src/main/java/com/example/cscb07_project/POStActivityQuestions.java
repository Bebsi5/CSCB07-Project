package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class POStActivityQuestions extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_questions);
    }

    public void questionNext(View view) {
        Intent intent2 = new Intent(this, POStActivityResults.class);
        startActivity(intent2);
    }

}
