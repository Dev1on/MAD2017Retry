package com.example.avenger.mad2017retry.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.avenger.mad2017retry.database.ICRUDOperationsAsync.CallbackFunction;
import com.example.avenger.mad2017retry.model.Todo;

public class DBApplication extends Application  {
    private static String logger = DBApplication.class.getSimpleName();

    private ICRUDOperationsAsync crudOperations;


    //TODO implement a Map where all read items are stored. just do a recheck when doing actions. performance reasons

    /*TODO we need an application scoped class which contains depending on the connection
        to create a local or remote crudoperations.
    */

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO check internet connection and decide which crudoperations to create
        //TODO need for service which checks online status and bd accessibility

        String localOrRemote = "local";
        if (localOrRemote.equals("remote")) {
            this.crudOperations = new RemoteCRUDOperationsImpl();
        } else if (localOrRemote.equals("local")) {
            Context context = this.getApplicationContext();
            this.crudOperations = new LocalCRUDOperationsImpl(context);
        }

        Todo newItem1 = new Todo("Name1", "Description1");
        Todo newItem2 = new Todo("Name2", "Description2");
        Todo newItem3 = new Todo("Name3", "Description3");
        crudOperations.createToDo(newItem1, (Todo result) -> Log.d(logger, "Item 1 created."));
        crudOperations.createToDo(newItem2, (Todo result) -> Log.d(logger, "Item 2 created."));
        crudOperations.createToDo(newItem3, (Todo result) -> Log.d(logger, "Item 3 created."));
    }

    public ICRUDOperationsAsync getCrudOperations() {
        return crudOperations;
    }
}
