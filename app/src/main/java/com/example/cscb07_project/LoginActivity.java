package com.example.cscb07_project;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity implements LoginModel.LoginListener {
    private static final int MIN_PASSWORD_LENGTH = 6;
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
        textView = findViewById(R.id.registerNow);


        // Initialize UI components


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.navigateToPage(LoginActivity.this, Register.class);
            }
        });


        googleBtn.setOnClickListener(v -> presenter.signInWithGoogle(gsc, RC_SIGN_IN, this));


        buttonLogin.setOnClickListener(v -> {
            String email = String.valueOf(editTextEmail.getText());
            String password = String.valueOf(editTextPassword.getText());
            if (isValid(email, password)) {
                progressBar.setVisibility(View.VISIBLE);
                presenter.loginUser(email, password, mAuth, this);
            } else {
                // Handle invalid input (show an error message, etc.)
                Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already logged in, navigate to the appropriate page
            navigateToPage(currentUser);
            return;  // Exit the method to prevent further execution
        }


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


    private boolean isValid(String email, String password) {
        // Add your validation logic here
        // For example, you can use TextUtils or Patterns class for email validation
        // Check if the email and password meet your requirements


        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false; // Invalid email
        }


        if (TextUtils.isEmpty(password) || password.length() < MIN_PASSWORD_LENGTH) {
            return false; // Invalid password
        }


        return true; // Both email and password are valid
    }


    private void navigateToPage(FirebaseUser user) {
        String userId = user.getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);


        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean isAdmin = dataSnapshot.child("adminAccess").getValue(Boolean.class);
                Class<?> destinationClass = isAdmin != null && isAdmin ? Admin.class : MainActivity.class;
                presenter.navigateToPage(LoginActivity.this, destinationClass);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Database read failed: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
