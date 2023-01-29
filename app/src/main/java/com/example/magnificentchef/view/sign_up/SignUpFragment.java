package com.example.magnificentchef.view.sign_up;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.view.common.RegistrationError;
import com.example.magnificentchef.view.sign_up.presenter.SignUpInterface;
import com.example.magnificentchef.view.sign_up.presenter.SignUpPresenter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpFragment extends Fragment implements SignUpInterface, TextWatcher {
    private SignUpPresenter signUpPresenter;
    private TextInputEditText user_name, first_name, last_name, email, password;
    private TextInputLayout user_name_layout, first_name_layout, last_name_layout, email_layout, password_layout;
    private Button signUp;
    private TextView SIgnUpError;
    private SharedPreferences.Editor sharedPrefEditor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefEditor  =  requireContext()
                .getSharedPreferences(
                        getString(R.string.preference_file_key),
                        Context.MODE_PRIVATE).edit();
        signUpPresenter = new SignUpPresenter(requireActivity(), this, FirebaseAuth.getInstance(),
                new FireStoreRepository(FirebaseFirestore.getInstance(),
                        FirebaseAuth.getInstance()),new UserProfileChangeRequest.Builder());
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

        user_name.addTextChangedListener(this);
        first_name.addTextChangedListener(this);
        last_name.addTextChangedListener(this);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);

        //layout
        user_name_layout = view.findViewById(R.id.user_nametv);
        first_name_layout = view.findViewById(R.id.first_nametv);
        last_name_layout = view.findViewById(R.id.editTextTextPersonName3);
        email_layout = view.findViewById(R.id.user_nametv2);
        password_layout = view.findViewById(R.id.user_nametv3);
        SIgnUpError = view.findViewById(R.id.signUpErrorTextView);
        signUp = view.findViewById(R.id.signUp_Button);

        signUp.setOnClickListener(( view1 -> {

            SIgnUpError.setVisibility(View.INVISIBLE);

            signUpPresenter.signUp(user_name.getText().toString(),
                    password.getText().toString(),
                    first_name.getText().toString(),
                    last_name.getText().toString(),
                    email.getText().toString()
                    );
        } ));
    }

    @Override
    public void onSignSuccess() {
        signUpPresenter.updateUserInformation(user_name.getText().toString());
    }

    @Override
    public void onSignFailure(int error) {
      switch (error) {
          case RegistrationError.USER_EMAIL_PASSWORD_INVALID:
              SIgnUpError.setVisibility(View.VISIBLE);
              SIgnUpError.setText(R.string.invalid_email_and_password_message);
              break;
          case RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID:
              SIgnUpError.setVisibility(View.VISIBLE);
              SIgnUpError.setText(R.string.email_password_incorrect);
              break;

          case RegistrationError.DUBLICATION_EMAIL:
               SIgnUpError.setVisibility(View.VISIBLE);
              SIgnUpError.setText(R.string.dublication);
              break;
      }
    }

    @Override
    public void onEditTextTInputDataValide() {
        signUp.setEnabled(true);
    }

    @Override
    public void onUpdateUserInfoSuccess() {
        sharedPrefEditor.putString(getString(R.string.preference_file_key)
                , Constants.REGISTERED);
        sharedPrefEditor.apply();
        NavHostFragment
                .findNavController(requireParentFragment())
                .navigate(SignUpFragmentDirections
                        .actionSignUpFragmentToBaseFragment("none").setKey("none"));
    }

    @Override
    public void onEditTextTInputDataNotValide() {
        signUp.setEnabled(false);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            signUpPresenter.checked(user_name.getText().toString(),
                    password.getText().toString(),
                    first_name.getText().toString(),
                    last_name.getText().toString(),
                    email.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }


}

