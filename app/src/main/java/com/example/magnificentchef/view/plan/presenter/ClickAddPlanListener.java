package com.example.magnificentchef.view.plan.presenter;

public interface ClickAddPlanListener<T> {
    void onClickAddPlanListener(String day);
    void onPlannedMealClick(T t);
    void onDeleteMealClick(T t);
}
