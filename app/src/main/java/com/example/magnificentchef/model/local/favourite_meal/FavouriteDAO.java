package com.example.magnificentchef.model.local.favourite_meal;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
@Dao
public interface FavouriteDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(FavouriteMeal...FavouriteMeals);

    @Delete
    Completable delete(FavouriteMeal...FavouriteMeals);

   @Query("SELECT * FROM FavouriteMeal")
    Single<List<FavouriteMeal>> getFavouriteMeals();

    @Query("DELETE FROM FavouriteMeal")
    Completable delete();

}
