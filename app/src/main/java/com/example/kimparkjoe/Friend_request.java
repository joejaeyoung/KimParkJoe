package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.TreeMap;

public class Friend_request extends AppCompatActivity {

    private ArrayList<String> NAME_list = new ArrayList<>();
    private ArrayList<Number> PROFILE_list = new ArrayList<>();

    public static TreeMap<String, Number> friendRequestMap = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        //TODO : DB에서 친구 요청을 보낸 사용자의 프로필 사진 숫자와 이름을 불러옴
        {
            friendRequestMap.put("REQUESTER_1",1);
            friendRequestMap.put("REQUESTER_2",2);
            friendRequestMap.put("REQUESTER_3",3);
            friendRequestMap.put("REQUESTER_4",4);
            friendRequestMap.put("REQUESTER_5",5);
        }

        for(String key : friendRequestMap.keySet()){
            NAME_list.add(key);
            PROFILE_list.add(friendRequestMap.get(key));
        }

        RecyclerView recyclerView = findViewById(R.id.rv_received_request_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FriendRequestAdapter adapter = new FriendRequestAdapter(NAME_list,PROFILE_list);
        recyclerView.setAdapter(adapter);
    }
}