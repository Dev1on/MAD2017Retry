package com.example.avenger.mad2017retry.presenter;

import android.accessibilityservice.AccessibilityService;
import android.app.Application;
import android.content.Context;
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
    private static String DB_URL = "http://127.0.0.1:8080/todos";

    private LoginView loginView;
    private Application app;
    private URL databaseURL = null;

    public LoginPresenter(LoginView aLoginView, Application app) {
        this.loginView = aLoginView;
        this.app = app;
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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) app.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isDbOnline() {
        createDatabaseURL();

        HttpURLConnection con;
        String response;

        try {
            con = (HttpURLConnection) databaseURL.openConnection();
            con.setReadTimeout(100000);
            con.setConnectTimeout(150000);
            con.setRequestMethod("GET");
            con.setDoInput(true);

            con.connect();

            response = con.getInputStream().toString();
        } catch (IOException e) {
            Log.i("LoginPresenter", "Database is not reachable.");
            e.printStackTrace();
            return false;
        }
        if (("").equals(response)) {
            Log.i("LoginPresenter","Database is reachable, but did not respond.");
            return false;
        }
        return true;
    }

    private void createDatabaseURL() {
        try {
            databaseURL = new URL(DB_URL);
        } catch (MalformedURLException e) {
            Log.i("LoginPresenter","The URL could not be created. Probably it's not a URL.");
            e.printStackTrace();
        }
        Log.i("LoginPresenter","URL is to database is: " + databaseURL.toString());
    }

}
