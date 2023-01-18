package com.example.magnificentchef.view.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Ingredients;
import com.example.magnificentchef.view.search.model.RootMeal;
import com.example.magnificentchef.view.search.model.Custom;
import com.example.magnificentchef.view.search.network.ApiSearch;
import com.example.magnificentchef.view.search.network.ApiSearchInterface;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCategories;
import com.example.magnificentchef.view.search.presenter.SearchAdapterCountres;
import com.example.magnificentchef.view.search.presenter.SearchAdapterIngredients;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView, recyclerView2,recyclerView3;
    SearchAdapterIngredients searchAdapterIngredients;
    SearchAdapterCategories SearchAdapterCategories;
    SearchAdapterCountres searchAdapterCountres;
    List<Ingredients> data = new ArrayList<>();
    List<Custom> country = new ArrayList<>();
    List<Custom> category = new ArrayList<>();

    String[]country_name;
    int[]country_images;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        country_name=new String[]{"American","Spanish","Indian","Japanese","British","French","Chinese","Egyptian","Italian","Turkish"};
        country_images=new int[]{R.drawable.usa,R.drawable.span,R.drawable.india,R.drawable.japan,R.drawable.uk,R.drawable.franch,R.drawable.china,R.drawable.egypt,R.drawable.italy,R.drawable.turkey};


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit apiClient = ApiSearch.getClient();
        ApiSearchInterface apiInterface = apiClient.create(ApiSearchInterface.class);
        Call<RootMeal> call = apiInterface.getProducts();
        call.enqueue(new Callback<RootMeal>() {
            @Override
            public void onResponse(Call<RootMeal> call, Response<RootMeal> response) {
                if (response.isSuccessful()) {
                    data = response.body().getMeals();

                    recyclerView2 = view.findViewById(R.id.recyclerView2);
                    recyclerView2.setHasFixedSize(true);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
                    recyclerView2.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                    searchAdapterIngredients = new SearchAdapterIngredients(data);
                    recyclerView2.setAdapter(searchAdapterIngredients);
                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {

            }
        });


        recyclerView = view.findViewById(R.id.country_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(requireContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager2);

         country = Arrays.asList(
                new Custom("USA", R.drawable.usa),
                new Custom("Uk", R.drawable.uk),
                new Custom("China", R.drawable.china),
                new Custom("Franch", R.drawable.franch),
                new Custom("Span", R.drawable.span),
                new Custom("Turkey", R.drawable.turkey),
                new Custom("India", R.drawable.india),
                 new Custom("Italy", R.drawable.italy),
                 new Custom("Egypt", R.drawable.egypt),
                 new Custom("Japan", R.drawable.japan)
                 );
                searchAdapterCountres = new SearchAdapterCountres(country);
                recyclerView.setAdapter(searchAdapterCountres);



        recyclerView3 = view.findViewById(R.id.recyclerView);
        recyclerView3.setHasFixedSize(true);
        /*LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(requireContext());
        linearLayoutManager3.setOrientation(RecyclerView.VERTICAL);
        recyclerView3.setLayoutManager(linearLayoutManager3);
*/
        category = Arrays.asList(
                new Custom("Breakfast", R.drawable.usa),
                new Custom("Lunch", R.drawable.uk),
                new Custom("Dinner", R.drawable.china),
                new Custom("Dessert", R.drawable.franch),
                new Custom("Drinks", R.drawable.span),
                new Custom("Appetizers", R.drawable.turkey)
        );
        SearchAdapterCategories = new SearchAdapterCategories(category);
        recyclerView3.setAdapter(SearchAdapterCategories);



    }
    }



