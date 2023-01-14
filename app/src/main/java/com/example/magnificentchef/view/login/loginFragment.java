package com.example.magnificentchef.view.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.presenter.login.LoginPresenter;
import com.example.magnificentchef.presenter.login.LoginPresenterInterface;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;


public class loginFragment extends Fragment implements LoginPresenterInterface {
    private LoginPresenter loginPresenter;
    private Button login;
    private EditText emailEditText, passwordEditText;
    private TextView incorrectTexView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login = view.findViewById(R.id.loginButton);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        incorrectTexView = view.findViewById(R.id.incorrectMessage);

        login.setOnClickListener((view1 -> {
            loginPresenter = new LoginPresenter(requireActivity(),this,FirebaseAuth.getInstance());
            loginPresenter.logIn(emailEditText.getText().toString(),
                    passwordEditText.getText().toString());
        }));

    }

    @Override
    public void onLoginSuccess() {
        incorrectTexView.setVisibility(View.INVISIBLE);
        //Navigation.findNavController(getView()).navigate(0);
    }

    @Override
    public void onLoginFailure() {
        incorrectTexView.setVisibility(View.VISIBLE);
    }
}