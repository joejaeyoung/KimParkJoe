package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TreeMap;

public class WrongANS_bookmark_main extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<String> ENG_list, KOR_list;
    private Button btn_start;

    public static TreeMap<String, String> bookmarkWordMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_ans_bookmark_main);

        bookmarkWordMap = new TreeMap<>();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(MainActivity.uid).child("wrongWord");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookmarkWordMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    ANSItemList wrongWord = dataSnapshot.getValue(ANSItemList.class);

                    String Eng = wrongWord.getEng();
                    String Kor = wrongWord.getKor();

                    bookmarkWordMap.put(Eng, Kor);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });

        //TODO : DB에 정보 추가 후 아래 코드 삭제
        {
            bookmarkWordMap.put("temp_1.1","temp_1.2");
            bookmarkWordMap.put("temp_2.1","temp_2.2");
            bookmarkWordMap.put("temp_3.1","temp_3.2");
            bookmarkWordMap.put("temp_4.1","temp_4.2");
            bookmarkWordMap.put("temp_5.1","temp_5.2");
        }

        btn_start = (Button) findViewById(R.id.btn_wrong_ans_bookmark_start);
        btn_start.setOnClickListener(this);
        findViewById(R.id.btn_wrong_ans_bookmark_quit).setOnClickListener(this);

        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : bookmarkWordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(bookmarkWordMap.get(key));
        }
        RecyclerView recyclerView = findViewById(R.id.rv_wrong_ans_bookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WrongANSAdapter adapter = new WrongANSAdapter(ENG_list, KOR_list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_wrong_ans_bookmark_start:
                myStartActivity(WrongAnsTest.class);
                break;
            case R.id.btn_wrong_ans_bookmark_quit:
                finish();
                break;
        }
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}