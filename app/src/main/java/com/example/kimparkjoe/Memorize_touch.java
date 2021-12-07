package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class Memorize_touch extends AppCompatActivity {
    private ArrayList<String> ENG_list, KOR_list;
    private String curr_ENG, curr_KOR;
    private TextView wordShown;
    private int position;
    private boolean currShownIsENG;  // true 면 영어 띄우기
    private TextView upperBar;
    private Button BookMark;

    private FirebaseDatabase database, bookmarkDatabase;
    private DatabaseReference databaseReference, bookmarkReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_touch);
        wordShown = (TextView) findViewById(R.id.tv_memorize_touch_word);
        findViewById(R.id.btn_memorize_touch_pre).setOnClickListener(onClickListener);
        findViewById(R.id.btn_memorize_touch_next).setOnClickListener(onClickListener);
        findViewById(R.id.btn_memorize_touch_quit).setOnClickListener(onClickListener);
        BookMark = findViewById(R.id.btn_memorize_touch_bookmark);
        BookMark.setOnClickListener(onClickListener);
        wordShown.setOnClickListener(onClickListener);

        upperBar = (TextView) findViewById(R.id.memorize_touch_upper_bar);
        upperBar.setText(" "+Word_main.curr_week+"주차");

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
                case R.id.tv_memorize_touch_word:
                    toggleWordShown();
                    break;
                case R.id.btn_memorize_touch_quit:
                    finish();
                    break;
                case R.id.btn_memorize_touch_bookmark:
                    getBookMark();
                    break;
            }
        }
    };

    private void getBookMark(){
        BookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BookMark.isSelected() == false) {
                    BookMark.setSelected(true);

                    ((MainActivity)MainActivity.context_main).putBookmarkWordsToDB(ENG_list.get(position), KOR_list.get(position));
                }
                else if(BookMark.isSelected() == true){
                    BookMark.setSelected(false);

                    bookmarkReference = bookmarkDatabase.getInstance().getReference();
                    bookmarkReference.child("user").child(MainActivity.userEmail).child("Bookmark").removeValue();
                }
            }
        });
    }

    private void goPreWord(){
        if(position==0){
            Toast.makeText(this,"단어 목록의 처음입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position--;
        if(Memorize_method_select.isAskingEng){     // 영어 맞추기면
            currShownIsENG = false;
        }
        else
            currShownIsENG = true;
        setTextWordShown();
    }

    private void goNextWord(){
        if(position==ENG_list.size()-1){
            Toast.makeText(this,"단어 목록의 끝입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position++;
        if(Memorize_method_select.isAskingEng){     // 영어 맞추기면
            currShownIsENG = false;
        }
        else
            currShownIsENG = true;
        setTextWordShown();
    }

    private void setTextWordShown(){
        if(currShownIsENG) wordShown.setText(ENG_list.get(position));
        else wordShown.setText(KOR_list.get(position));

        //즐겨찾기 여부 표시
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(MainActivity.userEmail).child("Bookmark").child(ENG_list.get(position));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    BookMark.setSelected(true);
                }
                else {
                    BookMark.setSelected(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });
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
            KOR_list.add(MainActivity.wordMap.get(key));
        }
    }
}