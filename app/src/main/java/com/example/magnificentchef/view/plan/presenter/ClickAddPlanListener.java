package com.example.magnificentchef.view.plan.presenter;

import com.example.magnificentchef.model.local.plan_meal.PlanMeal;

public interface ClickAddPlanListener<T> {
    void onClickAddPlanListener(String day);
    void onPlannedMealClick(T t);
    void onDeleteMealClick(T t);
}
