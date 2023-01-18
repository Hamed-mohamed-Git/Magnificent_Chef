package com.example.magnificentchef.view.search.network;

import com.example.magnificentchef.view.search.model.RootMeal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiSearchInterface {

    @GET("list.php?i=list")
    Call<RootMeal> getProducts();
}
