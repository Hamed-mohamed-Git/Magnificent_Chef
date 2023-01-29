package com.example.magnificentchef.model.remote.firebase;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;

import java.util.List;

public interface FireStoreDelegate {
    void onFavouriteMealList(List<FavouriteMeal> favouriteMeals);

}
