package com.example.avenger.mad2017retry;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.avenger.mad2017retry.presenter.ToDoDetailPresenter;
import com.example.avenger.mad2017retry.presenter.ToDoListPresenter;
import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.view.ToDoListView;

public class ToDoDetailActivity extends AppCompatActivity implements ToDoDetailView {

    private ToDoDetailPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        presenter = new ToDoDetailPresenter(this);


    }


    @Override
    public void saveItem() {

    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void readItem() {

    }

    @Override
    public void createItem() {

    }



    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
