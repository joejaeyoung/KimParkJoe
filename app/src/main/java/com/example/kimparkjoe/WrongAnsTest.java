package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class WrongAnsTest extends AppCompatActivity implements View.OnClickListener {
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private ArrayList<String> ENG_list, KOR_list;
    private TextView wordShown;
    private int position;
    private TextView upperBar;
    private boolean currShownIsENG;  // true 면 영어 띄우기
    private TreeMap<String,String> testWordMap = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        //TODO : DB에 정보 추가 후 아래 코드 삭제
        {
            testWordMap.put("temp_1.1","temp_1.2");
            testWordMap.put("temp_2.1","temp_2.2");
            testWordMap.put("temp_3.1","temp_3.2");
            testWordMap.put("temp_4.1","temp_4.2");
            testWordMap.put("temp_5.1","temp_5.2");
        }

 */

        setContentView(R.layout.activity_wrong_ans_test);
        findViewById(R.id.btn_wrong_ans_touch_pre).setOnClickListener(this);
        findViewById(R.id.btn_wrong_ans_touch_next).setOnClickListener(this);
        findViewById(R.id.btn_wrong_ans_touch_quit).setOnClickListener(this);
        wordShown = (TextView) findViewById(R.id.tv_wrong_ans_touch_word);
        wordShown.setOnClickListener(this);

        upperBar = (TextView) findViewById(R.id.tv_wrong_ans_test_upper_bar);

        position=0;
        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        if(WrongANS_main.isBookMark){
            testWordMap = WrongANS_bookmark_main.bookmarkWordMap;
            upperBar.setText("즐겨찾기");
            System.out.println("즐겨찾기 test -> "+testWordMap);
            for(String key:WrongANS_bookmark_main.bookmarkWordMap.keySet()){
                ENG_list.add(key);
                KOR_list.add(WrongANS_bookmark_main.bookmarkWordMap.get(key));
            }
            System.out.println("ENG_list : "+ENG_list);
            System.out.println("KOR_list : "+KOR_list);
        }
        else {
            testWordMap = WrongANS_wrongquestion_main.wrongWordMap;
            upperBar.setText("틀린 단어");
            System.out.println("오답 노트 test -> "+testWordMap);
            for(String key: testWordMap.keySet()){
                ENG_list.add(key);
                KOR_list.add(testWordMap.get(key));
            }
        }
        shuffleList();
        currShownIsENG=true;
        setTextWordShown();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_wrong_ans_touch_pre:
                goPreWord();
                break;
            case R.id.btn_wrong_ans_touch_next:
                goNextWord();
                break;
            case R.id.tv_wrong_ans_touch_word:
                toggleWordShown();
                break;
            case R.id.btn_wrong_ans_touch_quit:
                finish();
                break;
        }
    }

    private void goPreWord(){
        if(position==0){
            Toast.makeText(this,"단어 목록의 처음입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position--;
        currShownIsENG=true;
        setTextWordShown();
    }

    private void goNextWord(){
        if(position==ENG_list.size()-1){
            Toast.makeText(this,"단어 목록의 끝입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position++;
        currShownIsENG = true;
        setTextWordShown();
    }

    private void setTextWordShown(){
        if(currShownIsENG) wordShown.setText(ENG_list.get(position));
        else wordShown.setText(KOR_list.get(position));
    }

    private void toggleWordShown(){
        if(currShownIsENG){     // 현재 영어가 띄워진 상태면
            currShownIsENG=false;
        }
        else{
            currShownIsENG=true;
        }
        setTextWordShown();
    }

    private void shuffleList(){
        Collections.shuffle(ENG_list);
        KOR_list.clear();
        for(String key : ENG_list){
            if(WrongANS_main.isBookMark)
                KOR_list.add(testWordMap.get(key));
            else
                KOR_list.add(testWordMap.get(key));
        }
    }
}