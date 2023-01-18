package com.example.magnificentchef.network;


import com.example.magnificentchef.network.model.RandomMealResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface MealApiService {
    @GET("random.php")
    Single<RandomMealResponse> getMeal();

}
