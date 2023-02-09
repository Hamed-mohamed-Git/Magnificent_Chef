package com.example.magnificentchef.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.RandomMealDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.common.OnMealClickListener;
import com.example.magnificentchef.view.home.presenter.HomeInterface;
import com.example.magnificentchef.view.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements
        RandomMealDelegate,
        NetworkDelegate<MealsItem>,
        OnMealClickListener,
        FavouriteMealDelegate,
        HomeInterface {

    private ViewPager2 dailyInspirationRecyclerView;
    private HomePresenter homePresenter;
    private RecyclerView mealRecyclerView;
    private RecyclerView moreYouLikeRecyclerView;
    private NavController navController;
    private MealsAdapter inspirationMealAdapter,mealsAdapter,moreYouMightLikeAdapter;
    private Group failedConnection, connection;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController =
                ((NavHostFragment) requireActivity()
                        .getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment))
                        .getNavController();
        homePresenter = new HomePresenter(new Repository(this,
                Remote.getRetrofitInstance()),
                new FavouriteRepository(Local.getLocal(requireContext()),this),
                this,
                getActivity().getApplicationContext());
        inspirationMealAdapter = new MealsAdapter(R.layout.daily_inspiration_card,
                new ArrayList<>(),
                navController,
                this);
        mealsAdapter = new MealsAdapter(R.layout.meal_home_card,
                new ArrayList<>(),
                navController,
                this);
        moreYouMightLikeAdapter = new MealsAdapter(R.layout.more_you_might_card,
                new ArrayList<>(),
                navController,
                this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        homePresenter.checkConnectionChange();
    }

    private void initView(View view){
        dailyInspirationRecyclerView = view.findViewById(R.id.dailyInspirationRecyclerView);
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView);
        moreYouLikeRecyclerView = view.findViewById(R.id.moreYouLikeRecyclerView);
        connection = view.findViewById(R.id.internet_connection_view);
        failedConnection = view.findViewById(R.id.failed_internet_connection_view);
        dailyInspirationRecyclerView.setOffscreenPageLimit(10);
        dailyInspirationRecyclerView.setPageTransformer(new InspirationTransFormer(10));
        dailyInspirationRecyclerView.setAdapter(inspirationMealAdapter);
        mealRecyclerView.setAdapter(mealsAdapter);
        moreYouLikeRecyclerView.setAdapter(moreYouMightLikeAdapter);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {

    }


    @Override
    public void onSuccessResult(MealsItem mealsItem) {
        homePresenter.divideMeals(mealsItem);
    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMealClickListener(MealsItem meal) {
        navController.navigate(BaseFragmentDirections.actionBaseFragmentToMealDetailsFragment(meal).setMealItem(meal));
    }

    @Override
    public void onMealFavouriteClickListener(MealsItem favouriteMeal) {
        try {
            homePresenter.favouriteMeal(favouriteMeal);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(List<FavouriteMeal> favouriteMeals) {

    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(String errorMessage) {}

    @Override
    public void setDailyInspirationMeal(MealsItem mealsItem) {
        inspirationMealAdapter.addMeal(mealsItem);
    }

    @Override
    public void setMealData(MealsItem mealsItem) {
        mealsAdapter.addMeal(mealsItem);
    }

    @Override
    public void setMoreYouLikeMeal(MealsItem mealsItem) {
        moreYouMightLikeAdapter.addMeal(mealsItem);
    }

    @Override
    public void onInternetAvailable() {
        homePresenter.getRandomMeal(40,this);
        failedConnection.setVisibility(View.GONE);
        connection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInternetLost() {
        failedConnection.setVisibility(View.VISIBLE);
        connection.setVisibility(View.GONE);
    }
}





//mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealList));
//moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,mealList));
//DisplayMetrics displayMetrics=new DisplayMetrics();
//requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//displayMetrics.widthPixels