package com.example.magnificentchef.model.remote.model.ingredient_model;

import com.google.gson.annotations.SerializedName;

public class MealsItem{
	public final static String urlBase="https://www.themealdb.com/images/ingredients/";
	public final static String urlTail="-Small.png";

	@SerializedName("strDescription")
	private String strDescription;

	@SerializedName("strIngredient")
	private String strIngredient;

	@SerializedName("strType")
	private Object strType;

	@SerializedName("idIngredient")
	private String idIngredient;

	public String getStrDescription(){
		return strDescription;
	}

	public String getStrIngredient(){
		return strIngredient;
	}

	public Object getStrType(){
		return strType;
	}

	public String getIdIngredient(){
		return idIngredient;
	}

	public String getIngredientImage(){
		return urlBase+strIngredient+urlTail;
	}

}