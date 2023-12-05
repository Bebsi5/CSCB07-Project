package com.example.cscb07_project.post;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cscb07_project.MainActivity;
import com.example.cscb07_project.R;

import java.util.ArrayList;
import java.util.Arrays;

public class POStActivityQuestions extends AppCompatActivity implements View.OnClickListener {

    TextView questionTextView;
    RadioGroup rg;
    private ArrayList<String> questions = new ArrayList<String>(
            Arrays.asList("CSCA08 - Introduction to Computer Science I",
                          "CSCA20 - Introduction to Programming",
                          "CSCA48 - Introduction to Computer Science II",
                          "CSCA67/MATA67 - Discrete Mathematics",
                          "MATA22 - Linear Algebra I for Mathematical Sciences",
                          "MATA23 - Linear Algebra I",
                          "MATA30 - Calculus I for Physical Sciences",
                          "MATA31 - Calculus I for Mathematical Sciences",
                          "MATA32 - Calculus for Management I",
                          "MATA36 - Calculus II for Physical Sciences",
                          "MATA37 - Calculus II for Mathematical Sciences"));

    int questionIndex = 0;
    int ans;
    static POStGPAInfo GPAInfo = new POStGPAInfo();
    private static final String TAG = POStActivityQuestions.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity_questions);
        questionTextView = findViewById(R.id.question); //needs to be put in onCreate, not outside of function
        rg = findViewById(R.id.answerRadioGroup);

        loadQuestion();
    }

    private void loadQuestion() {
        questionTextView.setText(questions.get(questionIndex));
        rg.clearCheck();
    }

    public String getStringAtButton(RadioGroup radioGroup, int radioButtonID) {
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);

        return (String) radioButton.getText();
    }

    public void saveAnswers(String question, int ans) {
        GPAInfo.add(question.substring(0, 6), getStringAtButton(rg, ans));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        ans = rg.getCheckedRadioButtonId();

        if (ans == -1) {
            showToast("Please fill in the question.");
        } else {
            saveAnswers(questions.get(questionIndex), ans);
            questionIndex++;

            if (ans == -1) {
                showToast("Please fill in the question.");
            } else if (questionIndex == questions.size()) {
                //GPAInfo.printMap();

                //Toast.makeText(this, "going to next page", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(this, POStActivityResults.class);
                startActivity(intent2);
            } else {
                loadQuestion();
            }
        }
    }

    public void onClickBack(View v) {
        // Start MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity to remove it from the back stack
    }

}
