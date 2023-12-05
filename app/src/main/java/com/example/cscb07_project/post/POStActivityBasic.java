package com.example.cscb07_project.post;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cscb07_project.MainActivity;
import com.example.cscb07_project.R;

public class POStActivityBasic extends AppCompatActivity implements View.OnClickListener {

    RadioGroup rg1, rg2, rg3;
    int ans1, ans2, ans3;
    private static final String TAG = POStActivityBasic.class.getName();

    static POStBasicInfo basicInfo = new POStBasicInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_basic);

        rg1 = (RadioGroup) findViewById(R.id.answerRadioGroup1);
        rg2 = (RadioGroup) findViewById(R.id.answerRadioGroup2);
        rg3 = (RadioGroup) findViewById(R.id.answerRadioGroup3);

        addListenerOnRadioGrp(rg1);
        addListenerOnRadioGrp(rg2);
        addListenerOnRadioGrp(rg3);

    }

    private void addListenerOnRadioGrp(RadioGroup rg) {
        //RadioGroup rg = (RadioGroup) findViewById(R.id.answerRadioGroup1);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonID)
            {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                String selectedText = getStringAtButton(radioGroup, radioButtonID);
                int position = radioGroup.indexOfChild(radioButton);
                //Toast.makeText(POStActivityBasic.this, "rg button: "+selectedText, Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "rg button: "+position);
            }
        });
    }

    public String getStringAtButton(RadioGroup radioGroup, int radioButtonID) {
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

        return (String) radioButton.getText();
    }

    public void saveAnswers(int ans1, int ans2, int ans3) {
        basicInfo.add("Select which POSt you want to apply for:", getStringAtButton(rg1, ans1));
        basicInfo.add("Select which program you are currently enrolled in:", getStringAtButton(rg2, ans2));
        basicInfo.add("Select how many credits you have completed currently:", getStringAtButton(rg3, ans3));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        ans1 = rg1.getCheckedRadioButtonId();
        ans2 = rg2.getCheckedRadioButtonId();
        ans3 = rg3.getCheckedRadioButtonId();

        if (ans1 == -1) {
            showToast("Please fill in Question 1.");
        } else if (ans2 == -1) {
            showToast("Please fill in Question 2.");
        } else if (ans3 == -1) {
            showToast("Please fill in Question 3.");
        } else {
            saveAnswers(ans1, ans2, ans3);
            //basicInfo.printMap();

            Intent intent1 = new Intent(this, POStActivityQuestions.class);
            //Toast.makeText(this, "going to next page", Toast.LENGTH_SHORT).show();
            startActivity(intent1);
        }
    }

    public void onClickBack(View v) {
        // Start MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to remove it from the back stack
    }
}

