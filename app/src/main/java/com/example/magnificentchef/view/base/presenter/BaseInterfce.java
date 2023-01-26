package com.example.magnificentchef.view.base.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public interface BaseInterfce {
    void onLoggedUser();
    void onGuestUser();

}
