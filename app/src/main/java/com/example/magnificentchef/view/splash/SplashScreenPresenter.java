package com.example.magnificentchef.view.splash;

import com.example.magnificentchef.view.common.Constants;

public class SplashScreenPresenter {
    private RegisterListener registerListener;

    public SplashScreenPresenter(RegisterListener registerListener) {
        this.registerListener = registerListener;
    }

    public void checkRegistered(String key){
        if (key.equals(Constants.UN_REGISTERED))
            registerListener.onLoggedOut();
        else if(key.equals(Constants.REGISTERED))
            registerListener.onRegistered();
        else{
            registerListener.onBoarding();
        }
    }
}
