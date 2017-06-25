package com.example.avenger.mad2017retry.database;

import com.example.avenger.mad2017retry.model.Todo;

import java.util.List;

public interface ICRUDOperationsAsync {

    //inner interface for defining callback function
    interface CallbackFunction<T> {
        void process(T result);
    }

    void createToDo(Todo item, CallbackFunction<Todo> callback);
    void readAllToDos(CallbackFunction<List<Todo>> callback);
    void readToDo(long id, CallbackFunction<Todo> callback);
    void updateToDo(long id, Todo item, CallbackFunction<Todo> callback);
    void deleteToDo(long id, CallbackFunction<Boolean> callback);
}