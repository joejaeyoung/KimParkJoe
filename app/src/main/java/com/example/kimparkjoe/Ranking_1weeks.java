package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Ranking_1weeks extends AppCompatActivity {
    private Button prevButton;
    private Button nextButton;
    private Button testButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking1weeks);

        prevButton = findViewById(R.id.btn_prev_ranking1);
        nextButton = findViewById(R.id.btn_next_ranking1);
        testButton = findViewById(R.id.btn_test_start1);


        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Ranking_main.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Ranking_2weeks.class);
                startActivity(intent);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Test_selectweeks_main.class);
                startActivity(intent);
            }
        });

    }
}