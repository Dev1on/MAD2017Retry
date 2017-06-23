package com.example.avenger.mad2017retry.presenter;

import com.example.avenger.mad2017retry.view.ToDoDetailView;
import com.example.avenger.mad2017retry.view.ToDoListView;

/**
 * Created by Avenger on 21.06.17.
 */

public class ToDoDetailPresenter {

    private ToDoDetailView toDoDetailView;

    public ToDoDetailPresenter(ToDoDetailView aToDoDetailView) {
        this.toDoDetailView = aToDoDetailView;
    }

    public void onDestroy() {
        toDoDetailView = null;
    }
}
