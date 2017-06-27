package com.example.avenger.mad2017retry;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.avenger.mad2017retry.helper.DividerItemDecoration;
import com.example.avenger.mad2017retry.model.Todo;
import com.example.avenger.mad2017retry.presenter.ToDoListPresenter;
import com.example.avenger.mad2017retry.adapter.ToDoListAdapter;
import com.example.avenger.mad2017retry.view.ToDoListView;

public class ToDoListActivity extends AppCompatActivity implements ToDoListView {

    private Todo[] todos;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent showTodoDetails = new Intent(context, ToDoDetailActivity.class);
                showTodoDetails.putExtra("createItem", true);
                context.startActivity(showTodoDetails);
            }
        });

        Todo item1 = new Todo("lorem", "dolor") ;
        item1.setDone(true);
        item1.setFavourite(true);
        item1.setExpiry(5);

        Todo item2 = new Todo("lorem", "dolor") ;
        item2.setDone(false);
        item2.setFavourite(false);
        item2.setExpiry(4);

        Todo[] testTodos =   {item1, item2};

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        adapter = new ToDoListAdapter(this, testTodos);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
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