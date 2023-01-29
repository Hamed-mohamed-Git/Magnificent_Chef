package com.example.magnificentchef.view.plan.presenter;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.remote.firebase.FireStoreDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class PlanPresenter {
    private ClickAddPlanListener clickAddPlanListener;
    private PlanSaveRepository planSaveRepository;
    public PlanPresenter(ClickAddPlanListener clickAddPlanListener, PlanSaveRepository planSaveRepository) {
        this.clickAddPlanListener = clickAddPlanListener;
        this.planSaveRepository = planSaveRepository;
    }

    public void addSearchedRecipe(String day){
        clickAddPlanListener.onClickAddPlanListener(day);
    }


    public void getPlannedMeal(){
        planSaveRepository.plannedMeals();
    }

    public void deletePlannedMeal(PlanMeal planMeal){
        planSaveRepository.deletePlanMeal(planMeal);
        new FireStoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
                .deletePlannedMeal(planMeal.getMeal_id());
    }
}
