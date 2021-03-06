package com.example.avenger.mad2017retry;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.presenter.LoginPresenter;
import com.example.avenger.mad2017retry.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private LoginPresenter presenter;

    // TODO disable login Button if both fields empty

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Object systemService = getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        presenter = new LoginPresenter(this, systemService);

        findViewById(R.id.login_button).setEnabled(false);

        //TODO change the policy change to async task
        //set policy to all because of thread problem
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // check Internet connection and WebApplication availability
        if (presenter.isInternetConnectionAvailable() && presenter.isWebApplicationAvailable()) {
            progressBar = (ProgressBar) findViewById(R.id.progress);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);
            findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    presenter.validateCredentials(email.getText().toString(), password.getText().toString());
                }
            });
            ((DBApplication)getApplication()).setCRUDOperations("remote");
        } else {
            // TODO show Toast WebApp unavailable
            ((DBApplication)getApplication()).setCRUDOperations("local");
            // skip login
            navigateToHome();
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