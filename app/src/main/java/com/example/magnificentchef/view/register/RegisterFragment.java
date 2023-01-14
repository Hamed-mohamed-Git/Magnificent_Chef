package com.example.magnificentchef.view.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.magnificentchef.R;
import com.example.magnificentchef.presenter.registration.RegisterInterface;
import com.example.magnificentchef.presenter.registration.RegisterPresenter;


public class RegisterFragment extends Fragment implements RegisterInterface {
    Button btn_google;
    View view;
    private Button signUpButton;
    private RegisterPresenter registerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_google= view.findViewById(R.id.btn_google);
        signUpButton = view.findViewById(R.id.signUpButton);

        btn_google.setOnClickListener(view1 -> {
            registerPresenter=new RegisterPresenter(getContext(),requireActivity());
            registerPresenter.Join();
        });
        signUpButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_signUpFragment);
        });
    }

    @Override
    public void onClick() {
    //ToDo navigate action put here (modify it)
        //TODO what well send

        Navigation
                .findNavController(view)
                .navigate(R.id.action_splashScreenFragment_to_registerFragment);
    }
}