package com.example.magnificentchef.view.plan.presenter;

import com.example.magnificentchef.model.local.plan_meal.PlanMeal;

public interface ClickAddPlanListener {
    void onClickAddPlanListener(String day);
    void onPlannedMealClick(PlanMeal planMeal);
    void onDeleteMealClick(PlanMeal planMeal);
}
