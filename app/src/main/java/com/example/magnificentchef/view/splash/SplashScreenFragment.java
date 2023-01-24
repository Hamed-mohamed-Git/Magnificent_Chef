package com.example.magnificentchef.view.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.magnificentchef.R;
import com.google.firebase.auth.FirebaseAuth;


public class SplashScreenFragment extends Fragment implements RegisterListener {

    private View decorView;
    private int uiOptions;
    private LottieAnimationView lottieAnimationView;
    private SharedPreferences sharedPref;
    private View requireView;
    private SplashScreenPresenter splashScreenPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        decorView = requireActivity().getWindow().getDecorView();
        sharedPref  =  requireContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        splashScreenPresenter = new SplashScreenPresenter(this);
        hideStatusBar();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lottieAnimationView = view.findViewById(R.id.animationView);
        requireView = view;
        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                splashScreenPresenter.checkRegistered(sharedPref.getString("registered","false"));
            }
        });

    }

    @Override
    public void onStop() {
        showStatusBar();
        super.onStop();
    }

    private void hideStatusBar(){
        // Hide the status bar.
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        //((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    private void showStatusBar(){
        // show the status bar.
        uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }


    @Override
    public void onRegistered() {
        Navigation
                .findNavController(requireView)
                .navigate(R.id.action_splashScreenFragment_to_baseFragment);
    }

    @Override
    public void onLoggedOut() {
        Navigation
                .findNavController(requireView)
                .navigate(R.id.action_splashScreenFragment_to_registerFragment);
    }
}