package com.example.avenger.mad2017retry.presenter;

import android.util.Log;

import com.example.avenger.mad2017retry.database.ICRUDOperationsAsync;
import com.example.avenger.mad2017retry.view.ToDoListView;

/**
 * Created by Avenger on 21.06.17.
 */

public class ToDoListPresenter {

    private ToDoListView toDoListView;

    private ICRUDOperationsAsync crudOperations;

    public ToDoListPresenter(ToDoListView aToDoListView) {
        this.toDoListView = aToDoListView;
    }

    public void onDestroy() {
        toDoListView = null;
    }
/*
    public void readToDos() {
        //First of all let application check if there is a item with the given id.
        //if yes read the item out of the map, if not then use the crudOperations to read from db

        crudOperations.readAllToDos(result -> {
            Log.i("ReadAllTodos", "Result is: " + result);

            if(result.isEmpty()) {
                toDoListView.displayTodosNotFound();
            } else {
                toDoListView.setToDoListView();
            }

            if(result.getId() == 0) {
                toDoDetailView.displayTodoNotFound();
            } else {
                setTodo(result);
                toDoDetailView.setTodoView(result);
            }
        });
    }*/
}
