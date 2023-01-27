package com.example.magnificentchef.view.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Toast;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.base.BaseFragment;
import com.example.magnificentchef.view.base.BaseFragmentDirections;
import com.example.magnificentchef.view.search.model.Ingredients;
import com.example.magnificentchef.view.search.model.RootMeal;
import com.example.magnificentchef.view.search.model.Custom;
import com.example.magnificentchef.view.search.network.ApiSearch;
import com.example.magnificentchef.view.search.presenter.OnAreaItemClickListener;
import com.example.magnificentchef.view.search.presenter.OnCategoryClickListener;
import com.example.magnificentchef.view.search.presenter.OnSearchItemListener;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCategories;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCountres;
import com.example.magnificentchef.view.search.presenter.SearchAdapterIngredients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class SearchFragment extends Fragment implements TextWatcher, OnSearchItemListener, OnAreaItemClickListener, OnCategoryClickListener {
    private RecyclerView recyclerView, recyclerView2,recyclerView3;
    private SearchAdapterIngredients searchAdapterIngredients;
    private SearchAdapterCategories SearchAdapterCategories;
    private SearchAdapterCountres searchAdapterCountres;
    private List<Ingredients> ingredientsList;
    private List<Custom> countryList;
    private List<Custom> categoryList;
    private String[]country_name;
    private int[]country_images;
    private View view;
    private EditText search;
    private NavHostFragment navHostFragment;
    private NavController navController;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientsList = new ArrayList<>();
        countryList = new ArrayList<>();
        categoryList = new ArrayList<>();
        country_name=new String[]{"American","Spanish","Indian","Japanese","British","French","Chinese","Egyptian","Italian","Turkish"};
        country_images=new int[]{R.drawable.usa,R.drawable.span,R.drawable.india,R.drawable.japan,R.drawable.uk,R.drawable.franch,R.drawable.china,R.drawable.egypt,R.drawable.italy,R.drawable.turkey};
        navHostFragment =(NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController= navHostFragment.getNavController();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        search=view.findViewById(R.id.editTextTextPersonName);
        search.addTextChangedListener(this);
        Single<RootMeal>call=ApiSearch.getClient()
                .rootMealSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        call.subscribe(new SingleObserver<RootMeal>() {
            @Override
            public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull RootMeal rootMeal) {
                ingredientsList = rootMeal.getMeals();
                recyclerView2 = view.findViewById(R.id.recyclerView2);
                recyclerView2.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                recyclerView2.setLayoutManager(linearLayoutManager);
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                searchAdapterIngredients = new SearchAdapterIngredients(ingredientsList,SearchFragment.this::onSuccessClickItemListener);
                recyclerView2.setAdapter(searchAdapterIngredients);


            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Toast.makeText(requireContext(),"fail"+ e.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        recyclerView = view.findViewById(R.id.country_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager2);


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
                recyclerView.setAdapter(searchAdapterCountres);



        recyclerView3 = view.findViewById(R.id.recyclerView);
        recyclerView3.setHasFixedSize(true);

    categoryList = Arrays.asList(
                new Custom("Breakfast", R.drawable.breakfast),
                new Custom("Beef", R.drawable.lunch),
                new Custom("Chicken", R.drawable.dinner),
                new Custom("Dessert", R.drawable.dessert),
                new Custom("Pasta", R.drawable.pasta_img),
                new Custom("Starter", R.drawable.appetizer)
        );
        SearchAdapterCategories = new SearchAdapterCategories(categoryList,this);
        recyclerView3.setAdapter(SearchAdapterCategories);



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
}



