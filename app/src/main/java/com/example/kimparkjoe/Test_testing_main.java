package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Test_testing_main extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> ENG_list, KOR_list;
    private ArrayList<Integer> ans_num_list;
    private TextView wordShown, selection_1, selection_2, selection_3, selection_4;
    private int position;
    private boolean currShownIsEng; // true면 영어 띄움

    private TextView en_word;   //tv_test_select_word
    private TextView test_totalNum;
    private TextView test_timer;
    private TextView test_weeks;
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

    @Override
    public void onClick(View view) {

    }
}