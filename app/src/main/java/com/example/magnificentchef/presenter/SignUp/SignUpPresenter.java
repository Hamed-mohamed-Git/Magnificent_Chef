package com.example.magnificentchef.presenter.SignUp;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter {
    private static final String TAG = "SignUp Screen";
    private FirebaseAuth auth;
    private Activity activity;
    private SignUpInterface signUpInterface;

    public SignUpPresenter (FragmentActivity context, SignUpInterface signUpInterface, FirebaseAuth auth) {
        this.activity = context;
        this.signUpInterface = signUpInterface;
        this.auth = auth;
    }

    public void signUp(String userName, String password){
        auth.signInWithEmailAndPassword(userName, password).addOnCompleteListener(activity, task -> {
            if (task.isSuccessful()){
                signUpInterface.onSignSuccess();
            }else {
                signUpInterface.onSignFailure();
            }
        });
    }
}
