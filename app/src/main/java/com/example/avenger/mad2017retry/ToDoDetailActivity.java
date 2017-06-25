package com.example.avenger.mad2017retry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.presenter.ToDoDetailPresenter;
import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.model.Todo;


public class ToDoDetailActivity extends AppCompatActivity implements ToDoDetailView {

    private ToDoDetailPresenter presenter;
    private Todo todo;

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
        long itemId = (long) getIntent().getSerializableExtra("id");
        todo = presenter.readToDo(itemId);
        if (todo != null) {
            nameText.setText(todo.getName());
            descriptionText.setText(todo.getDescription());
            statusText.setText("" + todo.isDone());
        }
    }

    @Override
    public void saveItem() {
        presenter.saveItem(this.todo);

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
