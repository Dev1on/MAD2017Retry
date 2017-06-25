package com.example.avenger.mad2017retry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.presenter.ToDoDetailPresenter;
import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.model.Todo;


public class ToDoDetailActivity extends AppCompatActivity implements ToDoDetailView {

    private ToDoDetailPresenter presenter;

    //TODO load all ui elements
    private EditText nameText;
    private EditText descriptionText;
    private CheckBox favouriteBox;
    private CheckBox doneBox;
    private EditText contactText;
    private EditText locationText;

    private ProgressDialog progressDialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_detail);

        presenter = new ToDoDetailPresenter(this, ((DBApplication)getApplication()));
        //get all ui elements
        nameText = (EditText) findViewById(R.id.nameTextDetail);
        descriptionText = (EditText) findViewById(R.id.descriptionTextDetail);
        favouriteBox = (CheckBox) findViewById(R.id.favouriteBox);
        doneBox = (CheckBox) findViewById(R.id.doneBox);
        contactText = (EditText) findViewById(R.id.contactText);
        locationText = (EditText) findViewById(R.id.locationText);

        progressDialog = new ProgressDialog(this);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);

        setSupportActionBar(toolbar);



        initializeScreen();
    }

    private void initializeScreen() {
        progressDialog.show();
        long itemId = (long) getIntent().getSerializableExtra("id");
        presenter.readToDo(itemId);
    }


    @Override
    public void saveItem() {
        presenter.saveItem();

        Intent returnIntent = new Intent();
        Todo item = new Todo(nameText.getText().toString(), descriptionText.getText().toString());
        returnIntent.putExtra("item", item);

        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void deleteItem() {

    }

    @Override
    public void setTodoView(Todo todo) {
        nameText.setText(todo.getName());
        descriptionText.setText(todo.getDescription());
        favouriteBox.setChecked(todo.isFavourite());
        doneBox.setChecked(todo.isDone());
        //contactText.setText(todo.getContacts());
        locationText.setText(todo.getLocation().getName());

        progressDialog.dismiss();
    }

    @Override
    public void createItem() {

    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu_resource, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}
