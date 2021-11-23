package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.HashMap;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private Ranking_main ranking_main;
    private Word_main word_main;
    private Friend_main friend_main;
    private Setting_main setting_main;
    private WrongANS_main wrongANS_main;

    //TODO : static?
    public static HashMap<String, String> wordMap = new HashMap<String, String>();    // 단어 불러올 공간
    public static Context context_main;
    public static String testString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;

        //타이틀 바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //화면 전환
        NavigationBarView navigationBarView = findViewById(R.id.navigationView);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ranking_menu:
                        setFrag(0);
                        break;
                    case R.id.word_menu:
                        setFrag(1);
                        break;
                    case R.id.wrongANS_menu:
                        setFrag(2);
                        break;
                    case R.id.friend_menu:
                        setFrag(3);
                        break;
                    case R.id.setting_menu:
                        setFrag(4);
                        break;
                }
                return false;
            }
        });
        ranking_main = new Ranking_main();
        word_main = new Word_main();
        wrongANS_main = new WrongANS_main();
        friend_main = new Friend_main();
        setting_main = new Setting_main();
        setFrag(0);
    }

    private void setFrag(int n){
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch(n){
            case 0:
                ft.replace(R.id.frameLayout, ranking_main);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.frameLayout, word_main);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.frameLayout, wrongANS_main);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frameLayout, friend_main);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.frameLayout, setting_main);
                ft.commit();
                break;
        }
    }

    public void putWordsFromDB(Number week){
        //TODO : 파이어 베이스랑 연동해서 코드 수정
        wordMap.clear();
        {   // 단어 삽입
            wordMap.put("apple", "사과");
            wordMap.put("banana", "바나나");
            wordMap.put("cat", "고양이");
            wordMap.put("dog", "고양이");
            wordMap.put("lion", "사자");
            wordMap.put("list", "목록");
            wordMap.put("select", "선택");
            wordMap.put("memorize", "외우다");
            wordMap.put("center", "중심");
        }
    }
}