package com.example.magnificentchef.presenter.registration;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class RegisterPresenter {
    private RegisterInterface registerInterface;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount googleSignInAccount;
    private FragmentActivity activity;

    public RegisterPresenter(Context context,FragmentActivity activity){
    }

    public void  Join(){
        if(googleSignInAccount!=null){
            registerInterface.onClick();
        }

        ActivityResultLauncher<Intent> activityResultLauncher = activity.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInTask(task);
            }
        });
    }

    private void handleSignInTask(Task<GoogleSignInAccount>task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            final String getFullname=account.getDisplayName();
            final String getEmail=account.getEmail();
            registerInterface.onClick();



        } catch (ApiException e) {
            e.printStackTrace();
        }
    }


}
