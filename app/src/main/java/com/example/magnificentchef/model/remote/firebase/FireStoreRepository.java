package com.example.magnificentchef.model.remote.firebase;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FireStoreRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public FireStoreRepository(FirebaseFirestore db,FirebaseAuth firebaseAuth) {
        this.firebaseFirestore = db;
        this.firebaseAuth = firebaseAuth;
    }

    public void CreateUser(String firstName,String lastName, String userName,String email){
        Map<String,String>user=new HashMap<>();
        user.put("firstName",firstName);
        user.put("lastName",lastName);
        user.put("userName",userName);
        user.put("email",email);

        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .set(user)
                .addOnSuccessListener(unused -> {

                })
                .addOnFailureListener(e -> {

                });
    }

    public void deletePlannedMeal(String mealID){
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Planned Meals")
                .document(mealID)
                .delete();
    }

    public void createPlannedMeals(PlanMeal planMeal) {
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Planned Meals")
                .document(planMeal.getMeal_id())
                .set(planMeal)
                .addOnCompleteListener(task -> {

                })
                .addOnFailureListener(e -> {
                });
    }

    public void deleteSavedMeal(String mealID){
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Favourite Meals")
                .document(mealID)
                .delete();
    }

    public void createSavedMeals(FavouriteMeal favouriteMeal) {
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Favourite Meals")
                .document(favouriteMeal.getMeal_id())
                .set(favouriteMeal)
                .addOnCompleteListener(task -> {

                })
                .addOnFailureListener(e -> {
                });
    }
}

