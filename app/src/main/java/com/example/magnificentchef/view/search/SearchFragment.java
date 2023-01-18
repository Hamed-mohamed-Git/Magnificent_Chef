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
import com.example.magnificentchef.view.search.network.ApiSearch;
import com.example.magnificentchef.view.search.network.ApiSearchInterface;
import com.example.magnificentchef.view.search.presenter.SearchAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView, recyclerView2;
    SearchAdapter searchAdapter;
    List<Ingredients> data = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    searchAdapter = new SearchAdapter(data);
                    recyclerView2.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onFailure(Call<RootMeal> call, Throwable t) {

            }
        });


    }
}


