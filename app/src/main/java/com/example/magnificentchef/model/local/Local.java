package com.example.magnificentchef.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteDAO;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.MealPlanDAO;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;


@Database(entities = {PlanMeal.class, FavouriteMeal.class},version = 1)
public abstract class Local extends RoomDatabase {
    public abstract MealPlanDAO mealPlanDAO();
    public abstract FavouriteDAO favouriteDAO();
    private static Local local = null;
    public static synchronized Local getLocal(Context context){
        if (local == null){
            local = Room.databaseBuilder(context.getApplicationContext(), Local.class, "chefSide")
                    .build();
        }
        return local;
    }
}
