package com.example.magnificentchef.view.base.presenter;

import com.example.magnificentchef.model.local.common.MealsDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.remote.firebase.FireStoreDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class BasePresenter {

    private FirebaseUser firebaseUser;
    private BaseInterfce baseInterfce;
    private FavouriteRepository favouriteRepository;
    private PlanSaveRepository planSaveRepository;
    private FireStoreRepository fireStoreRepository;
    private MealsDelegate mealsDelegate;
    private FireStoreDelegate fireStoreDelegate;

    public BasePresenter(FirebaseUser firebaseUser, BaseInterfce baseInterfce,
                         FavouriteRepository favouriteRepository,
                         PlanSaveRepository planSaveRepository,
                         FireStoreRepository fireStoreRepository,
                         MealsDelegate mealsDelegate,
                         FireStoreDelegate fireStoreDelegate
    ) {
        this.firebaseUser = firebaseUser;
        this.baseInterfce=baseInterfce;
        this.favouriteRepository = favouriteRepository;
        this.planSaveRepository = planSaveRepository;
        this.fireStoreRepository=fireStoreRepository;
        this.mealsDelegate = mealsDelegate;
        this.fireStoreDelegate = fireStoreDelegate;
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
    public  void checkSavePlanMeal(){
        fireStoreRepository.checkPlanMealCount(mealsDelegate);

    }

    public  void checkSaveFavouriteMeal(){
        fireStoreRepository.checkFavouriteMealsCount(mealsDelegate);
    }

    public void getMealsPlanMeals(int count){
        if (count > 0)
            fireStoreRepository.getPlannedMeals(fireStoreDelegate);
    }

    public void getMealsFavouriteMeals(int count){
        if (count > 0)
            fireStoreRepository.getSavedMeals(fireStoreDelegate);
    }

    public void setMealsPlanMeals(List<PlanMeal> planMealList){
        planSaveRepository.addPlannedMealList(planMealList);
    }

    public void setMealsFavouriteMeals(List<FavouriteMeal> favouriteMealList){
       favouriteRepository.addFavouriteMealList(favouriteMealList);
    }

}
