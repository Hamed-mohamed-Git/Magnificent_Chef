package com.example.magnificentchef.model.remote.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireStoreRepository {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    String area = "";
    private int favoriteMealsCount = 0;

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

    public List<FavouriteMeal> getSavedMeals(){
        List<FavouriteMeal> favouriteMealList = new ArrayList<>();
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Favourite Meals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FavouriteMeal favouriteMeal = new FavouriteMeal();
                                favouriteMeal.setArea((String)document.getData().get("area"));
                                favouriteMeal.setCategory((String)document.getData().get("category"));
                                favouriteMeal.setMeal_id((String)document.getData().get("meal_id"));
                                favouriteMeal.setMeasure((String)document.getData().get("measure"));
                                favouriteMeal.setDirections((String)document.getData().get("directions"));
                                favouriteMeal.setImage((String)document.getData().get("image"));
                                favouriteMeal.setName((String)document.getData().get("name"));
                                favouriteMeal.setIngredients((String)document.getData().get("ingredients"));
                                favouriteMeal.setVideoUrl((String)document.getData().get("videoUrl"));
                                favouriteMealList.add(favouriteMeal);
                            }
                        }
                    }
                });

        return favouriteMealList;
    }

    public List<PlanMeal> getPlannedMeals(){
        List<PlanMeal> planMealList = new ArrayList<>();
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Planned Meals")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PlanMeal planMeal = new PlanMeal();
                                planMeal.setArea((String)document.getData().get("area"));
                                planMeal.setCategory((String)document.getData().get("category"));
                                planMeal.setMeal_id((String)document.getData().get("meal_id"));
                                planMeal.setMeasure((String)document.getData().get("measure"));
                                planMeal.setDirections((String)document.getData().get("directions"));
                                planMeal.setImage((String)document.getData().get("image"));
                                planMeal.setName((String)document.getData().get("name"));
                                planMeal.setRecipe((String)document.getData().get("recipe"));
                                planMeal.setVideoUrl((String)document.getData().get("videoUrl"));
                                planMeal.setDate((String)document.getData().get("date"));
                                planMealList.add(planMeal);
                            }
                        }
                    }
                });

        return planMealList;
    }

    public int checkFavouriteMeals(){
        firebaseFirestore.collection("Users")
                .document(Objects.requireNonNull(firebaseAuth.getUid()))
                .collection("Favourite Meals")
                .count().get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                        favoriteMealsCount = (int)task.getResult().getCount();
                    }
                });
        return favoriteMealsCount;
    }

}

