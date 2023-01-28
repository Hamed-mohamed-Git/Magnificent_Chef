package com.example.magnificentchef.view.sign_up.presenter;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.magnificentchef.model.remote.firebase.FireStoreRepository;
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.view.common.RegistrationError;
import com.example.magnificentchef.view.sign_up.SignUpFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.regex.Pattern;

public class SignUpPresenter {
    private static final String TAG = "SignUp Screen";
    private FirebaseAuth auth;
    private Activity activity;
    private SignUpInterface signUpInterface;
    private SignUpFragment signUpFragment;
    private String status;
    private Pattern regex;

    private final FireStoreRepository fireStoreRepository;
    private final UserProfileChangeRequest.Builder profileUpdates;

    public SignUpPresenter (FragmentActivity context, SignUpInterface signUpInterface,
                            FirebaseAuth auth,
                            FireStoreRepository fireStoreRepository,UserProfileChangeRequest.Builder profileUpdates) {
        this.activity = context;
        this.signUpInterface = signUpInterface;
        this.auth = auth;
        this.fireStoreRepository = fireStoreRepository;
       regex = Pattern.compile(Constants.EMAIL_PATTERN);
        this.profileUpdates = profileUpdates;
    }

    public void signUp(String userName, String password,String firstName,String lastName,String email) {
        if (!userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !lastName.isEmpty() &&!firstName.isEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    fireStoreRepository.CreateUser(firstName,lastName, userName, email);
                    signUpInterface.onSignSuccess();
                } else {
                    Log.i("aya", "signUp: "+task.getException().getMessage());
                    System.out.println("aya"+task.getException());

                    if (task.getException() instanceof FirebaseAuthInvalidUserException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_DATA_INVALID);
                    else if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                        signUpInterface.onSignFailure(RegistrationError.USER_EMAIL_PASSWORD_INVALID);

                    else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        signUpInterface.onSignFailure(RegistrationError.DUBLICATION_EMAIL);
                    }
                }
            });
        }



    public void Checked(String userName,String password,String firstName,String lastName,String email){
        if(!userName.isEmpty() && !password.isEmpty() && !email.isEmpty() && !lastName.isEmpty() && !firstName.isEmpty() && password.length()>=6 && regex.matcher(email).matches()){
                signUpInterface.onEditTextTInputDataValide();

        }
        else {
            signUpInterface.onEditTextTInputDataNotValide();
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
