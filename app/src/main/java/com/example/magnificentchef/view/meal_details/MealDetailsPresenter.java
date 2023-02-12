package com.example.magnificentchef.view.meal_details;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.local.plan_meal.PlannedDelegate;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.common.Ingredient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MealDetailsPresenter {

    private final Repository repository;
    private final PlanSaveRepository planSaveRepository;
    private final MealDetailsPresenterInterface mealDetailsPresenterInterface;
    private final FavouriteRepository favouriteRepository;

    public MealDetailsPresenter(Repository repository,
                                MealDetailsPresenterInterface mealDetailsPresenterInterface,
                                PlanSaveRepository planSaveRepository,
                                FavouriteRepository favouriteRepository) {
        this.repository = repository;
        this.mealDetailsPresenterInterface = mealDetailsPresenterInterface;
        this.planSaveRepository = planSaveRepository;
        this.favouriteRepository = favouriteRepository;
    }

    public void checkMealResourceType(String type){
        switch (type){
            case "retrofit":
                mealDetailsPresenterInterface.onGetMealFromRetrofit();
            case "localPlanMeal":
                mealDetailsPresenterInterface.onGetPlannedMealFromDatabase();
            case "localFavouriteMeal":
                mealDetailsPresenterInterface.onGetFavouriteMealFromDatabase();
        }
    }

    public void getMealByLetter(String id){
        repository.getMealById(id);
    }

    public void getPlannedMealByID(String id, PlannedDelegate plannedDelegate){
        planSaveRepository.getPlannedMealByID(id, plannedDelegate);
    }

    public void getFavouriteMealByID(String id, FavouriteDelegate favouriteDelegate){
        favouriteRepository.getFavouriteMealByID(id, favouriteDelegate);
    }

    public String getYoutubeCode(String url){
        char[] chars = url.toCharArray();
        int code = 0;
        for (int loop = 0; loop < chars.length; loop++){
            if (chars[loop] == '='){
                code = loop + 1;
            }
        }
        return url.substring(code);
    }

    public List<Ingredient> getIngredient(MealsItem mealsItem) throws IllegalAccessException, NoSuchFieldException {
        List<Ingredient> ingredientsList = new ArrayList<>();
        Field[] fieldList =  mealsItem.getClass().getDeclaredFields();
        for (Field field : fieldList) {
            if (field.getName().startsWith("strIngredient")) {
                field.setAccessible(true);
                if (field.get(mealsItem) != null && !field.get(mealsItem).toString().isEmpty()) {
                    Field measureField = mealsItem.getClass().getDeclaredField("strMeasure" + field.getName().substring(13, field.getName().length()));
                    measureField.setAccessible(true);
                    ingredientsList.add(new Ingredient((String) field.get(mealsItem),
                            (String) measureField.get(mealsItem)));
                }
            }
        }
        return ingredientsList;
    }
}
