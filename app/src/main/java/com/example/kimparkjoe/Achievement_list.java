package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Achievement_list extends AppCompatActivity {

    private ArrayList<String> achievement_list;
    private ArrayList<Boolean> isCompleted_list;

    private ImageButton button_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_list);

        button_quit = findViewById(R.id.btn_achieve_quit);
        button_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        achievement_list = new ArrayList<>();
        isCompleted_list = new ArrayList<>();
        for(String key : Setting_main.achieveMap.keySet()){
            achievement_list.add(key);
            isCompleted_list.add(Setting_main.achieveMap.get(key));
        }

        RecyclerView recyclerView = findViewById(R.id.rv_achieve_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AchieveAdapter adapter = new AchieveAdapter(achievement_list,isCompleted_list);
        recyclerView.setAdapter(adapter);
    }
}