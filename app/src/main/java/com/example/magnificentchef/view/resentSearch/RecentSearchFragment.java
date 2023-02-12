package com.example.magnificentchef.view.resentSearch;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
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
import android.widget.ImageView;

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
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.view.common.MealsAdapter;
import com.example.magnificentchef.view.common.OnMealClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Observable;

public class RecentSearchFragment extends Fragment implements NetworkDelegate<MealsItem>,
        TextWatcher ,OnMealClickListener,
        FavouriteMealDelegate,MealNetworkDelegate,OnSearchCheckListener{
//    private RecentSearchAdapter recentSearchAdapter;
    private MealsAdapter mealsAdapter;
    private RecentSearchPresenter recentSearchPresenter;
    private List<MealsItem> mealsItems;
    private RecyclerView recyclerView;
    private EditText search;
    private NavController navController;
    private FavouriteRepository favouriteRepository;
    private Pattern pattern;
    private ImageView closeButton;
    private Group failedConnection, connection;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mealsItems = new ArrayList<>();
        mealsAdapter=new MealsAdapter(R.layout.more_you_might_card,mealsItems,navController,this);
        recentSearchPresenter = new RecentSearchPresenter(new Repository(this,
                Remote.getRetrofitInstance()),
                new FavouriteRepository(Local.getLocal(requireContext()),
                        this),getActivity().getApplicationContext(),this);
        pattern = Pattern.compile(Constants.SEARCH_PATTERN);
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
        initView(view);
        recentSearchPresenter.checkConnectionChange();
        closeButton.setOnClickListener(view1 -> {
            navController.popBackStack();
        });
        search.addTextChangedListener(this);
    }

    private void initView(View view){
        recyclerView =view.findViewById(R.id.result_search);
        search = view.findViewById(R.id.search_edt);
        closeButton = view.findViewById(R.id.closeButton);
        connection = view.findViewById(R.id.internet_connection_view);
        failedConnection = view.findViewById(R.id.failed_internet_connection_view);
    }

    @Override
    public void onSuccessResult(List<MealsItem> itemList) {
        if (itemList != null){
            mealsAdapter.setMealItemList(itemList);
            mealsItems=itemList;
        }
    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()==1 && pattern.matcher(charSequence.toString()).matches()) {
            recentSearchPresenter.getMealsByKey(charSequence.toString());
        }
        else if (charSequence.length() > 1&& pattern.matcher(charSequence.toString()).matches()){
            Observable<MealsItem> mealsItemObservable=Observable.fromIterable(mealsItems);
            mealsItemObservable.filter(mealsItem -> mealsItem.getStrMeal().toLowerCase()
                            .startsWith(charSequence.toString().toLowerCase()))
                    .toList()
                    .doOnSuccess(mealsAdapter::setMealItemList)
                    .subscribe();
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
                    .actionRecentSearchFragmentToMealDetailsFragment("00000","")
                    .setMealResourceType("retrofit")
                    .setMealID(mealResponseList.get(0).getIdMeal()));

//            .actionRecentSearchFragmentToMealDetailsFragment(mealResponseList.get(0))
//                    .setMealItem(mealResponseList.get(0)));

        }
        else{
            try {
                recentSearchPresenter.RecentSearchFavouriteMeal(mealResponseList.get(0));
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

    @Override
    public void onSearchCategoryListener(String key) {
        recentSearchPresenter.getMealsByCategory(key);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onSearchListener(String key) {
        search.setText(key);
        recentSearchPresenter.getMealsByKey(key);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onSearchAreaListener(String key) {
        recentSearchPresenter.getMealsByArea(key);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onSearchIngredientListener(String key) {
        recentSearchPresenter.getMealsByIngredient(key);
        recyclerView.setAdapter(mealsAdapter);
    }

    @Override
    public void onInternetAvailable() {
        recentSearchPresenter.checkSearchType(RecentSearchFragmentArgs.fromBundle(getArguments()).getKey()
                ,RecentSearchFragmentArgs.fromBundle(getArguments()).getLetters());
        failedConnection.setVisibility(View.GONE);
        connection.setVisibility(View.VISIBLE);
    }

    @Override
    public void onInternetLost() {
        failedConnection.setVisibility(View.VISIBLE);
        connection.setVisibility(View.GONE);
    }
}