package com.example.avenger.mad2017retry.presenter;

import com.example.avenger.mad2017retry.database.DBApplication;
import com.example.avenger.mad2017retry.database.ICRUDOperationsAsync;
import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.model.Todo;

import static android.R.attr.id;

public class ToDoDetailPresenter {

    // TODO use
    private ToDoDetailView toDoDetailView;

    private ICRUDOperationsAsync crudOperations;

    public ToDoDetailPresenter(ToDoDetailView aToDoDetailView, DBApplication application) {
        this.toDoDetailView = aToDoDetailView;

        //getting the application scoped DBApplication
        crudOperations = application.getCrudOperations();
    }

    public void saveItem(Todo item) {
        crudOperations.updateToDo(id, item, new ICRUDOperationsAsync.CallbackFunction<Todo>() {
            @Override
            public void process(Todo result) {

            }
        });
    }

    public Todo readToDo(long id) {
        //First of all let application check if there is a item with the given id.
        //if yes read the item out of the map, if not then use the crudOperations to read from db

        //TODO to discuss, do we always want to read the item from web or not?

        final Todo[] returnItem = new Todo[1];
        crudOperations.readToDo(id, new ICRUDOperationsAsync.CallbackFunction<Todo>() {
            @Override
            public void process(Todo result) {
                returnItem[0] = result;
            }
        });


        return returnItem[0];
    }

    public void onDestroy() {
        toDoDetailView = null;
    }
}
