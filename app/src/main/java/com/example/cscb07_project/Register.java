package com.example.cscb07_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.view.View.OnClickListener;


import com.example.cscb07_project.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextName;
    FirebaseAuth mAuth;
    Button buttonReg;
    ProgressBar progressBar;
    TextView textView;
    FirebaseDatabase db;
    DatabaseReference reference;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            navigateToPage(MainActivity.class);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        editTextName = findViewById(R.id.name);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToPage(Login.class);
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               progressBar.setVisibility(View.VISIBLE);
               String email, password, confirmPassword, name;
               email = String.valueOf(editTextEmail.getText());
               password = String.valueOf(editTextPassword.getText());
               confirmPassword = String.valueOf(editTextConfirmPassword.getText());
               name = String.valueOf(editTextName.getText());

               if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)) {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(Register.this, "Missing Field", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (!isValidEmail(email)) {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(Register.this, "Invalid Email Format", Toast.LENGTH_SHORT).show();
                   return;
               }

               if (!isValidPassword(password)) {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(Register.this, "Password should be at least 6 characters long and contain a digit", Toast.LENGTH_SHORT).show();
                   return;
               }

               if(!password.equals(confirmPassword)){
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(Register.this, "Passwords Do Not Match", Toast.LENGTH_SHORT).show();
                   return;
               }

               //db = FirebaseDatabase.getInstance();
               //reference = db.getReference("Users");
               //reference.child(email).setValue(name, email);

               mAuth.createUserWithEmailAndPassword(email, password)
                       .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               progressBar.setVisibility(View.GONE);
                               if (task.isSuccessful()) {
                                   String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                   // Store user data under the UID
                                   DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                                   userRef.child("name").setValue(name);
                                   userRef.child("email").setValue(email);
                                   userRef.child("adminAccess").setValue(false);

                                   Toast.makeText(Register.this, "Account Created",
                                           Toast.LENGTH_SHORT).show();
                                   navigateToPage(Login.class);

                               } else {
                                   // If sign in fails, display a message to the user.
                                   Toast.makeText(Register.this, "Authentication failed.",
                                           Toast.LENGTH_SHORT).show();
                               }
                           }
                       });
           }
        });
    }

    private boolean isValidEmail(String email) {
        // Use a simple regex for email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private boolean isValidPassword(String password) {
        // Check if the password is at least 6 characters long and contains at least one digit
        return password.length() >= 6 && password.matches(".*\\d.*");
    }

    void navigateToPage(Class<?> destinationClass) {
        finish();
        Intent intent = new Intent(getApplicationContext(), destinationClass);
        startActivity(intent);
    }
}