package com.example.avenger.mad2017retry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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

        boolean createItem = (boolean) getIntent().getSerializableExtra("createItem");

        if (createItem)
            initializeEmpty();
        else
            initializeScreenWithTodo();
    }

    private void initializeEmpty() {
        Todo todo = new Todo("","");
        setTodoView(todo);
    }

    private void initializeScreenWithTodo() {
        progressDialog.show();
        long itemId = (long) getIntent().getSerializableExtra("id");

        presenter.readToDo(itemId);
        progressDialog.hide();
    }


    @Override
    public void saveItem() {
        boolean createItem = (boolean) getIntent().getSerializableExtra("createItem");

        if (!createItem) {
            progressDialog.show();
            presenter.saveItem();
        } else {
            presenter.createItem();
        }

        progressDialog.dismiss();
        Toast.makeText(this, "Todo saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteItem() {
        presenter.deleteTodo((long) getIntent().getSerializableExtra("id"));

        Toast.makeText(this, "Todo deleted", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
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
            deleteItem();
            return true;
        } else if(item.getItemId() == R.id.action_save) {
            saveItem();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public Todo getCurrentTodo() {
        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        boolean favourite = favouriteBox.isChecked();
        boolean done = doneBox.isChecked();
        String location_name = locationText.getText().toString();

        //TODO location
        Todo.Location location = new Todo.Location();
        Todo.LatLng latlng = new Todo.LatLng();
        latlng.setLat(12);
        latlng.setLng(13);
        location.setLatlng(latlng);
        location.setName(location_name);
        //TODO save contacts

        Todo returnTodo = new Todo(name, description);
        returnTodo.setFavourite(favourite);
        returnTodo.setDone(done);
        returnTodo.setLocation(location);

        return returnTodo;
    }

    public void displayTodoNotFound() {
        Toast.makeText(this, "Sorry, Todo not found", Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
