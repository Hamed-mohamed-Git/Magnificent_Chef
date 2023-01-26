package com.example.magnificentchef.model.remote;


import com.example.magnificentchef.model.remote.model.RandomMealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("random.php")
    Single<RandomMealResponse> getMeal();

    @GET("search.php")
    Single<RandomMealResponse> getSearchLetter(@Query("f") String letter);

    @GET("filter.php")
    Single<RandomMealResponse> getSearchIngredients(@Query("i") String ingredient);

    @GET("filter.php")
    Single<RandomMealResponse> getSearchArea(@Query("a") String area);

    @GET("lookup.php")
    Single<RandomMealResponse> getMealById(@Query("i") String id);


}
