package com.example.magnificentchef.view.sign_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.common.RegistrationError;
import com.example.magnificentchef.view.login.presenter.LoginPresenter;
import com.example.magnificentchef.view.sign_up.presenter.SignUpInterface;
import com.example.magnificentchef.view.sign_up.presenter.SignUpPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements SignUpInterface {
    private SignUpPresenter signUpPresenter;
    private TextInputEditText user_name, first_name, last_name, email, password;
    private TextInputLayout user_name_layout, first_name_layout, last_name_layout, email_layout, password_layout;
    private Button signUp;
    private TextView SIgnUpError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user_name = view.findViewById(R.id.userNameTextInputEditText);
        first_name = view.findViewById(R.id.firstNameTextInputEditText);
        last_name = view.findViewById(R.id.lastNameTextInputEditText);
        email = view.findViewById(R.id.emailTextInputEditText);
        password = view.findViewById(R.id.passwordTextInputEditText);

        //layout
        user_name_layout = view.findViewById(R.id.user_nametv);
        first_name_layout = view.findViewById(R.id.first_nametv);
        last_name_layout = view.findViewById(R.id.editTextTextPersonName3);
        email_layout = view.findViewById(R.id.user_nametv2);
        password_layout = view.findViewById(R.id.user_nametv3);
        SIgnUpError = view.findViewById(R.id.signUpErrorTextView);
        signUp = view.findViewById(R.id.signUp_Button);

        signUp.setOnClickListener(( view1 -> {

            email_layout.setErrorEnabled(false);
            user_name_layout.setErrorEnabled(false);
            password_layout.setErrorEnabled(false);
            first_name_layout.setErrorEnabled(false);
            last_name_layout.setErrorEnabled(false);
            email_layout.setErrorEnabled(false);
            SIgnUpError.setVisibility(View.INVISIBLE);
            signUpPresenter = new SignUpPresenter(requireActivity(), this, FirebaseAuth.getInstance());
            signUpPresenter.signUp(user_name.getText().toString(),
                    first_name.getText().toString(),
                    last_name.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString());
        } ));


    }

    @Override
    public void onSignSuccess() {
        NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_baseFragment);

    }

    @Override
    public void onSignFailure(int error) {
        switch (error) {
            case RegistrationError.EMPTY_USER_EMAIL:
                email_layout.setErrorEnabled(true);
                email.setError("please enter your email");
                break;
            case RegistrationError.EMPTY_USER_PASSWORD:
                password_layout.setErrorEnabled(true);
                password.setError("please enter your password");
                break;
            case RegistrationError.EMPTY_FIRST_NAME:
                first_name_layout.setErrorEnabled(true);
                first_name_layout.setError("please enter your first_name");
                break;
            case RegistrationError.EMPTY_Last_NAME:
                last_name_layout.setErrorEnabled(true);
                last_name_layout.setError("please enter your last_name");
                break;

            case RegistrationError.EMPTY_User_Name:
                user_name_layout.setErrorEnabled(true);
                user_name_layout.setError("please enter your user_name");
                break;

            case RegistrationError.USER_EMAIL_PASSWORD_INVALID:
                SIgnUpError.setVisibility(View.VISIBLE);
                SIgnUpError.setText(R.string.invalid_email_and_password_message);
                break;
            case RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID:
                SIgnUpError.setVisibility(View.VISIBLE);
                SIgnUpError.setText(R.string.email_password_incorrect);
                break;

            default:
                password_layout.setErrorEnabled(true);
                password_layout.setError("please enter your password");
                email_layout.setErrorEnabled(true);
                email_layout.setError("please enter your Email");
                first_name_layout.setErrorEnabled(true);
                first_name_layout.setError("please enter your First Name");
                last_name_layout.setErrorEnabled(true);
                last_name_layout.setError("please enter your Last Name");
                user_name_layout.setErrorEnabled(true);
                user_name_layout.setError("please enter your User Name");
        }
    }
}