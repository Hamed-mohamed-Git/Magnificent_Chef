package com.example.magnificentchef.network;


import com.example.magnificentchef.network.model.RandomMealResponse;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {
    @GET("random.php")
    Single<RandomMealResponse> getMeal();


}
