package com.example.avenger.mad2017retry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.database.ICRUDOperationsAsync;
import com.example.avenger.mad2017retry.model.ToDoItem;
import com.example.avenger.mad2017retry.presenter.ToDoDetailPresenter;
import com.example.avenger.mad2017retry.presenter.ToDoListPresenter;
import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.view.ToDoListView;

import java.io.Serializable;

import static android.R.attr.id;

public class ToDoDetailActivity extends AppCompatActivity implements ToDoDetailView {

    private ToDoDetailPresenter presenter;

    //TODO load all ui elements
    private EditText nameText;
    private EditText descriptionText;
    private EditText statusText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        presenter = new ToDoDetailPresenter(this, ((DBApplication)getApplication()));
        //get all ui elements
        nameText = (EditText) findViewById(R.id.nameTextDetail);
        descriptionText = (EditText) findViewById(R.id.descriptionTextDetail);
        statusText = (EditText) findViewById(R.id.statusTextDetail);

        //set data of ui elements


        //TODO we will get the id of the item, so we need to read the item from the map or the database


        long itemId = (long) getIntent().getSerializableExtra("itemId");
        ToDoItem item = presenter.readToDoItem(itemId);
        if (item != null) {
            nameText.setText(item.getName());
            descriptionText.setText(item.getDescription());
            statusText.setText("" + item.getStatus());

        }


    }


    //TODO implement methods to save items etc
    @Override
    public void saveItem() {
        presenter.saveItem();

        Intent returnIntent = new Intent();
        ToDoItem item = new ToDoItem(nameText.getText().toString());
        returnIntent.putExtra("item", item);

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
