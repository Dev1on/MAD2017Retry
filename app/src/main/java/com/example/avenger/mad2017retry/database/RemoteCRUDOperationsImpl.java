package com.example.avenger.mad2017retry.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.avenger.mad2017retry.model.Todo;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public class RemoteCRUDOperationsImpl implements ICRUDOperationsAsync {
    //inner interface to define webapi calls to given remote database application
    public interface ICRUDWebApi {
        @POST("/api/todos")
        Call<Todo > createToDoItem(@Body Todo item);

        @GET("/api/todos")
        Call<List<Todo>> readAllToDoItems();

        @GET("/api/todos/{id}")
        Call<Todo> readToDoItem(@Path("id") long id);

        @PUT("/api/todos/{id}")
        Call<Todo> updateToDoItem(@Path("id") long id, @Body Todo item);

        @DELETE("/api/todos/{id}")
        Call<Boolean> deleteToDoItem(@Path("id") long id);

        //TODO implement clearDB method
    }

    private static String WEB_API_BASE_URL = "http://localhost:8080/";
    protected static String logger = RemoteCRUDOperationsImpl.class.getSimpleName();

    private LocalCRUDOperationsImpl localCRUD;
    private ICRUDWebApi webAPI;

    public RemoteCRUDOperationsImpl(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webAPI = retrofit.create(ICRUDWebApi.class);
        this.localCRUD = new LocalCRUDOperationsImpl(context);
    }


    @Override
    public void createToDo(Todo item, final CallbackFunction<Todo> callback) {
        new AsyncTask<Todo, Void, Todo>() {
            @Override
            protected Todo doInBackground(Todo... params) {
                final Todo[] newItem = {new Todo("", "")};
                localCRUD.createToDo(item, result -> newItem[0] = result);
                try {
                    return webAPI.createToDoItem(newItem[0]).execute().body();
                } catch (IOException e) {
                    Log.d(logger, "Item not created.");
                    e.printStackTrace();
                }
                return null;
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
                try {
                    return webAPI.readAllToDoItems().execute().body();
                } catch (IOException e) {
                    Log.d(logger, "Could not read all items.");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<Todo> toDoItems) {
                callback.process(toDoItems);
            }
        }.execute();
    }

    @Override
    public void readToDo(long id, final CallbackFunction<Todo> callback) {
        new AsyncTask<Long, Void, Todo>() {
            @Override
            protected Todo doInBackground(Long... params) {
                try {
                    return webAPI.readToDoItem(params[0]).execute().body();
                } catch (IOException e) {
                    Log.d(logger, "Could not read item with id: " + params[0]);
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Todo toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(id);
    }

    @Override
    public void updateToDo(long id, final Todo item, final CallbackFunction<Todo> callback) {
        new AsyncTask<Long, Void, Todo>() {
            @Override
            protected Todo doInBackground(Long... params) {
                final Todo[] newItem = {new Todo("", "")};
                localCRUD.updateToDo(id, item, result -> newItem[0] = result);
                try {
                    return webAPI.updateToDoItem( newItem[0].getId(),  newItem[0]).execute().body();
                } catch (IOException e) {
                    Log.d(logger, "Could not update item with id: " + params[0]);
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Todo todo) {
                callback.process(todo);
            }
        }.execute(id);
    }

    @Override
    public void deleteToDo(long id, final CallbackFunction<Boolean> callback) {
        new AsyncTask<Long, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Long... params) {
                final boolean[] deletedLocally = {false};
                localCRUD.deleteToDo(id, result -> deletedLocally[0] = result);
                if (deletedLocally[0]) {
                    try {
                        return webAPI.deleteToDoItem(params[0]).execute().body();
                    } catch (IOException e) {
                        Log.d(logger, "Could not delete item with id: " + params[0]);
                        e.printStackTrace();
                    }
                    return false;
                }
                Log.d(logger, "Could not delete todo locally.");
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.process(aBoolean);
            }
        }.execute(id);
    }
}