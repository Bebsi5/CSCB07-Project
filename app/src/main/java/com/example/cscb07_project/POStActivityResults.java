package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class POStActivityResults extends AppCompatActivity implements View.OnClickListener {

    TextView qualifyDescription;
    String programName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_results);

        qualifyDescription = findViewById(R.id.resultDescription);

        POStCheckQualify post = determineRequirements(POStActivityBasic.basicInfo, POStActivityQuestions.GPAInfo);
        programName = POStActivityBasic.basicInfo.getAnswer("Select which POSt you want to apply for:");
        if(post.qualifies(POStActivityBasic.basicInfo, POStActivityQuestions.GPAInfo)) {
            qualifyDescription.setText("Qualifies for "+programName);
        } else {
            qualifyDescription.setText("Does not qualify for "+programName);
        }

    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public POStCheckQualify determineRequirements(POStBasicInfo basicInfo, POStGPAInfo GPAInfo) {
        POStCheckQualify post = null;
        String newProgram = basicInfo.getAnswer("Select which POSt you want to apply for:");
        String oldProgram = basicInfo.getAnswer("Select which program you are currently enrolled in:");

        if((newProgram.substring(0, 10)).startsWith(oldProgram)) {
            if(newProgram.equals(POStStatsMajor.name)) {
                post = new POStStatsMajor();
            } else if (newProgram.equals(POStStatsMLDSOther.name)) {
                post = new POStStatsMLDSOther();
            } else if (newProgram.equals(POStStatsSpec.name)) {
                post = new POStStatsSpec();
            }
        }
        else if ((newProgram.substring(0, 11)).startsWith(oldProgram)) {
            if(newProgram.equals(POStMathMajor.name)) {
                post = new POStMathMajor();
            } else if (newProgram.equals(POStMathSpec.name)) {
                post = new POStMathSpec();
            }
        }
        else if ((newProgram.substring(0, 16)).startsWith(oldProgram)) {
            if(newProgram.equals(POStCompSciMinor.name)) {
                post = new POStCompSciMinor();
            } else if (newProgram.equals(POStCompSciMajSpec.name)) {
                post = new POStCompSciMajSpec();
            }
        }
        else {
            post = outsideProgram(basicInfo, GPAInfo);
        }
        return post;
    }

    public String findShortest(ArrayList<String> l) {
        int len = Integer.MAX_VALUE;
        String shortest = "";

        for(String i: l) {
            if(i.length() < len) {
                len = i.length();
                shortest = i;
            }
        }
        return shortest;
    }

    public POStCheckQualify outsideProgram(POStBasicInfo basicInfo, POStGPAInfo GPAInfo) {
        String newProgram = basicInfo.getAnswer("Select which POSt you want to apply for:");
        POStCheckQualify post = null;

        if((newProgram).startsWith("Statistics")) {
            post = new POStStatsMLDSOther();
        } else if ((newProgram).startsWith("Mathematics")) {
            post = new POStMathOtherProgram();
        } else if ((newProgram).startsWith("Computer Science")) {
            post = new POStCompSciOtherProgram();
        }

        return post;
    }

    @Override
    public void onClick(View v) {
        showToast("End of questionnaire, go back to home");
    }
}
