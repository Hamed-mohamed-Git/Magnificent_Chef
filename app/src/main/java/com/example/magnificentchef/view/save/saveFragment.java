package com.example.magnificentchef.view.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.Mapper;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.plan.PlannedMealsAdapter;
import com.example.magnificentchef.view.plan.presenter.ClickAddPlanListener;
import com.example.magnificentchef.view.save.prsenter.SavePresenter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class saveFragment extends Fragment implements ClickAddPlanListener<FavouriteMeal>, FavouriteMealDelegate {
    private PlannedMealsAdapter plannedMealsAdapter;
    private List<FavouriteMeal> favouriteMeals;
    private SavePresenter savePresenter;
    private RecyclerView recyclerView;

       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savePresenter=new SavePresenter(new FavouriteRepository(Local
                .getLocal(requireContext()),
                this));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.saveMeaslsRecyclerView);
        savePresenter.getFavouriteMeals();


    }

    @Override
    public void onClickAddPlanListener(String day) {

    }

    @Override
    public void onPlannedMealClick(FavouriteMeal favouriteMeal) {

        ((NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment))
                .getNavController()
                .navigate(BaseFragmentDirections
                        .actionBaseFragmentToMealDetailsFragment(new MealsItem())
                        .setMealItem(Mapper.convert(favouriteMeal)));
    }

    @Override
    public void onDeleteMealClick(FavouriteMeal favouriteMeal) {
           savePresenter.deleteFavouriteMeals(favouriteMeal);

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccess(List<FavouriteMeal> favouriteMeals) {
           recyclerView.setAdapter(new FavouriteMealsAdapter(R.layout.planned_meal_card,favouriteMeals,this));

    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(String errorMessage) {

    }
}