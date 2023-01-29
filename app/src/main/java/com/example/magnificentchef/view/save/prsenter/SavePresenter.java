package com.example.magnificentchef.view.save.prsenter;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
