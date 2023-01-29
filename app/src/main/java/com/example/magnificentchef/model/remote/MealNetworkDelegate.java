package com.example.magnificentchef.model.remote;

import com.example.magnificentchef.model.remote.model.MealsItem;

import java.util.List;

public interface MealNetworkDelegate extends NetworkDelegate<MealsItem>{
    void onSuccessMealResult(List<MealsItem> mealResponseList,String key);
}
