package com.example.magnificentchef.view.splash;

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

import com.example.magnificentchef.R;


public class SplashScreenFragment extends Fragment {

    private View decorView;
    private int uiOptions;

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
        new Handler(Looper.getMainLooper()).postDelayed(() ->
                Navigation
                        .findNavController(view)
                        .navigate(R.id.action_splashScreenFragment_to_registerFragment),
                4500);
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
    }

    private void showStatusBar(){
        // show the status bar.
        uiOptions = View.VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);
    }


}