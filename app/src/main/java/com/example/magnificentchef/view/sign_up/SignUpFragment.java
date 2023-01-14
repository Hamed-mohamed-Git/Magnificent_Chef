package com.example.magnificentchef.view.sign_up;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment implements SignUpInterface {
    private SignUpPresenter signUpPresenter;
    private EditText user_name,first_name,last_name,email,password;
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
        user_name=view.findViewById(R.id.user_nametv);
        first_name=view.findViewById(R.id.first_nametv);
        last_name=view.findViewById(R.id.last_nameTv);
        email=view.findViewById(R.id.email_TV);
        password=view.findViewById(R.id.password_tv);
        signUp=view.findViewById(R.id.signUp_btn);
        signUp.setOnClickListener((view1 -> {
            signUpPresenter = new SignUpPresenter(requireActivity(),this, FirebaseAuth.getInstance());
            signUpPresenter.signUp(email.getText().toString(),
                    password.getText().toString());
        }));


    }

    @Override
    public void onSignSuccess() {


    }

    @Override
    public void onSignFailure() {

    }
}