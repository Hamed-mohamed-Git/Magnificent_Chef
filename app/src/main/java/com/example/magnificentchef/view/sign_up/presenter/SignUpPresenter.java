package com.example.magnificentchef.view.sign_up.presenter;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;

import com.example.magnificentchef.R;
import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.view.common.RegistrationError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpPresenter {
    private static final String TAG = "SignUp Screen";
    private FirebaseAuth auth;
    private Activity activity;
    private SignUpInterface signUpInterface;
    private final FireStoreRepository fireStoreRepository;
    private final UserProfileChangeRequest.Builder profileUpdates;

    public SignUpPresenter (FragmentActivity context, SignUpInterface signUpInterface,
                            FirebaseAuth auth,
                            FireStoreRepository fireStoreRepository,UserProfileChangeRequest.Builder profileUpdates) {
        this.activity = context;
        this.signUpInterface = signUpInterface;
        this.auth = auth;
        this.fireStoreRepository = fireStoreRepository;
        this.profileUpdates = profileUpdates;
    }

    public void signUp(String userName, String password,String firstName,String lastName,String email) {
        if (!userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !lastName.isEmpty() &&!firstName.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    fireStoreRepository.CreateUser(firstName,lastName, userName, email);
                    signUpInterface.onSignSuccess();
                } else {
                    if (task.getException() instanceof FirebaseAuthInvalidUserException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID);
                    else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_INVALID);
                }
            });
        }
    }

    public void updateUserInformation(String name){
        auth.getInstance().getCurrentUser()
                .updateProfile(profileUpdates.setDisplayName(name)
                        .build()).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                       signUpInterface.onUpdateUserInfoSuccess();
                    }
                });
    }

}
