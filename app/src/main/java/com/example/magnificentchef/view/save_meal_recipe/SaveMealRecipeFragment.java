package com.example.magnificentchef.view.save_meal_recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanSaveRepository;
import com.example.magnificentchef.model.local.plan_meal.SavePlanMealDelegate;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.common.ConnectionState;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class SaveMealRecipeFragment extends Fragment implements NetworkDelegate<MealsItem>,
        TextWatcher,
        SavePlanMealDelegate,
        OnAddMealPlan,
        ConnectionState {

    private RecyclerView recyclerView;
    private FavouriteMealsAdapter favouriteMealsAdapter;
    private TextInputEditText searchTextInputEditText;
    private List<MealsItem> mealsItems;
    private SaveMealRecipePresenter saveMealRecipePresenter;
    private String day;
    private Group failedConnection, connection;
    private ImageView closeButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealsItems = new ArrayList<>();
        favouriteMealsAdapter = new FavouriteMealsAdapter(R.layout.search_plan_meals_card,mealsItems,this);
        saveMealRecipePresenter = new SaveMealRecipePresenter(new Repository(this,
                Remote.getRetrofitInstance()),
                new PlanSaveRepository(Local.getLocal(requireContext()),
                        this),
                this,
                requireActivity().getApplicationContext());
        day = SaveMealRecipeFragmentArgs
                .fromBundle(getArguments())
                .getDay();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_save_meal_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        saveMealRecipePresenter.checkConnectionChange();
        closeButton.setOnClickListener(view1 -> {
            Navigation.findNavController(view).popBackStack();
        });
    }


    private void initView(View view){
        recyclerView = view.findViewById(R.id.planSearchRecyclerView);
        searchTextInputEditText = view.findViewById(R.id.searchTextInputEditText);
        connection = view.findViewById(R.id.internet_connection_view);
        failedConnection = view.findViewById(R.id.failed_internet_connection_view);
        closeButton = view.findViewById(R.id.closeButton);
        searchTextInputEditText.addTextChangedListener(this);
        recyclerView.setAdapter(favouriteMealsAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()==1 && !charSequence.toString().contains("\n")){
            saveMealRecipePresenter.getMealsByKey(charSequence.toString());
        }
        else if (charSequence.length() > 1){
            Observable<MealsItem> mealsItemObservable=Observable.fromIterable(mealsItems);
            mealsItemObservable.filter(mealsItem -> mealsItem.getStrMeal()
                            .startsWith(charSequence.toString()))
                    .toList()
                    .doOnSuccess(favouriteMealsAdapter::setMealItemList)
                    .subscribe();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
        if (itemList != null){
            favouriteMealsAdapter.setMealItemList(itemList);
            mealsItems = itemList;
        }

    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onSuccessSavePlannedMeal(List<PlanMeal> planMeals) {

    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onAddClickMealListener(MealsItem mealsItem) {
        saveMealRecipePresenter.savePlaneMeal(mealsItem,day);
    }


    @Override
    public void onInternetAvailable() {
        saveMealRecipePresenter.getMealsByKey("a");
        failedConnection.setVisibility(View.GONE);
        connection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInternetLost() {
        failedConnection.setVisibility(View.VISIBLE);
        connection.setVisibility(View.GONE);
    }
}