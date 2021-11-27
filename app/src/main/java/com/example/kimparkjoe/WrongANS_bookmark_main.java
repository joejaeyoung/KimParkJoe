package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.TreeMap;

public class WrongANS_bookmark_main extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public static TreeMap<String, String> bookmarkWordMap = new TreeMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_ans_bookmark_main);

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
    }
}