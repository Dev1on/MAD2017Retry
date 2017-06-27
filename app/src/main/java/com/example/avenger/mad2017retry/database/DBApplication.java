package com.example.avenger.mad2017retry.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.avenger.mad2017retry.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class DBApplication extends Application  {
    private static String logger = DBApplication.class.getSimpleName();

    private ICRUDOperationsAsync crudOperations;

    @Override
    public void onCreate() {
        super.onCreate();
     }

    public void setCRUDOperations (String modus) {
        Context context = this.getApplicationContext();
        if (modus.equals("remote")) {
            this.crudOperations = new RemoteCRUDOperationsImpl(context);

            LocalCRUDOperationsImpl localCRUD = new LocalCRUDOperationsImpl(context);
            createLocalTestData(localCRUD);

            final List<Todo>[] localList = new List[]{new ArrayList<>()};
            localCRUD.readAllToDos(result -> {
                Log.i("DBAP", "Result is: " + result.size());
                localList[0].addAll(result);
                Log.i("DBApplication", "localData exists: " + localList[0].size());
                if (localList[0].size() > 0) {
                    //TODO if local db has data, then delete all online data and push all local data to remote db

                }
            });
        } else if (modus.equals("local")) {
            this.crudOperations = new LocalCRUDOperationsImpl(context);
            createLocalTestData(crudOperations);
        }
    }

    private void createLocalTestData(ICRUDOperationsAsync crudOperations) {
        //local test data
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

    public ICRUDOperationsAsync getCrudOperations() {
        return crudOperations;
    }
}
