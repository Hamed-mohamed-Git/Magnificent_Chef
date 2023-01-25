package com.example.magnificentchef.view.common;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.remote.model.MealsItem;

public interface OnMealClickListener {
    void onMealClickListener(MealsItem meal);

    void onMealFavouriteClickListener(MealsItem favouriteMeal);
}
