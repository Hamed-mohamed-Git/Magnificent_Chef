package com.example.magnificentchef.view.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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


public class SplashScreenFragment extends Fragment {

    private View decorView;
    private int uiOptions;
    private LottieAnimationView lottieAnimationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        decorView = requireActivity().getWindow().getDecorView();
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
        lottieAnimationView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_splashScreenFragment_to_registerFragment);
                super.onAnimationEnd(animation);
            }
        });
    }

    @Override
    public void onDestroy() {
        showStatusBar();
        super.onDestroy();
    }

    private void hideStatusBar(){
        // Hide the status bar.
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void showStatusBar(){
        // show the status bar.
        uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }


}