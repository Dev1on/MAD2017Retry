package com.example.avenger.mad2017retry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.avenger.mad2017retry.presenter.ToDoListPresenter;
import com.example.avenger.mad2017retry.view.ToDoListAdapter;
import com.example.avenger.mad2017retry.view.ToDoListView;

public class ToDoListActivity extends AppCompatActivity implements ToDoListView {

    private ToDoListPresenter presenter;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        String[] myStrings = {"test", "test2", "bla","test", "test2", "bla","test", "test2", "bla"};
        adapter = new ToDoListAdapter(myStrings);
        recyclerView.setAdapter(adapter);

        presenter = new ToDoListPresenter(this);
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