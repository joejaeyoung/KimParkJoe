package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;

    private Ranking_main ranking_main;
    private Word_main word_main;
    private Friend_main friend_main;
    private Setting_main setting_main;
    private WrongANS_main wrongANS_main;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public static String uid;
    public static TreeMap<String, String> wordMap = new TreeMap<String, String>();    // 단어 불러올 공간
    public static Context context_main;
    private MemorizeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context_main = this;

        //타이틀 바 없애기
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            uid = user.getUid();
        }

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

        //주차 path 설정
        String level = "level";
        String val = level.concat(Integer.toString((Integer) week));
        System.out.println(val);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("word").child(val);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wordMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    WordItemList word = dataSnapshot.getValue(WordItemList.class);

                    String Eng = word.getEng();
                    String Kor = word.getKor();
                    
                    Log.d("TAG", "Eng is " + Eng + "/Kor is " + Kor);
                    
                    wordMap.put(Eng, Kor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });
    }
}