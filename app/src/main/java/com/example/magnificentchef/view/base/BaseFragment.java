package com.example.magnificentchef.view.base;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.magnificentchef.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;


public class BaseFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    NavController navcontroller;
    NavigationView navigationView;
    AppBarConfiguration appBarConfiguration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, view, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView=view.findViewById(R.id.bottomNavigationView);
        navcontroller= Navigation.findNavController(getActivity(),R.id.fragment);
        NavigationUI.setupWithNavController(bottomNavigationView,navcontroller);

        appBarConfiguration=new AppBarConfiguration.Builder(navcontroller.getGraph()).build();
        
    }
}