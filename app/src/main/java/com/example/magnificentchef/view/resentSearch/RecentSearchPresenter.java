package com.example.magnificentchef.view.resentSearch;

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
import com.example.magnificentchef.model.remote.MealNetworkDelegate;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.SaveFiles;

import java.io.IOException;
import java.lang.reflect.Field;

public class RecentSearchPresenter {
    private Repository repository;
    private FavouriteRepository favouriteRepository;
    private Context context;
    private String ingredients="";
    private String measure="";
    private OnSearchCheckListener onSearchCheckListener;

    public RecentSearchPresenter(Repository repository, FavouriteRepository favouriteRepository, Context context, OnSearchCheckListener onSearchCheckListener) {
        this.repository = repository;
        this.favouriteRepository = favouriteRepository;
        this.context = context;
        this.onSearchCheckListener = onSearchCheckListener;
    }

    public void getMealsByKey(String keyLetter){
        repository.getMealsByKey(keyLetter);
    }
    public void getMealsByIngredient(String ingredient){
        repository.getMealsByIngredient(ingredient);
    }
    public void getMealsByArea(String area){
        repository.getMealsByArea(area);
    }
    public void getMealsById(String id,String key,MealNetworkDelegate mealNetworkDelegate){
        repository.getMealById(id,key,mealNetworkDelegate);
    }

    public void getMealsByCategory(String category){
        repository.getMealByCategory(category);
    }
    public void checkSearchType(String key, String query){
        switch (key){
            case "s":
                onSearchCheckListener.onSearchListener(query);
                break;
            case "a":
                onSearchCheckListener.onSearchAreaListener(query);
                break;
            case "c":
                onSearchCheckListener.onSearchCategoryListener(query);
                break;
            case "i":
                onSearchCheckListener.onSearchIngredientListener(query);
                break;

        }
    }

    public void RecentSearchfavouriteMeal(MealsItem favouriteMeal) throws NoSuchFieldException, IllegalAccessException {
        getFields(favouriteMeal);

        Glide.with(context).asBitmap().load(favouriteMeal.getStrMealThumb()).into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                try {
                    favouriteRepository.insertFavouriteMeal(convertToFavouriteMeal(favouriteMeal,
                            SaveFiles.saveImage(context,resource,favouriteMeal.getStrMeal())));
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

    private FavouriteMeal convertToFavouriteMeal(MealsItem mealsItem, String imagePath){
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
