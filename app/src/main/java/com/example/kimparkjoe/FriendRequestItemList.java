package com.example.kimparkjoe;

import com.google.firebase.database.Exclude;

import java.util.Map;
import java.util.TreeMap;

public class FriendRequestItemList {
    private String email;
    private String name;

    public FriendRequestItemList() {

    }

    public FriendRequestItemList(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
