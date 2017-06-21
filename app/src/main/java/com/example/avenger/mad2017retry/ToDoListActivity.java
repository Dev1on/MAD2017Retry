package com.example.avenger.mad2017retry;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.avenger.mad2017retry.presenter.ToDoListPresenter;
import com.example.avenger.mad2017retry.view.ToDoListView;

public class ToDoListActivity extends AppCompatActivity implements ToDoListView {

    private ToDoListPresenter presenter;

    private ViewGroup todoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        presenter = new ToDoListPresenter(this);

        todoListView = (ViewGroup) findViewById(R.id.toDoList);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void showDetails() {

    }

    @Override
    public void createItem() {

    }

    @Override
    public void toggleStatus() {

    }

    @Override
    public void sortBySolved() {

    }

    @Override
    public void sortByOpen() {

    }

    @Override
    public void sortByImportanceAndDate() {

    }

    @Override
    public void sortByDateAndImportance() {

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
