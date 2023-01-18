package com.example.magnificentchef.view.login.presenter;

public interface OnAuthLoginComplete {
    void onLoginSuccess();
    void onLoginFailure(int error);
}
