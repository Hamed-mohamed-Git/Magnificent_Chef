package com.example.magnificentchef.view.plan.presenter;

import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;

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
    }


}
