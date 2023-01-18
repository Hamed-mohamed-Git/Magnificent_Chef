package com.example.magnificentchef.view.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        homePresenter = new HomePresenter(new Repository(this, Remote.getRetrofitInstance()));
        homePresenter.getRandomMeal(30);
    }

    private void initView(View view){
        dailyInspirationRecyclerView = view.findViewById(R.id.dailyInspirationRecyclerView);
        mealRecyclerView = view.findViewById(R.id.mealRecyclerView);
        moreYouLikeRecyclerView = view.findViewById(R.id.moreYouLikeRecyclerView);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
        dailyInspirationRecyclerView.setAdapter(new MealsAdapter(R.layout.daily_inspiration_card,itemList));
    }

    @Override
    public void onFailureResult(String message) {

    }

    //        mealList = new ArrayList<>();
//        mealList.add(new Meal("https://www.themealdb.com/images/media/meals/qqwypw1504642429.jpg","Pork","Vietnamese Grilled Pork (bun-thit-nuong)"));
//        mealList.add(new Meal("https://www.themealdb.com/images/media/meals/1520083578.jpg","Beef","Oxtail with broad beans"));
//        mealList.add(new Meal("https://www.themealdb.com/images/media/meals/45xxr21593348847.jpg","Side","Pierogi (Polish Dumplings)"));
//        mealList.add(new Meal("https://www.themealdb.com/images/media/meals/usywpp1511189717.jpg","Pasta","Chilli prawn linguine"));


    //mealRecyclerView.setAdapter(new MealsAdapter(R.layout.meal_home_card,mealList));
    //moreYouLikeRecyclerView.setAdapter(new MealsAdapter(R.layout.more_you_might_card,mealList));
    //DisplayMetrics displayMetrics=new DisplayMetrics();
    //requireActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    //displayMetrics.widthPixels
}