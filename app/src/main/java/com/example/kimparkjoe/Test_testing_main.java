package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Test_testing_main extends AppCompatActivity {

    private ArrayList<String> ENG_list, KOR_list;

    private TextView en_word;//tv_test_select_word
    private TextView test_totalNum;
    private TextView test_timer;
    private TextView test_weeks;
    private TextView test_selectFIRST;
    private TextView test_selectSECOND;
    private TextView test_selectTHIRD;
    private TextView test_selectFOURTH;
    private Button selectPre;
    private Button selectNext;

    MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_testing_main);

        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : MainActivity.wordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(MainActivity.wordMap.get(key));
        }

    }
}