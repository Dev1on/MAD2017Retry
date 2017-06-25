package com.example.avenger.mad2017retry.database;

import com.example.avenger.mad2017retry.model.ToDoItem;

import java.util.List;

/**
 * Created by Adi on 23.06.2017.
 */

public interface ICRUDOperationsAsync {

    //inner interface for defining callback function
    public static interface CallbackFunction<T> {
        public void process(T result);
    }

    public void createToDo(ToDoItem item, CallbackFunction<ToDoItem> callback);
    public void readAllToDos(CallbackFunction<List<ToDoItem>> callback);
    public void readToDo(long id, CallbackFunction<ToDoItem> callback);
    public void updateToDo(long id, ToDoItem item, CallbackFunction<ToDoItem> callback);
    public void deleteToDo(long id, CallbackFunction<Boolean> callback);



}
