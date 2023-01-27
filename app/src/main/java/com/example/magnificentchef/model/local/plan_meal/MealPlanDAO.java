package com.example.magnificentchef.model.local.plan_meal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface MealPlanDAO {
    @Insert
    Completable insert(PlanMeal...PlanMeals);

    @Delete
    Completable delete(PlanMeal...PlanMeals);

    @Query("SELECT * FROM PlanMeal")
    Single<List<PlanMeal>> getMeals();

    @Query("DELETE FROM PlanMeal")
    void delete();
}
