package com.example.magnificentchef.view.plan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.local.plan_meal.SavePlanMealDelegate;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.utils.Mapper;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.plan.presenter.ClickAddPlanListener;
import com.example.magnificentchef.view.plan.presenter.PlanPresenter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class PlanMealFragment extends Fragment implements ClickAddPlanListener<PlanMeal>, SavePlanMealDelegate{

    private Button sunday;
    private Button monday;
    private Button tuesday;
    private Button wednesday;
    private Button thursday;
    private Button saturday;
    private Button friday;
    private RecyclerView sundayRecyclerView,
            mondayRecyclerView,
            tuesdayRecyclerView,
            wednesdayRecyclerView,
            thursdayRecyclerView,
            saturdayRecyclerView,
            fridayRecyclerView;
    private PlanPresenter planPresenter;
    private View view;
    private Group group;
    private TextView notAvailable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planPresenter = new PlanPresenter(this,
                new PlanSaveRepository(Local.getLocal(requireContext()),this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_plan_meal, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view = view;
        initView(view);
        planPresenter.getPlannedMeal();
        saturday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Saturday");
        }));
        sunday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Sunday");
        }));
        tuesday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Tuesday");
        }));
        wednesday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Wednesday");
        }));
        thursday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Thursday");
        }));
        monday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Monday");
        }));

        friday.setOnClickListener((view1 -> {
            planPresenter.addSearchedRecipe("Friday");
        }));
    }

    void initView(View view){
        saturday = view.findViewById(R.id.btn_saturday_add);
        sunday = view.findViewById(R.id.btn_sunday_add);
        tuesday = view.findViewById(R.id.btn_tuesday_add);
        wednesday = view.findViewById(R.id.btn_wednesday_add);
        thursday = view.findViewById(R.id.btn_thursday_add);
        monday = view.findViewById(R.id.btn_monday_add);
        friday = view.findViewById(R.id.btn_friday_add);
        group = view.findViewById(R.id.planGroup);
        notAvailable = view.findViewById(R.id.not_available);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            group.setVisibility(View.GONE);
            notAvailable.setVisibility(View.VISIBLE);
        }else {
            group.setVisibility(View.VISIBLE);
            notAvailable.setVisibility(View.GONE);
        }

        sundayRecyclerView = view.findViewById(R.id.sundayRecyclerView);
        mondayRecyclerView = view.findViewById(R.id.mondayRecyclerView);
        tuesdayRecyclerView = view.findViewById(R.id.tuesdayRecyclerView);
        wednesdayRecyclerView = view.findViewById(R.id.wednesdayRecyclerView);
        thursdayRecyclerView = view.findViewById(R.id.thursdayRecyclerView);
        saturdayRecyclerView = view.findViewById(R.id.saturdayRecyclerView);
        fridayRecyclerView = view.findViewById(R.id.fridayRecyclerView);
    }

    @Override
    public void onClickAddPlanListener(String day) {
        Navigation.findNavController(view).navigate(PlanMealFragmentDirections
                .actionPlanMealScreenToSaveMealRecipeFragment("monday")
                .setDay(day));
    }

    @Override
    public void onPlannedMealClick(PlanMeal planMeal) {
        ((NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment))
                .getNavController()
                .navigate(BaseFragmentDirections
                .actionBaseFragmentToMealDetailsFragment("00000","")
                        .setMealResourceType("localPlanMeal")
                        .setMealID(planMeal.getMeal_id()));
                //Mapper.convert(planMeal))
    }

    @Override
    public void onDeleteMealClick(PlanMeal planMeal) {
        planPresenter.deletePlannedMeal(planMeal);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccessSavePlannedMeal(List<PlanMeal> planMeals) {
        setPlannedMealToAdapters(planMeals);
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(String errorMessage) {

    }

    private void setPlannedMealToAdapters(List<PlanMeal> planMeals){
        List<PlanMeal> sundayMeals = new ArrayList<>();
        List<PlanMeal> mondayMeals = new ArrayList<>();
        List<PlanMeal> tuesdayMeals = new ArrayList<>();
        List<PlanMeal> wednesdayMeals = new ArrayList<>();
        List<PlanMeal> thursdayMeals = new ArrayList<>();
        List<PlanMeal> saturdayMeals = new ArrayList<>();
        List<PlanMeal> fridayMeals = new ArrayList<>();
        for (PlanMeal planMeal : planMeals){
            switch (planMeal.getDate()){
                case "Sunday":
                    sundayMeals.add(planMeal);
                    break;
                case "Monday":
                    mondayMeals.add(planMeal);
                    break;
                case "Tuesday":
                    tuesdayMeals.add(planMeal);
                    break;
                case "Wednesday":
                    wednesdayMeals.add(planMeal);
                    break;
                case "Thursday":
                    thursdayMeals.add(planMeal);
                    break;
                case "Saturday":
                    saturdayMeals.add(planMeal);
                    break;
                case "Friday":
                    fridayMeals.add(planMeal);
                    break;
            }
        }
        sundayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, sundayMeals, this));
        mondayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, mondayMeals, this));
        tuesdayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, tuesdayMeals, this));
        wednesdayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, wednesdayMeals, this));
        thursdayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, thursdayMeals, this));
        fridayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, fridayMeals, this));
        saturdayRecyclerView.setAdapter(new PlannedMealsAdapter(R.layout.planned_meal_card, saturdayMeals, this));
    }

}