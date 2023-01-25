package com.example.magnificentchef.utils;

import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.remote.model.MealsItem;

import java.lang.reflect.Field;

public class Mapper {
    private static MealsItem mealsItem;

    public static MealsItem convert(PlanMeal planMeal){
        mealsItem = new MealsItem();
        mealsItem.setIdMeal(planMeal.getMeal_id());
        mealsItem.setStrMeal(planMeal.getName());
        mealsItem.setStrArea(planMeal.getArea());
        mealsItem.setStrCategory(planMeal.getCategory());
        mealsItem.setStrMealThumb(planMeal.getImage());
        mealsItem.setStrYoutube(planMeal.getVideoUrl());
        mealsItem.setStrInstructions(planMeal.getDirections());
        try {
            getFields(planMeal.getRecipe().split("-"),planMeal.getMeasure().split("-"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return mealsItem;
    }


    private static void getFields(String[] ingredients,String[] measure) throws IllegalAccessException, NoSuchFieldException {
        Field[] fieldList =  mealsItem.getClass().getDeclaredFields();
        int count = 0;
        for (Field field : fieldList) {
            if (field.getName().startsWith("strIngredient")) {
                if (count < ingredients.length - 1) {
                    field.setAccessible(true);
                    Field measureField = mealsItem.getClass().getDeclaredField("strMeasure" + field.getName().substring(13, field.getName().length()));
                    measureField.setAccessible(true);
                    field.set(mealsItem,ingredients[count]);
                    measureField.set(mealsItem,measure[count]);
                }
                count++;
            }
        }

    }
}
