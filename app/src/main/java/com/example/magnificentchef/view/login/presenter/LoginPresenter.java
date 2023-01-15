package com.example.magnificentchef.view.login.presenter;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.auth.FirebaseAuth;

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
        auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()){
                Log.d(TAG, "signInWithEmail:success");
                loginPresenterInterface.onLoginSuccess();
            }else {
                Log.d(TAG, "signInWithEmail:failure");
                loginPresenterInterface.onLoginFailure();
            }
        });
    }

}
