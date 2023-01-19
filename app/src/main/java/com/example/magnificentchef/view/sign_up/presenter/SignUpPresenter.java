package com.example.magnificentchef.view.sign_up.presenter;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.magnificentchef.view.common.RegistrationError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class SignUpPresenter {
    private static final String TAG = "SignUp Screen";
    private FirebaseAuth auth;
    private Activity activity;
    private SignUpInterface signUpInterface;

    public SignUpPresenter (FragmentActivity context, SignUpInterface signUpInterface, FirebaseAuth auth) {
        this.activity = context;
        this.signUpInterface = signUpInterface;
        this.auth = auth;
    }

    public void signUp(String userName, String password,String firstName,String LastName,String email) {
        if (!userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !LastName.isEmpty() &&!firstName.isEmpty()) {
            auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    signUpInterface.onSignSuccess();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID);
                    else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_INVALID);
                }
            });
        }
        else if(firstName.isEmpty() && password.isEmpty() && userName.isEmpty() &&LastName.isEmpty() &&email.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_DATA);

        else if(userName.isEmpty() && password.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_USER_PASSWORD_EMAIL);
        else if(userName.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_User_Name);
        else if(password.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_USER_PASSWORD);
        else if(firstName.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_FIRST_NAME);
        else if(LastName.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_Last_NAME);
        else if(email.isEmpty())
            signUpInterface.onSignFailure(RegistrationError.EMPTY_USER_EMAIL);




    }

}
