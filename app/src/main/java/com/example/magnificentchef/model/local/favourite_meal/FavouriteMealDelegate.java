package com.example.magnificentchef.model.local.favourite_meal;

import com.example.magnificentchef.model.local.common.CompletableSaveMealPlanListener;
import com.example.magnificentchef.model.local.common.SingleSaveMealPlanListener;
import java.util.List;

public interface FavouriteMealDelegate extends CompletableSaveMealPlanListener, SingleSaveMealPlanListener<List<FavouriteMeal>> {

    void onSubscribe();
    void onError(String errorMessage);
}
