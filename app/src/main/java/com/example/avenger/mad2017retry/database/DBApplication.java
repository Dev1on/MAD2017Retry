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

            Todo newItem1 = new Todo("Name1", "Description1");
            newItem1.setDone(true);
            newItem1.setExpiry(123);
            newItem1.setFavourite(false);
            newItem1.setLocation(new Todo.Location("Havanna", new Todo.LatLng(2,3)));

            Todo newItem2 = new Todo("Name2", "Description2");
            newItem2.setDone(true);
            newItem2.setExpiry(1234);
            newItem2.setFavourite(true);
            newItem2.setLocation(new Todo.Location("Manhattan", new Todo.LatLng(2,3)));

            Todo newItem3 = new Todo("Name3", "Description3");
            newItem3.setDone(false);
            newItem3.setExpiry(1235);
            newItem3.setFavourite(true);
            newItem3.setLocation(new Todo.Location("Warschau", new Todo.LatLng(2,3)));

            crudOperations.createToDo(newItem1, (Todo result) -> Log.d(logger, "Item 1 created with id: " + result.getId()));
            crudOperations.createToDo(newItem2, (Todo result) -> Log.d(logger, "Item 2 created with id: " + result.getId()));
            crudOperations.createToDo(newItem3, (Todo result) -> Log.d(logger, "Item 3 created with id: " + result.getId()));
        }


    }

    public ICRUDOperationsAsync getCrudOperations() {
        return crudOperations;
    }
}
