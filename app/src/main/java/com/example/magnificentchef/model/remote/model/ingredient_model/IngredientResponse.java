package com.example.magnificentchef.model.remote.model.ingredient_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class IngredientResponse{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}
}