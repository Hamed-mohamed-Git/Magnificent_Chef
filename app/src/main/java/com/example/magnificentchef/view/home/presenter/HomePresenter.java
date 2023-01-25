package com.example.magnificentchef.view.home.presenter;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.common.Ingredient;

import java.lang.reflect.Field;

public class HomePresenter {
    private Repository repository;
    private String ingredients;
    private String measure;
    private FavouriteRepository favouriteRepository;

    public HomePresenter(Repository repository,FavouriteRepository favouriteRepository) {
        this.repository = repository;
        this.favouriteRepository=favouriteRepository;
    }
    public void getRandomMeal(int mealCount){
        repository.getRandomMeal(mealCount);
    }

    public void favouriteMeal(MealsItem favouriteMeal) throws NoSuchFieldException, IllegalAccessException {
        getFields(favouriteMeal);
       favouriteRepository.insertFavouriteMeal(convertToFavouriteMeal(favouriteMeal));
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
    private FavouriteMeal convertToFavouriteMeal(MealsItem mealsItem){
        FavouriteMeal favouriteMeal=new FavouriteMeal();
        favouriteMeal.setMeal_id(mealsItem.getIdMeal());
        favouriteMeal.setName(mealsItem.getStrMeal());
        favouriteMeal.setArea(mealsItem.getStrArea());
        favouriteMeal.setImage(mealsItem.getStrMealThumb());
        favouriteMeal.setDirections(mealsItem.getStrInstructions());
        favouriteMeal.setVideoUrl(mealsItem.getStrYoutube());
        favouriteMeal.setIngredients(ingredients);
        favouriteMeal.setMeasure(measure);
        favouriteMeal.setCategory(mealsItem.getStrCategory());
        return favouriteMeal;
    }
    }
