package com.example.avenger.mad2017retry.presenter;

import android.text.TextUtils;

import com.example.avenger.mad2017retry.view.LoginView;

/**
 * Created by Avenger on 21.06.17.
 */

public class LoginPresenter {

    private LoginView loginView;

    public LoginPresenter(LoginView aLoginView) {
        this.loginView = aLoginView;
    }

    public void validateCredentials(String anEmail, String aPassword) {
        if(loginView != null) {
            loginView.showProgress();
        }

        login(anEmail, aPassword);
    }

    private void login(String anEmail, String aPassword) {
        if (TextUtils.isEmpty(anEmail)) {
            onEmailError();
            return;
        }

        if (TextUtils.isEmpty(aPassword) || aPassword.length() != 6) {
            onPasswordError();
            return;
        }

        onSuccess();
    }

    public void onDestroy() {
        loginView = null;
    }

    private void onEmailError() {
        if(loginView != null) {
            loginView.setEmailError();
            loginView.hideProgress();
        }
    }

    private void onPasswordError() {
        if(loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    private void onSuccess() {
        if(loginView != null) {
            loginView.navigateToHome();
        }
    }
}
