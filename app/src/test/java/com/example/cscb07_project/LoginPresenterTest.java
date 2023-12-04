package com.example.cscb07_project;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LoginPresenterTest {

    @Mock
    private LoginModel mockModel;

    @Mock
    private FirebaseAuth mockFirebaseAuth;

    @Mock
    private GoogleSignInClient mockGoogleSignInClient;

    @Mock
    private Task<GoogleSignInAccount> mockGoogleSignInTask;

    @Mock
    private AppCompatActivity mockActivity;

    private LoginPresenter loginPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        loginPresenter = new LoginPresenter(mockModel);
    }

    @Test
    public void testLoginUser() {
        String email = "test@example.com";
        String password = "password";

        loginPresenter.loginUser(email, password, mockFirebaseAuth, Mockito.mock(LoginModel.LoginListener.class));

        // Verify that the signInWithEmailAndPassword method is called on the mockModel
        Mockito.verify(mockModel).signInWithEmailAndPassword(
                ArgumentMatchers.eq(email),
                ArgumentMatchers.eq(password),
                ArgumentMatchers.eq(mockFirebaseAuth),
                ArgumentMatchers.any());
    }

    @Test
    public void testSignInWithGoogle() {
        int requestCode = 100;

        // Mock the GoogleSignInClient and configure it to return a non-null intent
        Intent mockSignInIntent = Mockito.mock(Intent.class);
        Mockito.when(mockGoogleSignInClient.getSignInIntent()).thenReturn(mockSignInIntent);

        loginPresenter.signInWithGoogle(mockGoogleSignInClient, requestCode, mockActivity);

        // Verify that the startActivity method is called on the mockActivity with the expected Intent
        Mockito.verify(mockActivity).startActivityForResult(ArgumentMatchers.any(Intent.class), ArgumentMatchers.eq(requestCode));
    }



    @Test
    public void testNavigateToPage() {
        Class<?> destinationClass = MainActivity.class;

        loginPresenter.navigateToPage(mockActivity, destinationClass);

        // Verify that finish and startActivity methods are called on the mockActivity
        Mockito.verify(mockActivity).finish();
        Mockito.verify(mockActivity).startActivity(ArgumentMatchers.any(Intent.class));
    }
}