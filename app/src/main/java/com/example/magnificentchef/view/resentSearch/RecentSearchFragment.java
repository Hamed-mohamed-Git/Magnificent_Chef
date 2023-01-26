package com.example.magnificentchef.view.resentSearch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.Local;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMealDelegate;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteRepository;
import com.example.magnificentchef.model.remote.MealNetworkDelegate;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.common.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;

public class RecentSearchFragment extends Fragment implements NetworkDelegate<MealsItem>, TextWatcher ,OnMealClickListener, FavouriteMealDelegate,MealNetworkDelegate{
//    private RecentSearchAdapter recentSearchAdapter;
    private MealsAdapter mealsAdapter;
    private RecentSearchPresenter recentSearchPresenter;
    private List<MealsItem> mealsItems;
    private RecyclerView recyclerView;
    private EditText search;
    private NavController navController;
    private FavouriteRepository favouriteRepository;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mealsItems = new ArrayList<>();
        mealsAdapter=new MealsAdapter(R.layout.more_you_might_card,mealsItems,navController,this);
        recentSearchPresenter = new RecentSearchPresenter(new Repository(this, Remote.getRetrofitInstance()),new FavouriteRepository(Local.getLocal(requireContext()),this),getActivity().getApplicationContext());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController =
                ((NavHostFragment) requireActivity().
                        getSupportFragmentManager().
                        findFragmentById(R.id.nav_host_fragment)).
                        getNavController();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recent_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.result_search);
        search = view.findViewById(R.id.search_edt);
        search.addTextChangedListener(this);
       // search.setText(RecentSearchFragmentArgs.fromBundle(getArguments()).getLetters());
        //recentSearchPresenter.getMealsByKey(RecentSearchFragmentArgs.fromBundle(getArguments()).getLetters());
        //recentSearchPresenter.getMealsByIngredient();
        //recentSearchPresenter.getMealsByKey(RecentSearchFragmentArgs.fromBundle(getArguments()).getLetters());
        recentSearchPresenter.getMealsByArea(RecentSearchFragmentArgs.fromBundle(getArguments()).getLetters());
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
       mealsAdapter.setMealItemList(itemList);
       mealsItems=itemList;
    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()==1){
            recentSearchPresenter.getMealsByKey(charSequence.toString());
        }
        else{
//            Observable<MealsItem> mealsItemObservable=Observable.fromIterable(mealsItems);
//            mealsItemObservable.filter(mealsItem -> mealsItem.getStrMeal()
//                            .startsWith(charSequence.toString()))
//                    .toList()
//                    .doOnSuccess(mealsAdapter::setMealItemList)
//                    .subscribe();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onMealClickListener(MealsItem meal) {
        recentSearchPresenter.getMealsById(meal.getIdMeal(),"check",this);


    }

    @Override
    public void onMealFavouriteClickListener(MealsItem favouriteMeal) {
        recentSearchPresenter.getMealsById(favouriteMeal.getIdMeal(),"favourite",this);



    }

    @Override
    public void onSuccessMealResult(List<MealsItem> mealResponseList,String key) {
        if (key.equals("check")) {
            navController.navigate(RecentSearchFragmentDirections
                    .actionRecentSearchFragmentToMealDetailsFragment(mealResponseList.get(0))
                    .setMealItem(mealResponseList.get(0)));
        }
        else{
            try {
                recentSearchPresenter.RecentSearchfavouriteMeal(mealResponseList.get(0));
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }


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
    public void onError(String errorMessage) {

    }
}