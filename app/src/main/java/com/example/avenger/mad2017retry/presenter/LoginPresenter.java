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
       boolean tmpError = false;

        if (TextUtils.isEmpty(anEmail)) {
            onEmailError();
            tmpError = true;
            return;
        }

        if (TextUtils.isEmpty(aPassword)) {
            onPasswordError();
            tmpError = true;
            return;
        }

        // TODO check User

        if (!tmpError) {
            onSuccess();
        }
    }

    public void onDestroy() {
        loginView = null;
    }

    public void onEmailError() {
        if(loginView != null) {
            loginView.setEmailError();
            loginView.hideProgress();
        }
    }

    public void onPasswordError() {
        if(loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    public void onSuccess() {
        if(loginView != null) {
            loginView.navigateToHome();
        }
    }
}
