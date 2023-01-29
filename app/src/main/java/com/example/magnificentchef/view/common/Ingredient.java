package com.example.magnificentchef.view.common;

public class Ingredient {
    private String ingredientName;
    private String measure;

    public Ingredient(String ingredientName, String measure) {
        this.ingredientName = ingredientName;
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getMeasure() {
        return measure;
    }
}
