package com.example.avenger.mad2017retry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.avenger.mad2017retry.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adi on 23.06.2017.
 */

public class LocalCRUDOperationsImpl implements ICRUDOperationsAsync {

    private static final String DB_NAME = "TODOS";
    protected static String logger = LocalCRUDOperationsImpl.class.getSimpleName();

    private SQLiteDatabase db;

    public LocalCRUDOperationsImpl(Context context) {
        db = context.openOrCreateDatabase("mydb.sqlite", Context.MODE_PRIVATE, null);
        if (db.getVersion() == 0) {
            db.setVersion(1);

            //TODO create local database with all needed attributes
            db.execSQL("CREATE TABLE " + DB_NAME + " (ID INTEGER PRIMARY KEY, NAME TEXT, DESCRIPTION TEXT, EXPIRY INTEGER, DONE BOOLEAN, FAVOURITE BOOLEAN, CONTACTS INTEGER, LAENGEGRAD TEXT, BREITENGRAD TEXT)");
            db.execSQL("CREATE TABLE CONTACTS (ID INTEGER PRIMARY KEY, NAME TEXT, NUMBER TEXT)");
            db.execSQL("CREATE TABLE TODOSCONTACTS (TODOID INTEGER REFERENCES TODOS(ID), CONTACTID INTEGER REFERENCES CONTACTS(ID), PRIMARY KEY(TODOSID, CONTACTID))");
        }
    }

    @Override
    public void createToDo(final ToDoItem item, final CallbackFunction<ToDoItem> callback) {
        new AsyncTask<ToDoItem, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(ToDoItem... params) {
                ContentValues values = new ContentValues();
                values.put("NAME", item.getName());
                values.put("DESCRIPTION", item.getDescription());

                //TODO add other values that are needed


                long id = db.insertOrThrow(DB_NAME, null, values);
                item.setId((int) id);

                return item;
            }

            @Override
            protected void onPostExecute(ToDoItem toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(item);
    }

    @Override
    public void readAllToDos(final CallbackFunction<List<ToDoItem>> callback) {
        new AsyncTask<Void, Void, List<ToDoItem>>() {
            @Override
            protected List<ToDoItem> doInBackground(Void... params) {
                List<ToDoItem> items = new ArrayList<ToDoItem>();

                Cursor cursor = db.query(DB_NAME, new String[]{"ID", "NAME", "DESCRIPTION", "DUEDATE"},null,null,null,null,"ID");
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    boolean next = false;
                    do {
                        ToDoItem item = new ToDoItem("Name");

                        //TODO read out all db columns
                        long id = cursor.getLong(cursor.getColumnIndex("ID"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));

                        //TODO set all data on item
                        item.setId((int) id);
                        item.setName(name);
                        item.setDescription(description);

                        items.add(item);
                        next = cursor.moveToNext();

                    } while (next);

                }
                return items;
            }

            @Override
            protected void onPostExecute(List<ToDoItem> toDoItems) {
                callback.process(toDoItems);
            }
        }.execute();

    }

    @Override
    public void readToDo(long id, final CallbackFunction<ToDoItem> callback) {
        new AsyncTask<Long, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(Long... params) {
               //TODO implementation of local readToDo

                return new ToDoItem("Name");
            }

            @Override
            protected void onPostExecute(ToDoItem toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(id);

    }

    @Override
    public void updateToDo(long id, ToDoItem item, CallbackFunction<ToDoItem> callback) {

        //TODO implement local updateToDo method
    }

    @Override
    public void deleteToDo(long id, final CallbackFunction<Boolean> callback) {
        new AsyncTask<Long, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Long... params) {
                //TODO implement local deleteToDo method

                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.process(aBoolean);
            }
        }.execute(id);
    }
}
