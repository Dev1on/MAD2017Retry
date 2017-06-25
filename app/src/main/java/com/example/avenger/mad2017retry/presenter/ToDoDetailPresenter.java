package com.example.avenger.mad2017retry.presenter;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.database.ICRUDOperationsAsync;
import com.example.avenger.mad2017retry.model.Todo;
import com.example.avenger.mad2017retry.view.ToDoDetailView;

import static android.R.attr.id;

public class ToDoDetailPresenter {

    private ToDoDetailView toDoDetailView;

    private ICRUDOperationsAsync crudOperations;

    private Todo todo;

    public ToDoDetailPresenter(ToDoDetailView aToDoDetailView, DBApplication application) {
        this.toDoDetailView = aToDoDetailView;
        crudOperations = application.getCrudOperations();
    }

    public void saveItem() {
        crudOperations.updateToDo(id, this.todo, new ICRUDOperationsAsync.CallbackFunction<Todo>() {
            @Override
            public void process(Todo result) {

            }
        });
    }

    public void readToDo(long id) {
        //First of all let application check if there is a item with the given id.
        //if yes read the item out of the map, if not then use the crudOperations to read from db

        crudOperations.readToDo(id, result -> {
            setTodo(result);
            toDoDetailView.setTodoView(result);
        });
    }

    public void onDestroy() {
        toDoDetailView = null;
    }

    private void setTodo(Todo item) {
        this.todo = item;
    }
}
