package com.example.cscb07_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class POStActivityResults extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_results);
    }

    public void resultNext(View view) {
        Toast.makeText(this, "end of questionnaire. go home", Toast.LENGTH_SHORT).show();
    }
}
