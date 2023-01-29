package com.example.magnificentchef.view.save_meal_recipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.SaveFiles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.lang.reflect.Field;

public class SaveMealRecipePresenter {
    private Repository repository;
    private PlanSaveRepository planSaveRepository;
    private String ingredientList = "";
    private String measures = "";
    private Context context;
    private String imageURI;

    public SaveMealRecipePresenter(Repository repository, PlanSaveRepository planSaveRepository, Context context) {
        this.repository = repository;
        this.planSaveRepository = planSaveRepository;
        this.context = context;
        imageURI = "";
    }
    public void getMealsByKey(String keyLetter){
        repository.getMealsByKey(keyLetter);
    }

    public void savePlaneMeal(MealsItem mealsItem,String day){


        Glide.with(context).asBitmap().load(mealsItem.getStrMealThumb()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                PlanMeal planMeal = new PlanMeal();
                try {
                    getFields(mealsItem);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                try {
                    planMeal.setImage(SaveFiles.saveImage(context,resource,mealsItem.getStrMeal()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                planMeal.setMeal_id(mealsItem.getIdMeal() + day);
                planMeal.setName(mealsItem.getStrMeal());
                planMeal.setArea(mealsItem.getStrArea());
                planMeal.setVideoUrl(mealsItem.getStrYoutube());
                planMeal.setCategory(mealsItem.getStrCategory());
                planMeal.setDate(day);
                planMeal.setRecipe(ingredientList);
                planMeal.setMeasure(measures);
                planMeal.setDirections(mealsItem.getStrInstructions());
                planSaveRepository.insertPlanMeal(planMeal);
                planMeal.setImage(mealsItem.getStrMealThumb());
                new FireStoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
                        .createPlannedMeals(planMeal);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });


    }

    private void getFields(MealsItem mealsItem) throws IllegalAccessException, NoSuchFieldException {
        Field[] fieldList =  mealsItem.getClass().getDeclaredFields();
        for (Field field : fieldList) {
            if (field.getName().startsWith("strIngredient")) {
                field.setAccessible(true);
                if (field.get(mealsItem) != null && !field.get(mealsItem).toString().isEmpty()) {
                    Field measureField = mealsItem.getClass().getDeclaredField("strMeasure" + field.getName().substring(13,field.getName().length()));
                    measureField.setAccessible(true);
                    ingredientList += (field.get(mealsItem) + "-");
                    measures += (measureField.get(mealsItem) + "-");
                }
            }

        }

    }

}
