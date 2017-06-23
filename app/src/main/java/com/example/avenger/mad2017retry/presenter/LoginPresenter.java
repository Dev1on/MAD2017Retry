package com.example.avenger.mad2017retry.presenter;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import com.example.avenger.mad2017retry.view.LoginView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Avenger on 21.06.17.
 */

public class LoginPresenter {
    private static String WEB_APPLICATION_URL = "http://127.0.0.1:8080/todos";

    private LoginView loginView;
    private Object systemService;
    private URL databaseURL = null;

    public LoginPresenter(LoginView aLoginView, Object aSystemService) {
        this.loginView = aLoginView;
        this.systemService = aSystemService;
    }

    public void validateCredentials(String anEmail, String aPassword) {
        if(loginView != null) {
            loginView.showProgress();
        }

        // TODO add Email validation

        login(anEmail, aPassword);
    }

    public boolean isInternetConnectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isWebApplicationAvailable() {
        createWebApplicationURL();

        HttpURLConnection connection;
        String response;

        try {
            connection = (HttpURLConnection) databaseURL.openConnection();
            connection.setReadTimeout(100000);
            connection.setConnectTimeout(150000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            response = connection.getInputStream().toString();
        } catch (IOException e) {
            Log.d("LoginPresenter", "WebApplication unavailable.");
            e.printStackTrace();
            return false;
        }
        if (("").equals(response)) {
            Log.d("LoginPresenter","WebApplication available, but no response.");
            return false;
        }

        return true;
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

        // TODO send to server and validate success
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

    private void createWebApplicationURL() {
        try {
            databaseURL = new URL(WEB_APPLICATION_URL);
        } catch (MalformedURLException e) {
            Log.d("LoginPresenter","Invalid URL.");
            e.printStackTrace();
        }
        Log.d("LoginPresenter","WebApplication URL: " + databaseURL.toString());
    }
}
