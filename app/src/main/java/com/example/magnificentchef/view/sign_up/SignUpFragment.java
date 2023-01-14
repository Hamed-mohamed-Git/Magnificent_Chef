package com.example.magnificentchef.view.sign_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.magnificentchef.R;
import com.example.magnificentchef.presenter.SignUp.SignUpInterface;
import com.example.magnificentchef.presenter.SignUp.SignUpPresenter;
import com.example.magnificentchef.presenter.login.LoginPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements SignUpInterface {
    private SignUpPresenter signUpPresenter;
    private TextInputEditText user_name,first_name,last_name,email,password;
    private Button signUp;
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
        user_name=view.findViewById(R.id.userNameTextInputEditText);
        first_name=view.findViewById(R.id.firstNameTextInputEditText);
        last_name=view.findViewById(R.id.lastNameTextInputEditText);
        email=view.findViewById(R.id.emailTextInputEditText);
        password=view.findViewById(R.id.passwordTextInputEditText);
        signUp=view.findViewById(R.id.signUp_Button);

        signUp.setOnClickListener((view1 -> {
            if(email.getText()==null){
                email.setError("Email cann't be empty ");
            }
            if(password.getText()==null){
                email.setError("Password cann't be empty ");
            }
            if(user_name.getText()==null){
                email.setError("Username cann't be empty ");
            }
            if(first_name.getText()==null){
                email.setError("Username cann't be empty ");
            }
            if(last_name.getText()==null){
                email.setError("Username cann't be empty ");
            }
            signUpPresenter = new SignUpPresenter(requireActivity(),this, FirebaseAuth.getInstance());
            signUpPresenter.signUp(email.getText().toString(),
                    password.getText().toString());
        }));



    }

    @Override
    public void onSignSuccess() {
        Navigation.findNavController(getView()).navigate(R.id.action_signUpFragment_to_baseFragment);

    }

    @Override
    public void onSignFailure() {
        Toast.makeText(requireContext(),"SignUp Fail",Toast.LENGTH_SHORT).show();

    }
}