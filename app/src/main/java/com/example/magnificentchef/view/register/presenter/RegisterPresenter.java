package com.example.magnificentchef.view.register.presenter;

import androidx.annotation.NonNull;

import com.example.magnificentchef.view.login.presenter.OnAuthLoginComplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterPresenter {
    private final FirebaseAuth firebaseAuth;
    private final OnAuthLoginComplete onAuthLoginComplete;

    public RegisterPresenter(FirebaseAuth firebaseAuth, OnAuthLoginComplete onAuthLoginComplete) {
        this.firebaseAuth = firebaseAuth;
        this.onAuthLoginComplete = onAuthLoginComplete;
    }

    public void googleLogin(String idToken){
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(idToken,null)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    onAuthLoginComplete.onLoginSuccess();

                }else {
                    task.getException().printStackTrace();
                }
            }
        });

    }
}
