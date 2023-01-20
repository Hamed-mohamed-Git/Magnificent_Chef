package com.example.magnificentchef.network;


import com.example.magnificentchef.network.model.MealsItem;
import com.example.magnificentchef.network.model.RandomMealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("random.php")
    Single<RandomMealResponse> getMeal();

    @GET("search.php")
    Single<RandomMealResponse> getSearchLetter(@Query("f") String letter);


}
