package com.example.magnificentchef.view.splash;

public class SplashScreenPresenter {
    private RegisterListener registerListener;

    public SplashScreenPresenter(RegisterListener registerListener) {
        this.registerListener = registerListener;
    }

    public void checkRegistered(String key){
        if (key.equals("false"))
            registerListener.onLoggedOut();
        else
            registerListener.onRegistered();
    }
}
