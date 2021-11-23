package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Test_testing_main extends AppCompatActivity {
    private ArrayList<String> ENG_Data = null;
    private ArrayList<String> KOR_Data = null;
    TextView en_word;
    TextView test_totalNum;
    TextView test_timer;
    TextView test_weeks;




    //생성자에서 데이터 리스트 객체를 전달받음.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_testing_main);
    }
}