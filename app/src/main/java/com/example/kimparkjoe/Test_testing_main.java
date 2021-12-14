package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;


public class Test_testing_main extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> ENG_list, KOR_list, User_ans_list, Part_list;
    private ArrayList<Integer> ans_num_list, User_ans_index;
    public static ArrayList<ANSItemList> WrongWord = new ArrayList<>();
    private TextView wordShown, selection_1, selection_2, selection_3, selection_4;
    private ImageView check_1, check_2, check_3, check_4;
    private int position;
    private boolean currShownIsEng; // true면 영어 띄움
    private TextView upperBar, totalNum;
    private TextView testTimer;
    private CountDownTimer countDownTimer;
    public static long finalExamTime;

    public static int correct_ans_num;
    public static Activity test_testing_activity;

    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_testing_main);
        testTimer = (TextView) findViewById(R.id.test_timer);

        countDownTimer();
        countDownTimer.start();

        test_testing_activity = Test_testing_main.this;

        wordShown = (TextView) findViewById(R.id.tv_test_select_word);
        wordShown.setOnClickListener(this);
        selection_1 = (TextView) findViewById(R.id.tv_test_select_FIRST);
        selection_1.setOnClickListener(this);
        selection_2 = (TextView) findViewById(R.id.tv_test_select_SECOND);
        selection_2.setOnClickListener(this);
        selection_3 = (TextView) findViewById(R.id.tv_test_select_THIRD);
        selection_3.setOnClickListener(this);
        selection_4 = (TextView) findViewById(R.id.tv_test_select_FORTH);
        selection_4.setOnClickListener(this);

        check_1 = (ImageView) findViewById(R.id.img_testing_check_FIRST);
        check_2 = (ImageView) findViewById(R.id.img_testing_check_SECOND);
        check_3 = (ImageView) findViewById(R.id.img_testing_check_THIRD);
        check_4 = (ImageView) findViewById(R.id.img_testing_check_FORTH);

        totalNum = (TextView) findViewById(R.id.tv_testing_totalNum);

        findViewById(R.id.btn_testing_select_pre).setOnClickListener(this);
        findViewById(R.id.btn_testing_select_next).setOnClickListener(this);
        findViewById(R.id.btn_testing_submit).setOnClickListener(this);

        //TODO : 선택한 선택지 이미지 표시
        upperBar = (TextView)findViewById(R.id.tv_testing_upper_bar);
        upperBar.setText(" "+Test_selectweeks_main.curr_week+"주차");

        position = 0;
        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();
        Part_list = new ArrayList<>();
        User_ans_list = new ArrayList<>();
        User_ans_index = new ArrayList<>();
        ans_num_list = new ArrayList<>();

        for(String key : MainActivity.wordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(MainActivity.wordMap.get(key).getKor());
            Part_list.add(MainActivity.wordMap.get(key).getPart());
            User_ans_list.add("temp_answer");
            User_ans_index.add(-1);
        }

        shuffleList();
        setRandAnsNum();
        setTextWordShown();
    }

    public void countDownTimer(){
        countDownTimer = new CountDownTimer(180000, 1000){
            public void onTick(long millisUntilFinished){
                finalExamTime = millisUntilFinished;
                testTimer.setText(String.valueOf(millisUntilFinished/1000));
            }
            public void onFinish(){
                submit();
            }
        };
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_testing_select_pre:
                goPreWord();
                break;
            case R.id.btn_testing_select_next:
                goNextWord();
                break;
            case R.id.btn_testing_submit:
                countDownTimer.cancel();
                countDownTimer.onFinish();
                break;
            //TODO : 즐찾이랑 뒤로가기 등 나머지 버튼 구현할 것
            default:
                answerSelected((TextView) view);
        }

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

    private void shuffleList(){
        Collections.shuffle(ENG_list);
        KOR_list.clear();
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

        selection_1.setText(MainActivity.part_Kor_list.get(num[0]));
        selection_2.setText(MainActivity.part_Kor_list.get(num[1]));
        selection_3.setText(MainActivity.part_Kor_list.get(num[2]));
        selection_4.setText(MainActivity.part_Kor_list.get(num[3]));
    }

    private void setRandAnsNum(){
        Random rand = new Random();
        int r=-1;
        for(String key : ENG_list){
            r = rand.nextInt(4)+1;
//            System.out.println(r);
            ans_num_list.add((Integer)r);
        }
    }

    private void setTextWordShown(){
        wordShown.setText(ENG_list.get(position));

        shuffleWord(Part_list.get(position));

        switch(ans_num_list.get(position)){
            case 1:
                selection_1.setText(KOR_list.get(position));
                break;
            case 2:
                selection_2.setText(KOR_list.get(position));
                break;
            case 3:
                selection_3.setText(KOR_list.get(position));
                break;
            case 4:
                selection_4.setText(KOR_list.get(position));
                break;
        }
        int real_position = position + 1;
        totalNum.setText(real_position+"/"+ENG_list.size());


        check_1.setVisibility(View.INVISIBLE);
        check_2.setVisibility(View.INVISIBLE);
        check_3.setVisibility(View.INVISIBLE);
        check_4.setVisibility(View.INVISIBLE);
        switch(User_ans_index.get(position)){
            case 1:
                check_1.setVisibility(View.VISIBLE);
                break;
            case 2:
                check_2.setVisibility(View.VISIBLE);
                break;
            case 3:
                check_3.setVisibility(View.VISIBLE);
                break;
            case 4:
                check_4.setVisibility(View.VISIBLE);
                break;
        }

        // TODO : 선택시 이미지 표시
    }

    private void answerSelected(TextView textView){
        check_1.setVisibility(View.INVISIBLE);
        check_2.setVisibility(View.INVISIBLE);
        check_3.setVisibility(View.INVISIBLE);
        check_4.setVisibility(View.INVISIBLE);

        switch(textView.getId()){
            case R.id.tv_test_select_FIRST:
                check_1.setVisibility(View.VISIBLE);
                User_ans_index.set(position,1);
                break;
            case R.id.tv_test_select_SECOND:
                check_2.setVisibility(View.VISIBLE);
                User_ans_index.set(position,2);
                break;
            case R.id.tv_test_select_THIRD:
                check_3.setVisibility(View.VISIBLE);
                User_ans_index.set(position,3);
                break;
            case R.id.tv_test_select_FORTH:
                check_4.setVisibility(View.VISIBLE);
                User_ans_index.set(position,4);
                break;
        }
        User_ans_list.set(position,(String) textView.getText());
        System.out.println("선택 : "+User_ans_list.get(position));
        System.out.println("정답 : "+KOR_list.get(position));
    }

    private void submit(){
        int pos = 0;
        correct_ans_num =0;

        WrongWord.clear();
        for(String key : KOR_list){
            if(User_ans_list.get(pos).equals(KOR_list.get(pos))){
                correct_ans_num++;
                System.out.println("정답!");
            }
            else {
                ANSItemList word = new ANSItemList(ENG_list.get(pos), KOR_list.get(pos));
                WrongWord.add(word);
                System.out.println("오답");
            }
            pos++;
        }

        myStartActivity(Test_result_main.class);
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.putExtra("FINALTIME",finalExamTime);
        startActivity(intent);
    }
}