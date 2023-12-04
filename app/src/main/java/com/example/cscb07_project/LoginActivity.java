package com.example.cscb07_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements LoginModel.LoginListener {
    private TextInputEditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private LoginPresenter presenter;
    private static final int RC_SIGN_IN = 1000;
    private Button buttonLogin;  // Add this line
    private TextView textView;
    private SignInButton googleBtn;  // Add this line
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        presenter = new LoginPresenter(new LoginModel());

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.btn_login);  // Add this line
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.registerNow);
        googleBtn = findViewById(R.id.google_btn);

        // Initialize UI components

        googleBtn.setOnClickListener(v -> presenter.signInWithGoogle(gsc, RC_SIGN_IN, this));

        buttonLogin.setOnClickListener(v -> {
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());
            progressBar.setVisibility(View.VISIBLE);
            presenter.loginUser(email, password, mAuth, this);
        });

        // Other UI-related initialization
    }

    // Implement LoginModel.LoginListener methods

    @Override
    public void onLoginSuccess(boolean isAdmin) {
        progressBar.setVisibility(View.GONE);
        if (isAdmin) {
            presenter.navigateToPage(this, Admin.class);
        } else {
            Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
            presenter.navigateToPage(this, MainActivity.class);
        }
    }

    @Override
    public void onLoginFailure(String errorMessage) {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            presenter.handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data), this, MainActivity.class);
        }
    }


}

