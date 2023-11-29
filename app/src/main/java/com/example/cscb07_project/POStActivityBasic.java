package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class POStActivityBasic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_basic);
    }

    public void basicNext(View view) {
        Intent intent1 = new Intent(this, POStActivityQuestions.class);
        startActivity(intent1);
    }
}
