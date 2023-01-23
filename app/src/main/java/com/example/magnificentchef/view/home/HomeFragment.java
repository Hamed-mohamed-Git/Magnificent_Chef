package com.example.magnificentchef.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magnificentchef.R;
import com.example.magnificentchef.network.NetworkDelegate;
import com.example.magnificentchef.network.Remote;
import com.example.magnificentchef.network.Repository;
import com.example.magnificentchef.network.model.MealsItem;
import com.example.magnificentchef.view.common.Meal;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.home.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements NetworkDelegate<MealsItem> {

    private RecyclerView dailyInspirationRecyclerView;
    private HomePresenter homePresenter;
    private RecyclerView mealRecyclerView;
    private RecyclerView moreYouLikeRecyclerView;
    private List<Meal> mealList;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController =
                ((NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        homePresenter = new HomePresenter(new Repository(this, Remote.getRetrofitInstance()));
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
        for (int itemLoop = 0; itemLoop < itemList.size();itemLoop++){
            if (itemLoop < 7)
                inspirationMealList.add(itemList.get(itemLoop));
            else if (itemLoop < 17)
                mealItemList.add(itemList.get(itemLoop));
            else
                moreYouLikeMealList.add(itemList.get(itemLoop));
        }
        dailyInspirationRecyclerView.setAdapter(new MealsAdapter(R.layout.daily_inspiration_card,inspirationMealList,navController));
        mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealItemList,navController));
        moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,moreYouLikeMealList,navController));
    }

    //mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealList));
    //moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,mealList));
    //DisplayMetrics displayMetrics=new DisplayMetrics();
    //requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //displayMetrics.widthPixels
}