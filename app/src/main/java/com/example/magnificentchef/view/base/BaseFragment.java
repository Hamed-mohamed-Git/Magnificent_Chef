package com.example.magnificentchef.view.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.common.MealsDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.local.plan_meal.SavePlanMealDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreDelegate;
import com.example.magnificentchef.model.remote.firebase.FireStoreGetPlane;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.view.base.presenter.BaseInterfce;
import com.example.magnificentchef.view.base.presenter.BasePresenter;
import com.example.magnificentchef.view.common.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class BaseFragment extends Fragment implements BaseInterfce,
        FavouriteMealDelegate,SavePlanMealDelegate, MealsDelegate, FireStoreDelegate, FireStoreGetPlane {
    private BottomNavigationView bottomNavigationView;
    private NavController navcontroller;
    private AppBarConfiguration appBarConfiguration;
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private CircleImageView user_image;
    private TextView user_name;
    private TextView user_email;
    private TextView application_name;
    private BasePresenter basePresenter;
    private SharedPreferences.Editor sharedPrefEditor;
    private View logout;
    private String key;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        sharedPrefEditor = requireContext()
                .getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE).edit();
        basePresenter=new BasePresenter(firebaseUser,
                this,
                new FavouriteRepository(Local.getLocal(requireContext()),this),
                new PlanSaveRepository(Local.getLocal(requireContext()),this),
                new FireStoreRepository(FirebaseFirestore.getInstance(),FirebaseAuth.getInstance()),
                this,this,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, view, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView=view.findViewById(R.id.bottomNavigationView);
        navcontroller= Navigation.findNavController(requireActivity(),R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navcontroller);
        user_name=view.findViewById(R.id.name_tv);
        user_email=view.findViewById(R.id.email_tv);
        user_image=view.findViewById(R.id.circleImageView);
        application_name=view.findViewById(R.id.application_name);
        logout = view.findViewById(R.id.logoutButton);
        key = BaseFragmentArgs.fromBundle(getArguments()).getKey();
        logout.setOnClickListener(view1 -> {
            basePresenter.clearDatabaseTables();
            FirebaseAuth.getInstance().signOut();
            sharedPrefEditor.putString(getString(R.string.preference_file_key), Constants.UN_REGISTERED);
            sharedPrefEditor.apply();
            Navigation
                    .findNavController(view1)
                    .navigate(R.id.action_baseFragment_to_registerFragment);
        });
        basePresenter.check();
    }


    @Override
    public void onLoggedUser() {

        application_name.setVisibility(View.INVISIBLE);
        user_email.setVisibility(View.VISIBLE);
        user_name.setVisibility(View.VISIBLE);
        user_email.setText(firebaseUser.getEmail().toString());
        user_name.setText(firebaseUser.getDisplayName().toString());
        if(firebaseUser.getPhotoUrl()==null){
            user_image.setImageResource(R.drawable.person_black);
        }
        else{
            Glide.with(requireContext())
                    .load(firebaseUser.getPhotoUrl())
                    .into(user_image);
        }
        if (key.equals("google") || key.equals("signin")){
            basePresenter.checkSaveFavouriteMeal();
            basePresenter.checkSavePlanMeal();
        }
    }

    @Override
    public void onGuestUser() {

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
    public void onSuccess(List<FavouriteMeal> favouriteMeals) {

    }

    @Override
    public void onFavouriteMealCount(int count) {
        basePresenter.getMealsFavouriteMeals(count);
    }

    @Override
    public void onPlannedMealCount(int count) {
        basePresenter.getMealsPlanMeals(count);
    }

    @Override
    public void onFavouriteMealList(List<FavouriteMeal> favouriteMeals) {
        basePresenter.setMealsFavouriteMeals(favouriteMeals,requireActivity().getApplicationContext());
    }

    @Override
    public void onPlannedMealList(List<PlanMeal> planMeals) {
        basePresenter.setMealsPlanMeals(planMeals, requireActivity().getApplicationContext());
    }
}


