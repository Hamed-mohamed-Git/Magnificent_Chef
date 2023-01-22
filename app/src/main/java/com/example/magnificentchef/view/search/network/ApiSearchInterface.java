package com.example.magnificentchef.view.search.network;

import com.example.magnificentchef.network.model.RandomMealResponse;
import com.example.magnificentchef.view.search.model.RootMeal;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiSearchInterface {

    @GET("list.php?i=list")
    Single<RootMeal> getProducts();


}
