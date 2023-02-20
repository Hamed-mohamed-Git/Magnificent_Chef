package com.example.magnificentchef.view.onboarding;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.databinding.FragmentOnBoardingBinding;
import com.example.magnificentchef.view.common.Constants;

public class OnBoardingFragment extends Fragment implements OnBoardingInterface {

    private View view;
    private SharedPreferences.Editor sharedPrefEditor;
    private FragmentOnBoardingBinding fragmentOnBoardingBinding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPrefEditor  =  requireContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentOnBoardingBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_on_boarding,container,false);
        return fragmentOnBoardingBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentOnBoardingBinding.setAdapter(new OnBoardingAdapter(requireContext(),this));
        this.view=view;



    }

    @Override
    public void next(int position) {
       fragmentOnBoardingBinding.slideViewPager.setCurrentItem(position+1);
    }

    @Override
    public void skip() {
        sharedPrefEditor.putString(getString(R.string.preference_file_key),Constants.UN_REGISTERED);

        sharedPrefEditor.apply();
        Navigation
                .findNavController(view)
                .navigate(R.id.action_onBoardingFragment_to_registerFragment);

    }

    @Override
    public void started() {
        sharedPrefEditor.putString(getString(R.string.preference_file_key),Constants.UN_REGISTERED);
        sharedPrefEditor.apply();
        Navigation
                .findNavController(view)
                .navigate(R.id.action_onBoardingFragment_to_registerFragment);

    }
}
