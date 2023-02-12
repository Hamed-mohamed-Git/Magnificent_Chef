package com.example.magnificentchef.view.meal_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.local.plan_meal.PlannedDelegate;
import com.example.magnificentchef.model.local.plan_meal.SavePlanMealDelegate;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.Mapper;
import com.example.magnificentchef.view.common.Ingredient;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MealDetailsFragment extends
        Fragment
        implements NetworkDelegate<MealsItem>,
        MealDetailsPresenterInterface,
        PlannedDelegate,
        SavePlanMealDelegate,
        FavouriteMealDelegate,
        FavouriteDelegate {

    private String mealsItemID;
    private String mealResourceType;
    private MealDetailsPresenter mealDetailsPresenter;
    private TextView categoryTextView, mealNameTextView, countryTextView, directionTextView;
    private ImageView mealImageView;
    private Button watchButton;
    private RecyclerView ingredientsRecyclerView;
    private Intent intent;
    private YouTubePlayerView youTubePlayerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealsItemID = MealDetailsFragmentArgs
                .fromBundle(getArguments())
                .getMealID();
        mealResourceType = MealDetailsFragmentArgs.fromBundle(getArguments()).getMealResourceType();
        intent  = new Intent(Intent.ACTION_VIEW);
        mealDetailsPresenter = new MealDetailsPresenter(new Repository(this,
                Remote.getRetrofitInstance()),this,
                new PlanSaveRepository(Local.getLocal(requireContext()),this),
                new FavouriteRepository(Local.getLocal(requireContext()),this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mealDetailsPresenter.checkMealResourceType(mealResourceType);
    }

    private void initView(View view){
        categoryTextView = view.findViewById(R.id.categoryTextView);
        mealNameTextView = view.findViewById(R.id.mealNameTextView);
        countryTextView = view.findViewById(R.id.countryTextView);
        directionTextView = view.findViewById(R.id.directionTextView);
        watchButton =  view.findViewById(R.id.watchButton);
        mealImageView =  view.findViewById(R.id.mealImage);
        ingredientsRecyclerView =  view.findViewById(R.id.ingredientsRecyclerView);
        youTubePlayerView = view.findViewById(R.id.youtubeVideo);
        View back = view.findViewById(R.id.back);
        back.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
        getLifecycle().addObserver(youTubePlayerView);
    }

    private void setMealItemIntoViews(MealsItem mealsItem){
        categoryTextView.setText(mealsItem.getStrCategory());
        Glide.with(requireContext()).load(mealsItem.getStrMealThumb()).into(mealImageView);
        mealNameTextView.setText(mealsItem.getStrMeal());
        countryTextView.setText(mealsItem.getStrArea());
        directionTextView.setText(mealsItem.getStrInstructions());
        watchButton.setOnClickListener((v)->{
            intent.setData(Uri.parse(mealsItem.getStrYoutube()));
            intent.setPackage("com.google.android.youtube");
            startActivity(intent);
        });
        try {

            ingredientsRecyclerView
                    .setAdapter(new IngredientsAdapter(mealDetailsPresenter
                            .getIngredient(mealsItem)));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(mealDetailsPresenter.getYoutubeCode(mealsItem.getStrYoutube()),0f);
            }
        });
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
        setMealItemIntoViews(itemList.get(0));
    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void onGetMealFromRetrofit() {
        mealDetailsPresenter.getMealByLetter(mealsItemID);
    }

    @Override
    public void onGetPlannedMealFromDatabase() {
        mealDetailsPresenter.getPlannedMealByID(mealsItemID,this);
    }

    @Override
    public void onGetFavouriteMealFromDatabase() {
        mealDetailsPresenter.getFavouriteMealByID(mealsItemID,this);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onSuccessSavePlannedMeal(List<PlanMeal> planMealList) {

    }

    @Override
    public void onGetPlannedMeal(PlanMeal planMeal) {
        setMealItemIntoViews(Mapper.convert(planMeal));
    }

    @Override
    public void onSuccess(List<FavouriteMeal> favouriteMeals) {

    }

    @Override
    public void onFavouriteMeal(FavouriteMeal favouriteMeal) {
        setMealItemIntoViews(Mapper.convert(favouriteMeal));
    }
}