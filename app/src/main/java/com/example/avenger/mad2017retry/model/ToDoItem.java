package com.example.avenger.mad2017retry.model;


import android.location.Location;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Avenger on 21.06.17.
 */

public class ToDoItem implements Serializable{

    private int id;
    private String name;
    private String description;
    private Status status;
    private boolean favorite;

    private List<String> contacts;
    private Location location;
    private String locationName;

    public ToDoItem(String aName) {
        this.name = aName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}