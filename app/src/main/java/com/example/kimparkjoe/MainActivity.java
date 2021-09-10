package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Ranking_main ranking_main;
    private Word_main word_main;
    private Friend_main friend_main;
    private Setting_main setting_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    case R.id.friend_menu:
                        setFrag(2);
                        break;
                    case R.id.Setting_menu:
                        setFrag(3);
                        break;
                }
                return false;
            }
        });
        ranking_main = new Ranking_main();
        word_main = new Word_main();
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
                ft.replace(R.id.frameLayout, friend_main);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.frameLayout, setting_main);
                ft.commit();
                break;
        }
    }
}