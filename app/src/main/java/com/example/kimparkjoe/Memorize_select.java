package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class Memorize_select extends AppCompatActivity {

    private ArrayList<String> ENG_list, KOR_list, Part_list;
    private ArrayList<Integer> ans_num_list;
    private TextView wordShown, selection_1, selection_2, selection_3, selection_4;
    private int position, Num;
    private boolean currShownIsENG;  // true 면 영어 띄우기
    private ImageView firstCheckIMG, secondCheckIMG, thirdCheckIMG, forthCheckIMG;
    private ImageView firstXIMG, secondXIMG, thirdXIMG, forthXIMG;
    private TextView upperBar;
    private ImageButton BookMark;
    private String part;

    private FirebaseDatabase database, bookmarkDatabase, wordDatabase;
    private DatabaseReference databaseReference, bookmarkReference, wordReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_select);
        wordShown = (TextView) findViewById(R.id.tv_memorize_select_word);
        findViewById(R.id.btn_memorize_select_pre).setOnClickListener(onClickListener);
        findViewById(R.id.btn_memorize_select_next).setOnClickListener(onClickListener);
        selection_1 = (TextView) findViewById(R.id.tv_memorize_select_FIRST);
        selection_1.setOnClickListener(onClickListener);
        selection_2 = (TextView) findViewById(R.id.tv_memorize_select_SECOND);
        selection_2.setOnClickListener(onClickListener);
        selection_3 = (TextView) findViewById(R.id.tv_memorize_select_THIRD);
        selection_3.setOnClickListener(onClickListener);
        selection_4 = (TextView) findViewById(R.id.tv_memorize_select_FORTH);
        selection_4.setOnClickListener(onClickListener);
        findViewById(R.id.btn_memorize_select_quit).setOnClickListener(onClickListener);
        BookMark = findViewById(R.id.btn_memorize_select_bookmark);
        BookMark.setOnClickListener(onClickListener);
        firstCheckIMG = findViewById(R.id.img_memorize_check_FIRST);
        firstXIMG = findViewById(R.id.img_memorize_x_FIRST);
        secondCheckIMG = findViewById(R.id.img_memorize_check_SECOND);
        secondXIMG = findViewById(R.id.img_memorize_x_SECOND);
        thirdCheckIMG = findViewById(R.id.img_memorize_check_THIRD);
        thirdXIMG = findViewById(R.id.img_memorize_x_THIRD);
        forthCheckIMG = findViewById(R.id.img_memorize_check_FORTH);
        forthXIMG = findViewById(R.id.img_memorize_x_FORTH);

        upperBar = (TextView) findViewById(R.id.memorize_select_upper_bar);
        upperBar.setText(" "+Word_main.curr_week+"주차");

        wordDatabase = FirebaseDatabase.getInstance();

        position =0;
        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();
        Part_list = new ArrayList<>();
        ans_num_list = new ArrayList<>();

        for(String key : MainActivity.wordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(MainActivity.wordMap.get(key).getKor());
            Part_list.add(MainActivity.wordMap.get(key).getPart());
        }
        if(Memorize_method_select.isRandom){        // 랜덤이면
            shuffleList();
        }
        if(Memorize_method_select.isAskingEng){     // 영어 맞추기면
            currShownIsENG = false;
        }
        else
            currShownIsENG = true;

        setRandAnsNum();
        System.out.println(ENG_list);
        System.out.println(KOR_list);
        setTextWordShown();

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_memorize_select_pre:
                    goPreWord();
                    break;
                case R.id.btn_memorize_select_next:
                    goNextWord();
                    break;
                case R.id.btn_memorize_select_quit:
                    onBackPressed();
                    break;
                case R.id.btn_memorize_select_bookmark:
                    getBookMark();
                    break;
                default:
                    answerSelected((TextView) view);

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
        System.out.println("pushed pre!");
        if(position==0){
            Toast.makeText(this,"단어 목록의 처음입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position--;
        setTextWordShown();
    }

    private void goNextWord(){
        System.out.println("pushed next!");
        if(position==ENG_list.size()-1){
            Toast.makeText(this,"단어 목록의 끝입니다!",Toast.LENGTH_LONG).show();
            return;
        }
        position++;
        setTextWordShown();
    }

    private void setTextWordShown(){
        // 상단 단어 바꾸기
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

        System.out.println(part);

        // 하단 선택지 바꾸기
        shuffleWord(Part_list.get(position));

        switch(ans_num_list.get(position)){
            case 1:
                System.out.println("case1");
                if(currShownIsENG) selection_1.setText(KOR_list.get(position));
                else selection_1.setText(ENG_list.get(position));
                break;
            case 2:
                System.out.println("case2");
                if(currShownIsENG) selection_2.setText(KOR_list.get(position));
                else selection_2.setText(ENG_list.get(position));
                break;
            case 3:
                System.out.println("case3");
                if(currShownIsENG) selection_3.setText(KOR_list.get(position));
                else selection_3.setText(ENG_list.get(position));
                break;
            case 4:
                System.out.println("case4");
                if(currShownIsENG) selection_4.setText(KOR_list.get(position));
                else selection_4.setText(ENG_list.get(position));
                break;
        }

        // 정답 표시 초기화
        firstCheckIMG.setVisibility(View.INVISIBLE);
        secondCheckIMG.setVisibility(View.INVISIBLE);
        thirdCheckIMG.setVisibility(View.INVISIBLE);
        forthCheckIMG.setVisibility(View.INVISIBLE);
        firstXIMG.setVisibility(View.INVISIBLE);
        secondXIMG.setVisibility(View.INVISIBLE);
        thirdXIMG.setVisibility(View.INVISIBLE);
        forthXIMG.setVisibility(View.INVISIBLE);
    }

    private void setRandAnsNum(){
        Random rand = new Random();
        int r=-1;
        for(String key : ENG_list){
            r = rand.nextInt(4)+1;
            System.out.println(r);
            ans_num_list.add((Integer)r);
        }
    }

    private void shuffleList(){
        Collections.shuffle(ENG_list);
        KOR_list.clear();
        Part_list.clear();
        for(String key : ENG_list){
            KOR_list.add(MainActivity.wordMap.get(key).getKor());
            Part_list.add(MainActivity.wordMap.get(key).getPart());
        }
    }

    private void shuffleWord(String part) {
        int num[] = new int[4];
        Random rand = new Random();

        System.out.println("part : " + part);

        if(Objects.equals(part, "noun")) {
            for(int i=0; i<4; i++) {
                num[i] = rand.nextInt(426);
                System.out.println("Num : " + num[i]);
                if(MainActivity.part_Eng_list.get(num[i]) == ENG_list.get(position)) {
                    i--;
                }
                for(int j=0; j<i; j++) {
                    if(num[i] == num[j]) {
                        i--;
                    }
                }
                System.out.println(num[i]);
            }
        }
        else if(Objects.equals(part, "verb")) {
            for(int i=0; i<4; i++) {
                num[i] = rand.nextInt(127) + 427;
                System.out.println("Num : " + num[i]);
                if(MainActivity.part_Eng_list.get(num[i]) == ENG_list.get(position)) {
                    i--;
                }
                for(int j=0; j<i; j++) {
                    if(num[i] == num[j]) {
                        i--;
                    }
                }
                System.out.println(num[i]);
            }
        }
        else if(Objects.equals(part, "adverb")) {
            for(int i=0; i<4; i++) {
                num[i] = rand.nextInt(97) + 755;
                System.out.println("Num : " + num[i]);
                if(MainActivity.part_Eng_list.get(num[i]) == ENG_list.get(position)) {
                    i--;
                }
                for(int j=0; j<i; j++) {
                    if(num[i] == num[j]) {
                        i--;
                    }
                }
                System.out.println(num[i]);
            }
        }
        else if(Objects.equals(part, "adjective")) {
            for(int i=0; i<4; i++) {
                num[i] = rand.nextInt(199) + 555;
                System.out.println("Num : " + num[i]);
                if(MainActivity.part_Eng_list.get(num[i]) == ENG_list.get(position)) {
                    i--;
                }
                for(int j=0; j<i; j++) {
                    if(num[i] == num[j]) {
                        i--;
                    }
                }
                System.out.println(num[i]);
            }
        }

        if(currShownIsENG) selection_1.setText(MainActivity.part_Kor_list.get(num[0]));
        else selection_1.setText(MainActivity.part_Eng_list.get(num[0]));
        if(currShownIsENG) selection_2.setText(MainActivity.part_Kor_list.get(num[1]));
        else selection_2.setText(MainActivity.part_Eng_list.get(num[1]));
        if(currShownIsENG) selection_3.setText(MainActivity.part_Kor_list.get(num[2]));
        else selection_3.setText(MainActivity.part_Eng_list.get(num[2]));
        if(currShownIsENG) selection_4.setText(MainActivity.part_Kor_list.get(num[3]));
        else selection_4.setText(MainActivity.part_Eng_list.get(num[3]));
    }

    private void answerSelected(TextView textView){
        if(currShownIsENG){
            if(textView.getText() == KOR_list.get(position)){       // 정답을 고른 경우
                System.out.println("정답!");
                switch(ans_num_list.get(position)){                 // 정답 표시
                    case 1:
                        firstCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        secondCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        thirdCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        forthCheckIMG.setVisibility(View.VISIBLE);
                        break;
                }
            }
            else{                                               // 오답을 고른 경우
                System.out.println("오답!");
                switch(textView.getId()){             // 오답 표시
                    case R.id.tv_memorize_select_FIRST:
                        firstXIMG.setVisibility(View.VISIBLE);
                        break;
                    case R.id.tv_memorize_select_SECOND:
                        secondXIMG.setVisibility(View.VISIBLE);
                        break;
                    case R.id.tv_memorize_select_THIRD:
                        thirdXIMG.setVisibility(View.VISIBLE);
                        break;
                    case R.id.tv_memorize_select_FORTH:
                        forthXIMG.setVisibility(View.VISIBLE);
                        break;
                }
                switch(ans_num_list.get(position)){             // 정답 표시
                    case 1:
                        firstCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        secondCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        thirdCheckIMG.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        forthCheckIMG.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    }
}