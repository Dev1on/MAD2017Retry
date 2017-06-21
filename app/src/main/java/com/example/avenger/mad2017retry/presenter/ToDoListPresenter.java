package com.example.avenger.mad2017retry.presenter;

import com.example.avenger.mad2017retry.view.ToDoListView;

/**
 * Created by Avenger on 21.06.17.
 */

public class ToDoListPresenter {

    private ToDoListView toDoListView;

    public ToDoListPresenter(ToDoListView aToDoListView) {
        this.toDoListView = aToDoListView;
    }

    public void onDestroy() {
        toDoListView = null;
    }
}
