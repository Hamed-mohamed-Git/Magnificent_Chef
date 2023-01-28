package com.example.magnificentchef.model.local.plan_meal;

import com.example.magnificentchef.model.local.common.CompletableSaveMealPlanListener;
import com.example.magnificentchef.model.local.common.SingleSaveMealPlanListener;

import java.util.List;

public interface SavePlanMealDelegate extends CompletableSaveMealPlanListener{
    void onSubscribe();
    void onError(String errorMessage);
    void onSuccessSavePlannedMeal(List<PlanMeal> planMealList);
}
