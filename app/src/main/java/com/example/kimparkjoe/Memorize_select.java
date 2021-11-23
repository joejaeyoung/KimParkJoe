package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Memorize_select extends AppCompatActivity {
    private ArrayList<String> ENG_list, KOR_list;
    private String curr_ENG, curr_KOR;
    private TextView wordShown, selection_1, selection_2, selection_3, selection_4;
    private int position;
    private boolean currShownIsENG;  // true 면 영어 띄우기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_select);
        wordShown = (TextView) findViewById(R.id.tv_memorize_select_word);
        findViewById(R.id.btn_memorize_select_pre).setOnClickListener(onClickListener);
        findViewById(R.id.btn_memorize_select_next).setOnClickListener(onClickListener);
        selection_1 = (TextView) findViewById(R.id.text_memorize_select_FIRST);
        selection_2 = (TextView) findViewById(R.id.text_memorize_select_SECOND);
        selection_3 = (TextView) findViewById(R.id.text_memorize_select_THIRD);
        selection_4 = (TextView) findViewById(R.id.text_memorize_select_FORTH);
        position =0;
        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : MainActivity.wordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(MainActivity.wordMap.get(key));
        }
        if(Memorize_method_select.isRandom){        // 랜덤이면
            shuffleList();
        }
        if(Memorize_method_select.isAskingEng){     // 영어 맞추기면
            currShownIsENG = false;
        }
        else
            currShownIsENG = true;
        System.out.println(ENG_list);
        System.out.println(KOR_list);
        setTextWordShown();

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_memorize_touch_pre:
                    goPreWord();
                    break;
                case R.id.btn_memorize_touch_next:
                    goNextWord();
                    break;
            }
        }
    };

    private void goPreWord(){
        if(position==0){
            Toast.makeText(this,"단어 목록의 처음입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        setTextWordShown();
    }

    private void goNextWord(){
        if(position==ENG_list.size()-1){
            Toast.makeText(this,"단어 목록의 끝입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position++;
        setTextWordShown();
    }

    private void setTextWordShown(){
        if(currShownIsENG) wordShown.setText(ENG_list.get(position));
        else wordShown.setText(KOR_list.get(position));
    }

    private void setTextSelectionWord(){
        //TODO : 선택지 띄우기
    }

    private void shuffleList(){
        Collections.shuffle(ENG_list);
        KOR_list.clear();
        for(String key : ENG_list){
            KOR_list.add(MainActivity.wordMap.get(key));
        }
    }
}