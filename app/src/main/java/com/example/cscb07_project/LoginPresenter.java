package com.example.cscb07_project;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private final LoginModel model;

    public LoginPresenter(LoginModel model) {
        this.model = model;
    }

    void loginUser(String email, String password, FirebaseAuth mAuth, LoginModel.LoginListener listener) {
        model.signInWithEmailAndPassword(email, password, mAuth, listener);
    }

    void signInWithGoogle(GoogleSignInClient gsc, int requestCode, AppCompatActivity activity) {
        Intent signInIntent = gsc.getSignInIntent();
        activity.startActivityForResult(signInIntent, requestCode);
    }

    void handleGoogleSignInResult(Task<GoogleSignInAccount> task, AppCompatActivity activity, Class<?> destinationClass) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            // You may want to get some information from the account if needed
            // e.g., String personName = account.getDisplayName();
            activity.startActivity(new Intent(activity.getApplicationContext(), destinationClass));
            activity.finish();
        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(activity.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    void navigateToPage(AppCompatActivity activity, Class<?> destinationClass) {
        activity.finish();
        Intent intent = new Intent(activity.getApplicationContext(), destinationClass);
        activity.startActivity(intent);
    }


}

