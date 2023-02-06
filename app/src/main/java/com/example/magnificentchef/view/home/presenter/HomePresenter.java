package com.example.magnificentchef.view.home.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.remote.RandomMealDelegate;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.firebase.FireStoreDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.SaveFiles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class HomePresenter{
    private Repository repository;
    private String ingredients="";
    private String measure="";
    private FavouriteRepository favouriteRepository;
    private Context context;

    public HomePresenter(Repository repository,FavouriteRepository favouriteRepository,Context context) {
        this.repository = repository;
        this.favouriteRepository = favouriteRepository;
        this.context=context;
    }
    public void getRandomMeal(int mealCount,RandomMealDelegate randomMealDelegate){
        repository.getRandomMeal(mealCount,randomMealDelegate);
    }

    public void favouriteMeal(MealsItem favouriteMeal) throws NoSuchFieldException, IllegalAccessException {
        getFields(favouriteMeal);

        Glide.with(context).asBitmap().load(favouriteMeal.getStrMealThumb()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                try {
                    FavouriteMeal favouriteMealConverted = convertToFavouriteMeal(favouriteMeal,
                            SaveFiles.saveImage(context,resource,favouriteMeal.getStrMeal()));
                    favouriteRepository.insertFavouriteMeal(favouriteMealConverted);
                    new FireStoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
                            .createSavedMeals(favouriteMealConverted);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
                    Field measureField = mealsItem.getClass().getDeclaredField("strMeasure" + field.getName().substring(13, field.getName().length()));
                    measureField.setAccessible(true);
                    ingredients+=(String) field.get(mealsItem)+"-";
                    measure+=(String) measureField.get(mealsItem)+"-";
                }
            }

        }

    }
    private FavouriteMeal convertToFavouriteMeal(MealsItem mealsItem,String imagePath){
        FavouriteMeal favouriteMeal=new FavouriteMeal();
        favouriteMeal.setMeal_id(mealsItem.getIdMeal());
        favouriteMeal.setName(mealsItem.getStrMeal());
        favouriteMeal.setArea(mealsItem.getStrArea());
        favouriteMeal.setImage(imagePath);
        favouriteMeal.setDirections(mealsItem.getStrInstructions());
        favouriteMeal.setVideoUrl(mealsItem.getStrYoutube());
        favouriteMeal.setIngredients(ingredients);
        favouriteMeal.setMeasure(measure);
        favouriteMeal.setCategory(mealsItem.getStrCategory());
        return favouriteMeal;
    }
}
