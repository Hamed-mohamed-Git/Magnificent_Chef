package com.example.magnificentchef.view.save_meal_recipe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.firebase.FireStoreDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.SaveFiles;
import com.example.magnificentchef.view.common.ConnectionState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class SaveMealRecipePresenter{
    private final Repository repository;
    private final PlanSaveRepository planSaveRepository;
    private String ingredientList = "";
    private String measures = "";
    private final Context context;
    private final ConnectionState connectionState;

    public SaveMealRecipePresenter(Repository repository, PlanSaveRepository planSaveRepository, ConnectionState connectionState, Context context) {
        this.repository = repository;
        this.planSaveRepository = planSaveRepository;
        this.context = context;
        this.connectionState = connectionState;
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
    public void checkConnectionChange(){
        context.getSystemService(ConnectivityManager.class).registerDefaultNetworkCallback(new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                new Handler(Looper.getMainLooper()).post(connectionState::onInternetAvailable);
                super.onAvailable(network);

            }

            @Override
            public void onLost(@NonNull Network network) {
                new Handler(Looper.getMainLooper()).post(connectionState::onInternetLost);
                super.onLost(network);

            }
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
            }
        });
        if (context.getSystemService(ConnectivityManager.class).getActiveNetworkInfo()!=null)
            connectionState.onInternetAvailable();
        else {
            connectionState.onInternetLost();
        }
    }

}
