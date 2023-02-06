package com.example.magnificentchef.view.home;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.example.magnificentchef.view.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements RandomMealDelegate, NetworkDelegate<MealsItem>, OnMealClickListener, FavouriteMealDelegate {

    private ViewPager2 dailyInspirationRecyclerView;
    private HomePresenter homePresenter;
    private RecyclerView mealRecyclerView;
    private RecyclerView moreYouLikeRecyclerView;
    private NavController navController;
    private MealsAdapter inspirationMealAdapter,mealsAdapter,moreYouMightLikeAdapter;
    private int currentMealsCount;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private Group group;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentMealsCount = 0;
        navController =
                ((NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        homePresenter = new HomePresenter(new Repository(this,
                Remote.getRetrofitInstance()),
                new FavouriteRepository(Local.getLocal(requireContext()),this),getActivity().getApplicationContext());
        inspirationMealAdapter = new MealsAdapter(R.layout.daily_inspiration_card,new ArrayList<MealsItem>(),navController,this);
        mealsAdapter = new MealsAdapter(R.layout.meal_home_card,new ArrayList<MealsItem>(),navController,this);
        moreYouMightLikeAdapter = new MealsAdapter(R.layout.more_you_might_card,new ArrayList<MealsItem>(),navController,this);
        connectivityManager =
                requireContext().getSystemService(ConnectivityManager.class);
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                new Handler(Looper.getMainLooper()).post(() -> {
                    group.setVisibility(View.GONE);
                });

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);
                new Handler(Looper.getMainLooper()).post(() -> {
                    group.setVisibility(View.VISIBLE);
                });

            }
            @Override
            public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                //final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);

            }
        };
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
    }

    private void initView(View view){
        dailyInspirationRecyclerView = view.findViewById(R.id.dailyInspirationRecyclerView);
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView);
        moreYouLikeRecyclerView = view.findViewById(R.id.moreYouLikeRecyclerView);
        group = view.findViewById(R.id.base_view_group);
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
        dailyInspirationRecyclerView.setOffscreenPageLimit(10);
        dailyInspirationRecyclerView.setPageTransformer(new InspirationTransFormer(10));
        dailyInspirationRecyclerView.setAdapter(inspirationMealAdapter);
        mealRecyclerView.setAdapter(mealsAdapter);
        moreYouLikeRecyclerView.setAdapter(moreYouMightLikeAdapter);
        if (currentMealsCount < 40)
            homePresenter.getRandomMeal(40,this);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {

    }


    @Override
    public void onSuccessResult(MealsItem mealsItem) {
        if (currentMealsCount < 10){
            inspirationMealAdapter.addMeal(mealsItem);
        }else if (currentMealsCount < 20)
            mealsAdapter.addMeal(mealsItem);
        else if (currentMealsCount < 40)
            moreYouMightLikeAdapter.addMeal(mealsItem);
        currentMealsCount++;
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


    //mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealList));
    //moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,mealList));
    //DisplayMetrics displayMetrics=new DisplayMetrics();
    //requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //displayMetrics.widthPixels
}