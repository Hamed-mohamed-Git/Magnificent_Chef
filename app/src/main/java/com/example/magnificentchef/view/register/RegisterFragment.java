package com.example.magnificentchef.view.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.login.presenter.OnAuthLoginComplete;
import com.example.magnificentchef.view.register.presenter.RegisterPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class RegisterFragment extends Fragment implements OnAuthLoginComplete {
    Button btn_google;
    private Button signUpButton;
    private TextView login;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private RegisterPresenter registerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activityResultLauncher = registerForActivityResult(new ActivityResultContract<Intent, Task<GoogleSignInAccount>>() {
            @Override
            public Task<GoogleSignInAccount> parseResult(int i, @Nullable Intent intent) {
                //GoogleSignIn.getSignedInAccountFromIntent(intent).getException().printStackTrace();
                if (i != Activity.RESULT_OK) return null;
                return GoogleSignIn.getSignedInAccountFromIntent(intent);
            }

            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
                return intent;
            }
        }, result -> {
            if (result == null) return;
             registerPresenter =new RegisterPresenter(FirebaseAuth.getInstance(),this);
             registerPresenter.googleLogin(result.getResult().getIdToken());
            Toast.makeText(requireContext(),"true",Toast.LENGTH_LONG).show();
        });
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
        btn_google = view.findViewById(R.id.btn_google);
        signUpButton = view.findViewById(R.id.signUpButton);
        login = view.findViewById(R.id.textView4);
        login.setOnClickListener((view1) -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
        });



        btn_google.setOnClickListener(view1 -> {
            activityResultLauncher.launch(GoogleSignIn
                    .getClient(requireContext(),
                            new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                    .requestIdToken(getString(R.string.default_web_client_id))
                                    .requestEmail()
                                    .build())
                    .getSignInIntent());
        });

        signUpButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_signUpFragment);
        });
    }

    @Override
    public void onLoginSuccess() {
        Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_baseFragment);
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(requireContext(),"fail",Toast.LENGTH_LONG).show();
    }

}