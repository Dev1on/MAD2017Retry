package com.example.avenger.mad2017retry;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.avenger.mad2017retry.presenter.LoginPresenter;
import com.example.avenger.mad2017retry.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private static Application app;

    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private LoginPresenter presenter;

    // TODO disable login Button if both fields empty

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = getApplication();
        presenter = new LoginPresenter(this, app );

        if(presenter.isOnline()) {
            progressBar = (ProgressBar) findViewById(R.id.progress);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    presenter.validateCredentials(email.getText().toString(), password.getText().toString());
                }
            });

            //Set variable for CRUD Operations online
        } else {
            navigateToHome();
            //set variable for CRUD Operations offline
        }



    }

    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setEmailError() {
        email.setError(getString(R.string.email_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, ToDoListActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}