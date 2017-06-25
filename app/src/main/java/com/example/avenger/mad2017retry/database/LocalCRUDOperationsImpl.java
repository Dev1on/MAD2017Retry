package com.example.avenger.mad2017retry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.avenger.mad2017retry.model.ToDoItem;
import com.example.avenger.mad2017retry.model.Todo;

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

            db.execSQL("CREATE TABLE " + DB_NAME + " (ID INTEGER PRIMARY KEY, NAME TEXT, DESCRIPTION TEXT, EXPIRY INTEGER, DONE INTEGER, FAVOURITE INTEGER, LAENGENGRAD INTEGER, BREITENGRAD INTEGER, LOCATIONNAME TEXT)");
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
                //values.put("EXPIRY", item.getExpiry());
                //values.put("DONE", item.getDone());
                //values.put("FAVOURITE", item.getFavourite());
                //values.put("LAENGENGRAD", item.getLocation().getLatLng().getLat());
                //values.put("BREITENGRAD", item.getLocation().getLatLng().getLng());
                //values.put("LOCATIONNAME", item.getLocation().getName());
                //TODO add contacts to db

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

                Cursor cursor = db.query(DB_NAME, new String[]{"ID", "NAME", "DESCRIPTION", "EXPIRY", "DONE", "FAVOURITE", "LAENGENGRAD", "BREITENGRAD"},null,null,null,null,"ID");
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    boolean next = false;
                    do {
                        ToDoItem item = new ToDoItem("Name");

                        long id = cursor.getLong(cursor.getColumnIndex("ID"));
                        String name = cursor.getString(cursor.getColumnIndex("NAME"));
                        String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
                        long expiry = cursor.getLong(cursor.getColumnIndex("EXPIRY"));
                        boolean done = (cursor.getInt(cursor.getColumnIndex("DONE")))== 1 ? true : false;
                        boolean favourite = (cursor.getInt(cursor.getColumnIndex("FAVOURITE")))== 1 ? true : false;

                        long lat = cursor.getLong(cursor.getColumnIndex("LAENGENGRAD"));
                        long lng = cursor.getLong(cursor.getColumnIndex("BREITENGRAD"));
                        String location_name = cursor.getString(cursor.getColumnIndex("LOCATIONNAME"));
                        Todo.Location location = new Todo.Location(location_name, new Todo.LatLng(lat,lng));

                        //TODO read out all contacts
                        List<String> contacts = new ArrayList<String>();


                        item.setId((int) id);
                        item.setName(name);
                        item.setDescription(description);
                        //item.setExpiry(expiry);
                        //item.setDone(done);
                        //item.setFavourite(favourite);
                        //item.setLocation(location);
                        //item.setContacts(contacts);

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
    public void readToDo(final long id, final CallbackFunction<ToDoItem> callback) {
        new AsyncTask<Long, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(Long... params) {
                Cursor cursor = db.query(DB_NAME, new String[]{"ID", "NAME", "DESCRIPTION", "EXPIRY", "DONE", "FAVOURITE", "LAENGENGRAD", "BREITENGRAD"},null,null,null,null,"ID");
                ToDoItem returnItem = new ToDoItem("Name");
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    boolean next = false;
                    do {
                        if (cursor.getLong(cursor.getColumnIndex("ID")) == id) {

                            returnItem.setId((int) cursor.getLong(cursor.getColumnIndex("ID")));
                            returnItem.setName(cursor.getString(cursor.getColumnIndex("NAME")));
                            returnItem.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
                            //TODO implement rest of setting attributes from db


                            break;
                        }
                        next = cursor.moveToNext();
                    } while (next);
                }
                return returnItem;
            }

            @Override
            protected void onPostExecute(ToDoItem toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(id);

    }

    @Override
    public void updateToDo(long id, ToDoItem item, final CallbackFunction<ToDoItem> callback) {
        //TODO implement local updateToDo method

        new AsyncTask<Long, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(Long... params) {

                //int rowsAffected = db.update(DB_NAME, values, "ID=?", new String[]{String.valueOf(id)} );

                return null;
            }

            @Override
            protected void onPostExecute(ToDoItem toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(id);
    }

    @Override
    public void deleteToDo(final long id, final CallbackFunction<Boolean> callback) {
        new AsyncTask<Long, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Long... params) {
                int rowsAffected = db.delete(DB_NAME, "ID=?", new String[]{String.valueOf(id)});
                if (rowsAffected > 0)
                    return true;

                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.process(aBoolean);
            }
        }.execute(id);
    }
}
