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
import com.example.magnificentchef.view.search.presenter.SearchAdapter;

import java.util.Arrays;
import java.util.List;


public class SearchFragment extends Fragment {
    RecyclerView recyclerView,recyclerView2;
    SearchAdapter searchAdapter;
    List<Ingredients> data;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  country_images = new int[]
      //  ingredint_name=new String[]{"fish-Small.png","chicken-Small.png","tomato-Small.png","Lime-Small.png","egg-Small.png","beef-Small.png","Cucumber-Small.png"};
        //country_name=new String[]{"American","Spanish","Indian","Japanese","British","French","Chinese","Egyptian","Italian","Turkish"};
        //country_images=new int[]{R.drawable.usa,R.drawable.span,R.drawable.india,R.drawable.japan,R.drawable.uk,R.drawable.franch,R.drawable.china,R.drawable.egypt,R.drawable.italy,R.drawable.turkey};
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.country_recycle_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView.setLayoutManager(linearLayoutManager);
        data= Arrays.asList(
                new Ingredients("chicken",R.drawable.china),
                new Ingredients("Meat",R.drawable.china),
                new Ingredients("Pasta",R.drawable.china),
                new Ingredients("chicken",R.drawable.china),
                new Ingredients("Meat",R.drawable.china),
                new Ingredients("Pasta",R.drawable.china),
                new Ingredients("chicken",R.drawable.china),
                new Ingredients("Meat",R.drawable.china),
                new Ingredients("Pasta",R.drawable.china)



        );
        recyclerView2=view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(requireContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView2.setLayoutManager(linearLayoutManager2);
        data= Arrays.asList(
                new Ingredients("USA",R.drawable.usa),
                new Ingredients("Uk",R.drawable.uk),
                new Ingredients("China",R.drawable.china),
        new Ingredients("franch",R.drawable.franch),
        new Ingredients("span",R.drawable.span),
        new Ingredients("turkey",R.drawable.turkey),
                new Ingredients("india",R.drawable.india)




        );
        searchAdapter=new SearchAdapter(data);
        recyclerView2.setAdapter(searchAdapter);
        recyclerView.setAdapter(searchAdapter);
    }
}

