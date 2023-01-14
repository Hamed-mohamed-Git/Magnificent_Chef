package com.example.magnificentchef.presenter.login;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private static final String TAG = "login Screen";
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