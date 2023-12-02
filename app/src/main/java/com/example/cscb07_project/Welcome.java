package com.example.cscb07_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    Button register;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.loginNow);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(Login.class);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(Register.class);
            }
        });
    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }
}