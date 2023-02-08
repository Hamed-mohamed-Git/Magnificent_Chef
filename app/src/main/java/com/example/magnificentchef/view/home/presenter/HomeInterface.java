package com.example.magnificentchef.view.home.presenter;

import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.common.ConnectionState;

public interface HomeInterface extends ConnectionState {
    void setDailyInspirationMeal(MealsItem mealsItem);
    void setMealData(MealsItem mealsItem);
    void setMoreYouLikeMeal(MealsItem mealsItem);
}
