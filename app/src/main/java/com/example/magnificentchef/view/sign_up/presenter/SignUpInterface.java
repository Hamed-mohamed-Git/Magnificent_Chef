package com.example.magnificentchef.view.sign_up.presenter;

public interface SignUpInterface {
    void onSignSuccess();
    void onSignFailure(int error);
    void onEditTextTInputDataValide();
    void onEditTextTInputDataNotValide();
    void onUpdateUserInfoSuccess();
}
