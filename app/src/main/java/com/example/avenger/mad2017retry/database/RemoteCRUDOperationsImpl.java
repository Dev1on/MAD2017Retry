package com.example.avenger.mad2017retry.database;

import android.os.AsyncTask;
import android.util.Log;

import com.example.avenger.mad2017retry.model.ToDoItem;

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

/**
 * Created by Adi on 23.06.2017.
 */

public class RemoteCRUDOperationsImpl implements ICRUDOperationsAsync {

    private static String WEB_API_BASE_URL = "http://localhost:8080/";

    //inner interface to define webapi calls to given remote database application
    public interface ICRUDWebApi {
        @POST("/api/todos")
        public Call<ToDoItem> createToDoItem(@Body ToDoItem item);

        @GET("/api/todos")
        public Call<List<ToDoItem>> readAllToDoItems();

        @GET("/api/todos/{id}")
        public Call<ToDoItem> readToDoItem(@Path("id") long id);

        @PUT("/api/todos/{id}")
        public Call<ToDoItem> updateToDoItem(@Path("id") long id, @Body ToDoItem item);

        @DELETE("/api/todos/{id}")
        public Call<Boolean> deleteToDoItem(@Path("id") long id);

    }

    private ICRUDWebApi webAPI;

    public RemoteCRUDOperationsImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WEB_API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.webAPI = retrofit.create(ICRUDWebApi.class);
    }


    @Override
    public void createToDo(ToDoItem item, final CallbackFunction<ToDoItem> callback) {
        new AsyncTask<ToDoItem, Void, ToDoItem>() {
            @Override
            protected ToDoItem doInBackground(ToDoItem... params) {
                try {
                    return webAPI.createToDoItem(params[0]).execute().body();
                } catch (IOException e) {
                    Log.d("RemoteCRUDOperationsImpl", "Item not created.");
                    e.printStackTrace();
                }
                return null;
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
                try {
                    return webAPI.readAllToDoItems().execute().body();
                } catch (IOException e) {
                    Log.d("RemoteCRUDOperationsImpl", "Could not read all items.");
                    e.printStackTrace();
                }
                return null;
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
                try {
                    return webAPI.readToDoItem(params[0]).execute().body();
                } catch (IOException e) {
                    Log.d("RemoteCRUDOperationsImpl", "Could not read item with id: " + params[0]);
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ToDoItem toDoItem) {
                callback.process(toDoItem);
            }
        }.execute(id);
    }

    @Override
    public void updateToDo(long id, ToDoItem item, final CallbackFunction<ToDoItem> callback) {
        //TODO implement remote updateToDo method with asynctask
    }

    @Override
    public void deleteToDo(long id, final CallbackFunction<Boolean> callback) {
        new AsyncTask<Long, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Long... params) {
                try {
                    return webAPI.deleteToDoItem(params[0]).execute().body();
                } catch (IOException e) {
                    Log.d("RemoteCRUDOperationsImpl", "Could not delete item with id: " + params[0]);
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                callback.process(aBoolean);
            }
        }.execute(id);

    }
}
