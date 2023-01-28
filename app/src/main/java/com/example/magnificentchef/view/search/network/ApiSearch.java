package com.example.magnificentchef.view.search.network;

import com.example.magnificentchef.view.search.model.RootMeal;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiSearch {
    public static String BASE_URL ="https://www.themealdb.com/api/json/v1/1/";
    public static ApiSearchInterface apiSearchInterface;

    private ApiSearch() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiSearchInterface=retrofit.create(ApiSearchInterface.class);

    }

    private static Retrofit retrofit;
    private static ApiSearch apiSearch;
    public static ApiSearch getClient(){
        if(apiSearch == null){
            apiSearch=new ApiSearch();
        }
        return apiSearch;
    }
    public Single<RootMeal> rootMealSingle(){
        return apiSearchInterface.getProducts();

    }

}
