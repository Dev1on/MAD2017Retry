package com.example.avenger.mad2017retry.view;

/**
 * Created by Avenger on 21.06.17.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setEmailError();

    void setPasswordError();

    void navigateToHome();
}
