package com.example.magnificentchef.model.remote.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RandomMealResponse{

	@SerializedName("meals")
	private List<MealsItem> meals;

	public List<MealsItem> getMeals(){
		return meals;
	}


}