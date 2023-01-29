package com.example.magnificentchef.model.remote.firebase;

import com.example.magnificentchef.model.local.plan_meal.PlanMeal;

import java.util.List;

public interface FireStoreGetPlane {
    void onPlannedMealList(List<PlanMeal> planMeals);
}
