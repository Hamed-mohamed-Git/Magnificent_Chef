package com.example.magnificentchef.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.common.Meal;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.common.OnMealClickListener;
import com.example.magnificentchef.view.home.presenter.HomePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements NetworkDelegate<MealsItem>, OnMealClickListener, FavouriteMealDelegate {

    private RecyclerView dailyInspirationRecyclerView;
    private HomePresenter homePresenter;
    private RecyclerView mealRecyclerView;
    private RecyclerView moreYouLikeRecyclerView;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController =
                ((NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        homePresenter = new HomePresenter(new Repository(this,
                Remote.getRetrofitInstance()),
                new FavouriteRepository(Local.getLocal(requireContext()),this),getActivity().getApplicationContext());
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
        homePresenter.getRandomMeal(24);
        new FireStoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
                .getSavedMeals();
        new FireStoreRepository(FirebaseFirestore.getInstance(), FirebaseAuth.getInstance())
                .getPlannedMeals();
    }

    private void initView(View view){
        dailyInspirationRecyclerView = view.findViewById(R.id.dailyInspirationRecyclerView);
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView);
        moreYouLikeRecyclerView = view.findViewById(R.id.moreYouLikeRecyclerView);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
        setAdapterMealItems(itemList);
    }


    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setAdapterMealItems(List<MealsItem> itemList) {
        List<MealsItem> inspirationMealList = new ArrayList<>();
        List<MealsItem> mealItemList = new ArrayList<>();
        List<MealsItem> moreYouLikeMealList = new ArrayList<>();
        for (int itemLoop = 0; itemLoop < 7;itemLoop++){
            inspirationMealList.add(itemList.get(itemLoop));
        }
        dailyInspirationRecyclerView.setAdapter(new MealsAdapter(R.layout.daily_inspiration_card,inspirationMealList,navController,this));
        for (int itemLoop = 7; itemLoop < 17;itemLoop++){
            mealItemList.add(itemList.get(itemLoop));
        }
        mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealItemList,navController,this));
        for (int itemLoop = 17; itemLoop < itemList.size();itemLoop++){

            moreYouLikeMealList.add(itemList.get(itemLoop));
        }
        moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,moreYouLikeMealList,navController,this));
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


    //mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealList));
    //moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,mealList));
    //DisplayMetrics displayMetrics=new DisplayMetrics();
    //requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //displayMetrics.widthPixels
}