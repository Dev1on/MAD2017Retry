package com.example.avenger.mad2017retry.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by Adi on 24.06.2017.
 */

public class DBApplication extends Application  {
    private static String logger = DBApplication.class.getSimpleName();

    private ICRUDOperationsAsync crudOperations;

    /*TODO we need an application scoped class which contains depending on the connection
        to create a local or remote crudoperations.
    */

    @Override
    public void onCreate() {
        super.onCreate();

        //TODO check internet connection and decide which crudoperations to create
        //TODO need for service which checks online status and bd accessibility

        String localOrRemote = "remote";
        if (localOrRemote.equals("remote")) {
            this.crudOperations = new RemoteCRUDOperationsImpl();
        } else if (localOrRemote.equals("local")) {
            Context context = this.getApplicationContext();
            this.crudOperations = new LocalCRUDOperationsImpl(context);
        }
    }

    public ICRUDOperationsAsync getCrudOperations() {
        return crudOperations;
    }
}
