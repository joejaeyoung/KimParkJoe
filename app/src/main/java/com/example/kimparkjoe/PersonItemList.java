package com.example.kimparkjoe;

import com.google.firebase.database.Exclude;

import java.util.Map;
import java.util.TreeMap;

public class PersonItemList {
    private String profile;
    private String name;
    private String message;

    public PersonItemList() {

    }

    public PersonItemList(String profile, String name) {
        this.profile = profile;
        this.name = name;
    }

    public PersonItemList(String profile, String name, String message) {
        this.profile = profile;
        this.name = name;
        this.message = message;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
