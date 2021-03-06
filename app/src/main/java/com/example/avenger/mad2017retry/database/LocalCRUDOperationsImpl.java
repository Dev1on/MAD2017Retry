package com.example.avenger.mad2017retry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.avenger.mad2017retry.model.Todo;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL("delete from "+ DB_NAME);
    }

    @Override
    public void createToDo(final Todo item, final CallbackFunction<Todo> callback) {
        new AsyncTask<Todo, Void, Todo>() {
            @Override
            protected Todo doInBackground(Todo... params) {
                ContentValues values = setContentValuesForTodo(item);
                long id = db.insertOrThrow(DB_NAME, null, values);
                item.setId((int) id);

                return item;
            }

            @Override
            protected void onPostExecute(Todo toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(item);
    }


    @Override
    public void readAllToDos(final CallbackFunction<List<Todo>> callback) {
        new AsyncTask<Void, Void, List<Todo>>() {
            @Override
            protected List<Todo> doInBackground(Void... params) {
                List<Todo> todoList = new ArrayList<>();

                Cursor cursor = db.query(DB_NAME, new String[]{"ID", "NAME", "DESCRIPTION", "EXPIRY", "DONE", "FAVOURITE", "LAENGENGRAD", "BREITENGRAD", "LOCATIONNAME"},null,null,null,null,"ID");
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    boolean next;
                    do {
                        Todo todo = setTodoFromDB(cursor);
                        todoList.add(todo);
                        next = cursor.moveToNext();
                    } while (next);
                }
                cursor.close();
                return todoList;
            }

            @Override
            protected void onPostExecute(List<Todo> toDoItems) {
                callback.process(toDoItems);
            }
        }.execute();

    }

    @Override
    public void readToDo(final long id, final CallbackFunction<Todo> callback) {
        new AsyncTask<Long, Void, Todo>() {
            @Override
            protected Todo doInBackground(Long... params) {
                Cursor cursor = db.query(DB_NAME, new String[]{"ID", "NAME", "DESCRIPTION", "EXPIRY", "DONE", "FAVOURITE", "LAENGENGRAD", "BREITENGRAD", "LOCATIONNAME"},null,null,null,null,"ID");
                Todo returnItem = new Todo("Name", "Description");
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    boolean next = false;
                    do {
                        if (cursor.getLong(cursor.getColumnIndex("ID")) == id) {
                            returnItem = setTodoFromDB(cursor);
                            break;
                        }
                        next = cursor.moveToNext();
                    } while (next);
                }
                return returnItem;
            }

            @Override
            protected void onPostExecute(Todo todo) {
                callback.process(todo);
            }
        }.execute(id);

    }

    @Override
    public void updateToDo(final long id, final Todo item, final CallbackFunction<Todo> callback) {
        new AsyncTask<Long, Void, Todo>() {
            @Override
            protected Todo doInBackground(Long... params) {
                ContentValues values = setContentValuesForTodo(item);
                int rowsAffected = db.update(DB_NAME, values, "ID=?", new String[]{String.valueOf(id)} );
                if (rowsAffected > 0)
                   return item;
                return null;
            }

            @Override
            protected void onPostExecute(Todo toDoItem) {
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
                return rowsAffected > 0;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.process(aBoolean);
            }
        }.execute(id);
    }

    @NonNull
    private Todo setTodoFromDB(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
        long expiry = cursor.getLong(cursor.getColumnIndex("EXPIRY"));
        boolean done = (cursor.getInt(cursor.getColumnIndex("DONE"))) == 1;
        boolean favourite = (cursor.getInt(cursor.getColumnIndex("FAVOURITE"))) == 1;
        long lat = cursor.getLong(cursor.getColumnIndex("LAENGENGRAD"));
        long lng = cursor.getLong(cursor.getColumnIndex("BREITENGRAD"));
        String location_name = cursor.getString(cursor.getColumnIndex("LOCATIONNAME"));
        Todo.Location location = new Todo.Location(location_name, new Todo.LatLng(lat,lng));

        //TODO read out all contacts
        List<String> contacts = new ArrayList<String>();

        Todo todo = new Todo(name, description);
        todo.setId((int) id);
        todo.setExpiry(expiry);
        todo.setDone(done);
        todo.setFavourite(favourite);
        todo.setLocation(location);
        todo.setContacts(contacts);
        return todo;
    }

    @NonNull
    private ContentValues setContentValuesForTodo(Todo item) {
        ContentValues values = new ContentValues();
        values.put("NAME", item.getName());
        values.put("DESCRIPTION", item.getDescription());
        values.put("EXPIRY", item.getExpiry());
        values.put("DONE", item.isDone());
        values.put("FAVOURITE", item.isFavourite());
        values.put("LAENGENGRAD", item.getLocation().getLatlng().getLat());
        values.put("BREITENGRAD", item.getLocation().getLatlng().getLng());
        values.put("LOCATIONNAME", item.getLocation().getName());
        //TODO add contacts to db


        return values;
    }
}
