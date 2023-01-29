package com.example.magnificentchef.view.register.presenter;

import androidx.annotation.NonNull;

import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.view.login.presenter.OnAuthLoginComplete;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class RegisterPresenter {
    private final FirebaseAuth firebaseAuth;
    private final OnAuthLoginComplete onAuthLoginComplete;
    private final FireStoreRepository fireStoreRepository;


    public RegisterPresenter(FirebaseAuth firebaseAuth, OnAuthLoginComplete onAuthLoginComplete, FireStoreRepository fireStoreRepository) {
        this.firebaseAuth = firebaseAuth;
        this.onAuthLoginComplete = onAuthLoginComplete;
        this.fireStoreRepository = fireStoreRepository;

    }

    public void googleLogin(String idToken){
        firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(idToken,null)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = task.getResult().getUser();
                    fireStoreRepository
                            .CreateUser(firebaseUser
                                    .getDisplayName(),"",
                                    firebaseUser.getDisplayName(),
                                    firebaseUser.getEmail());
                    onAuthLoginComplete.onLoginSuccess();
                }else {
                    task.getException().printStackTrace();
                }
            }
        });

    }
}
