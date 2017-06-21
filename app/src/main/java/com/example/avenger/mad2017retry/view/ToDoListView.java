package com.example.avenger.mad2017retry.view;

/**
 * Created by Avenger on 21.06.17.
 */

public interface ToDoListView {

    void showDetails();

    void createItem();

    void toggleStatus();

    void sortBySolved();

    void sortByOpen();

    void sortByImportanceAndDate();

    void sortByDateAndImportance();
}
