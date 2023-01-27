package com.example.magnificentchef.view.login;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.view.common.RegistrationError;
import com.example.magnificentchef.view.login.presenter.LoginPresenter;
import com.example.magnificentchef.view.login.presenter.LoginPresenterInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class loginFragment extends Fragment implements LoginPresenterInterface {
    private LoginPresenter loginPresenter;
    private Button login;
    private TextInputEditText emailEditText, passwordEditText;
    private TextInputLayout email,password;
    private TextView incorrectTexView;
    private ImageButton backImageButton;
    private SharedPreferences.Editor sharedPrefEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefEditor  =  requireContext()
                .getSharedPreferences(
                getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE).edit();
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
        emailEditText = view.findViewById(R.id.emailLoginTextInputEditText);
        passwordEditText = view.findViewById(R.id.passwordLogInTextInputEditText);
        email = view.findViewById(R.id.user_nametv2);
        password = view.findViewById(R.id.user_nametv3);
        incorrectTexView = view.findViewById(R.id.registerErrorTextView);
        backImageButton = view.findViewById(R.id.back_button);

        login.setOnClickListener((view1 -> {
            email.setErrorEnabled(false);
            password.setErrorEnabled(false);
            incorrectTexView.setVisibility(View.INVISIBLE);
            loginPresenter = new LoginPresenter(requireActivity(),this,FirebaseAuth.getInstance());
            loginPresenter.logIn(emailEditText.getText().toString(),
                    passwordEditText.getText().toString());
        }));

        backImageButton.setOnClickListener((view1) ->{
            Navigation.findNavController(getView()).popBackStack();
        });

    }

    @Override
    public void onLoginSuccess() {
        sharedPrefEditor.putString(Constants.SHARED_PREFERENCES,Constants.REGISTERED);
        sharedPrefEditor.apply();
        incorrectTexView.setVisibility(View.INVISIBLE);
        Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_baseFragment);
    }

    @Override
    public void onLoginFailure(int errorCode) {
        switch (errorCode){
            case RegistrationError.EMPTY_USER_EMAIL :
                email.setErrorEnabled(true);
                email.setError("please enter your email");
                break;
            case RegistrationError.EMPTY_USER_PASSWORD:
                password.setErrorEnabled(true);
                password.setError("please enter your password");
                break;
            case RegistrationError.USER_EMAIL_PASSWORD_INVALID:
                incorrectTexView.setVisibility(View.VISIBLE);
                incorrectTexView.setText(R.string.invalid_email_and_password_message);
                break;
            case RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID:
                incorrectTexView.setVisibility(View.VISIBLE);
                incorrectTexView.setText(R.string.email_password_incorrect);
                break;
            default:
                password.setErrorEnabled(true);
                password.setError("please enter your password");
                email.setErrorEnabled(true);
                email.setError("please enter your email");
        }
    }
}