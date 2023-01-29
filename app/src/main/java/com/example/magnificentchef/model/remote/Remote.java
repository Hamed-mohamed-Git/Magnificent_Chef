package com.example.magnificentchef.model.remote;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remote {
    private static Retrofit retrofit;
    private static Remote remote;
    private static final String MEALS_BASE_URL = "https://www.themealdb.com/api/json/v1/1/";

    private Remote(){
        retrofit = new Retrofit.Builder()
                .baseUrl(MEALS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public static Remote getRetrofitInstance(){
        if (remote == null){
            remote = new Remote();
        }
        return remote;
    }

    public MealApiService getMealsApiService(){
        return retrofit.create(MealApiService.class);
    }



}



