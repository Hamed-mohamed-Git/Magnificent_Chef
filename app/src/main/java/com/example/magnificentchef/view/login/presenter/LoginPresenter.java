package com.example.magnificentchef.view.login.presenter;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.magnificentchef.view.common.RegistrationError;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginPresenter {
    private static final String TAG = "loginScreen";
    private FirebaseAuth auth;
    private Activity activity;
    private LoginPresenterInterface loginPresenterInterface;

    public LoginPresenter(FragmentActivity context, LoginPresenterInterface loginPresenterInterface, FirebaseAuth auth) {
        this.activity = context;
        this.loginPresenterInterface = loginPresenterInterface;
        this.auth = auth;
    }


    public void logIn(String userName, String password){
        if (!userName.isEmpty() && !password.isEmpty()) {
            auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    Log.d(TAG, "signInWithEmail:success");
                    loginPresenterInterface.onLoginSuccess();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException)
                        loginPresenterInterface.onLoginFailure(RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID);
                    else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                        loginPresenterInterface.onLoginFailure(RegistrationError.USER_EMAIL_PASSWORD_INVALID);
                }
            });
        }
        else if(userName.isEmpty() && password.isEmpty())
            loginPresenterInterface.onLoginFailure(RegistrationError.EMPTY_USER_PASSWORD_EMAIL);
        else if(userName.isEmpty())
            loginPresenterInterface.onLoginFailure(RegistrationError.EMPTY_USER_EMAIL);
        else if(password.isEmpty())
            loginPresenterInterface.onLoginFailure(RegistrationError.EMPTY_USER_PASSWORD);

    }

}
