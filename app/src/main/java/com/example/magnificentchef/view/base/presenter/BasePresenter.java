package com.example.magnificentchef.view.base.presenter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.view.base.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BasePresenter {

    private FirebaseUser firebaseUser;
    private BaseInterfce baseInterfce;
    private FavouriteRepository favouriteRepository;
    private PlanSaveRepository planSaveRepository;

    public BasePresenter(FirebaseUser firebaseUser, BaseInterfce baseInterfce,
                         FavouriteRepository favouriteRepository, PlanSaveRepository planSaveRepository) {
        this.firebaseUser = firebaseUser;
        this.baseInterfce=baseInterfce;
        this.favouriteRepository = favouriteRepository;
        this.planSaveRepository = planSaveRepository;
    }

    public void check(){
        if(firebaseUser!=null){
            baseInterfce.onLoggedUser();
        }
        else{
            baseInterfce.onGuestUser();
        }
    }

    public void clearDatabaseTables(){
        favouriteRepository.clearFavouriteMealsTableData();
        planSaveRepository.clearPlannedMealsTableData();
    }
}
