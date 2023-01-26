package com.example.magnificentchef.view.base.presenter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.view.base.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasePresenter {

    private FirebaseUser firebaseUser;
    private BaseInterfce baseInterfce;

    public BasePresenter(FirebaseUser firebaseUser,BaseInterfce baseInterfce) {
        this.firebaseUser = firebaseUser;
        this.baseInterfce=baseInterfce;
    }

    public void check(){
        if(firebaseUser!=null){
            baseInterfce.onLoggedUser();
        }
        else{
            baseInterfce.onGuestUser();
        }

    }

}
