package com.example.magnificentchef.model.remote;


import com.example.magnificentchef.model.remote.model.RandomMealResponse;
import com.example.magnificentchef.model.remote.model.ingredient_model.IngredientResponse;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("random.php")
    Flowable<RandomMealResponse> getMeal();

    @GET("search.php")
    Single<RandomMealResponse> getSearchLetter(@Query("f") String letter);

    @GET("filter.php")
    Single<RandomMealResponse> getSearchIngredients(@Query("i") String ingredient);

    @GET("filter.php")
    Single<RandomMealResponse> getSearchArea(@Query("a") String area);

    @GET("lookup.php")
    Single<RandomMealResponse> getMealById(@Query("i") String id);

    @GET("filter.php")
    Single<RandomMealResponse> getMealsByCategory(@Query("c") String id);

    @GET("list.php?i=list")
    Single<IngredientResponse> getIngredients();

}
