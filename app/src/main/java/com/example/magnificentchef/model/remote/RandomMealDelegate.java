package com.example.magnificentchef.model.remote;

import com.example.magnificentchef.model.remote.model.MealsItem;

import java.util.List;

public interface RandomMealDelegate{
    void onSuccessResult(MealsItem mealsItem);
    void onFailureResult(String message);
}
