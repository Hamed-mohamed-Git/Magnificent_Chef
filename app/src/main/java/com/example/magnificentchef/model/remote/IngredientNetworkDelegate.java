package com.example.magnificentchef.model.remote;

import com.example.magnificentchef.model.remote.model.ingredient_model.MealsItem;

import java.util.List;

public interface IngredientNetworkDelegate {
    void onSuccessIngredientsResult(List<MealsItem> ingredientsListList);

    void onFailIngredientsResult(String error);
}
