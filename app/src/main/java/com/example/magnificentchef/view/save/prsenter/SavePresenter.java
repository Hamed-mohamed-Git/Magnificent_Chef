package com.example.magnificentchef.view.save.prsenter;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.SaveFiles;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.lang.reflect.Field;

public class SavePresenter {
    private FavouriteRepository favouriteRepository;

    public SavePresenter(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public void getFavouriteMeals(){
        favouriteRepository.FavouriteMeals();
    }

    public void deleteFavouriteMeals(FavouriteMeal favouriteMeal){
        favouriteRepository.deleteFavouriteMeal(favouriteMeal);
        new FireStoreRepository(FirebaseFirestore.getInstance(),
                FirebaseAuth.getInstance())
                .deleteSavedMeal(favouriteMeal.getMeal_id());
    }

}
