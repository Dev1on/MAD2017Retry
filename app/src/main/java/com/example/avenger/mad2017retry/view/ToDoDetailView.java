package com.example.avenger.mad2017retry.view;

import com.example.avenger.mad2017retry.model.Todo;

/**
 * Created by Avenger on 21.06.17.
 */

public interface ToDoDetailView {
    void saveItem();

    void deleteItem();

    void setTodoView(Todo todo);

    Todo getCurrentTodo();

    void displayTodoNotFound();
}
