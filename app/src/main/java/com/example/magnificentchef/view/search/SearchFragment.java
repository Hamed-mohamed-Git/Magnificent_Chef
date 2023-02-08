package com.example.magnificentchef.view.search;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.remote.model.ingredient_model.MealsItem;
import com.example.magnificentchef.model.remote.IngredientNetworkDelegate;
import com.example.magnificentchef.model.remote.NetworkDelegate;
import com.example.magnificentchef.model.remote.Remote;
import com.example.magnificentchef.model.remote.Repository;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.search.model.Custom;
import com.example.magnificentchef.view.search.presenter.OnAreaItemClickListener;
import com.example.magnificentchef.view.search.presenter.OnCategoryClickListener;
import com.example.magnificentchef.view.search.presenter.OnSearchItemListener;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCategories;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCountres;
import com.example.magnificentchef.view.search.presenter.SearchAdapterIngredients;
import com.example.magnificentchef.view.search.presenter.SearchInterface;
import com.example.magnificentchef.view.search.presenter.SearchPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchFragment extends Fragment implements TextWatcher,
        OnSearchItemListener,
        OnAreaItemClickListener,
        OnCategoryClickListener,
        NetworkDelegate<com.example.magnificentchef.model.remote.model.MealsItem>,
        IngredientNetworkDelegate,
        SearchInterface {
    private RecyclerView countriesRecyclerView, ingredientsRecyclerView, mealCategoryRecyclerView;
    private SearchAdapterIngredients searchAdapterIngredients;
    private SearchAdapterCategories SearchAdapterCategories;
    private SearchAdapterCountres searchAdapterCountres;
    private List<Custom> countryList;
    private List<Custom> categoryList;
    private Group failedConnection, connection;
    private EditText search;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private SearchPresenter searchPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchPresenter=new SearchPresenter(new Repository(this,Remote.getRetrofitInstance()),
                this,
                this,
                requireContext());
        countryList = new ArrayList<>();
        categoryList = new ArrayList<>();
        navHostFragment =(NavHostFragment) requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController= navHostFragment.getNavController();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        searchPresenter.checkConnection();
    }

    private void initView(View view){
        search=view.findViewById(R.id.editTextTextPersonName);
        connection = view.findViewById(R.id.internet_connection_view);
        failedConnection = view.findViewById(R.id.failed_internet_connection_view);
        countriesRecyclerView = view.findViewById(R.id.country_recycle_view);
        mealCategoryRecyclerView = view.findViewById(R.id.recyclerView);
        ingredientsRecyclerView = view.findViewById(R.id.recyclerView2);
        ingredientsRecyclerView.setHasFixedSize(true);
        mealCategoryRecyclerView.setHasFixedSize(true);
        countriesRecyclerView.setHasFixedSize(true);
        search.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() > 0)
             navController.navigate(BaseFragmentDirections
                 .actionBaseFragmentToRecentSearchFragment("a","a")
                             .setKey("s")
                 .setLetters(charSequence.toString()));
    }

    @Override
    public void onPause() {
        search.getText().clear();
        super.onPause();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onSuccessClickItemListener(String itemData) {
        navController.navigate(BaseFragmentDirections.
                actionBaseFragmentToRecentSearchFragment("a","i")
                        .setKey("i")
                .setLetters(itemData));
    }

    @Override
    public void onClickItemListener(String itemData) {
        navController.navigate(BaseFragmentDirections.
                actionBaseFragmentToRecentSearchFragment("Egyptian","a")
                        .setKey("a")
                        .setLetters(itemData));

    }

    @Override
    public void onCategoryClickItemListener(String itemData) {
        navController.navigate(BaseFragmentDirections.
                actionBaseFragmentToRecentSearchFragment("breakfast","c")
                .setKey("c")
                .setLetters(itemData));
    }

    @Override
    public void onSuccessResult(List itemList) {

    }

    @Override
    public void onFailureResult(String message) {

    }

    @Override
    public void onSuccessIngredientsResult(List<MealsItem> ingredientsListList) {
        searchAdapterIngredients = new SearchAdapterIngredients(ingredientsListList,SearchFragment.this::onSuccessClickItemListener);
        ingredientsRecyclerView.setAdapter(searchAdapterIngredients);

    }

    @Override
    public void onFailIngredientsResult(String error) {

    }

    @Override
    public void onInternetAvailable() {
        setSearchCountry();
        setSearchCategory();
        searchPresenter.getIngredientData();
        failedConnection.setVisibility(View.GONE);
        connection.setVisibility(View.VISIBLE);
    }
    @Override
    public void onInternetLost() {
        failedConnection.setVisibility(View.VISIBLE);
        connection.setVisibility(View.GONE);
    }

    public void setSearchCountry (){

        countryList = Arrays.asList(
                new Custom("American", R.drawable.usa),
                new Custom("British", R.drawable.uk),
                new Custom("Chinese", R.drawable.china),
                new Custom("French", R.drawable.franch),
                new Custom("Spanish", R.drawable.span),
                new Custom("Turkish", R.drawable.turkey),
                new Custom("Indian", R.drawable.india),
                new Custom("Italian", R.drawable.italy),
                new Custom("Egyptian", R.drawable.egypt),
                new Custom("Japanese", R.drawable.japan)
        );
        searchAdapterCountres = new SearchAdapterCountres(countryList,this);
        countriesRecyclerView.setAdapter(searchAdapterCountres);
    }

    public void setSearchCategory(){
        categoryList = Arrays.asList(
                new Custom("Breakfast", R.drawable.breakfast),
                new Custom("Beef", R.drawable.lunch),
                new Custom("Chicken", R.drawable.dinner),
                new Custom("Dessert", R.drawable.dessert),
                new Custom("Pasta", R.drawable.pasta_img),
                new Custom("Starter", R.drawable.appetizer)
        );
        SearchAdapterCategories = new SearchAdapterCategories(categoryList,this);
        mealCategoryRecyclerView.setAdapter(SearchAdapterCategories);

    }
}



