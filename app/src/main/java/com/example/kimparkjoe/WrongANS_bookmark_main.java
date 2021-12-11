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
    private WrongANSAdapter adapter;

    public static TreeMap<String, String> bookmarkWordMap = new TreeMap<String, String >();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_ans_bookmark_main);

        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : bookmarkWordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(bookmarkWordMap.get(key));
        }

        RecyclerView recyclerView = findViewById(R.id.rv_wrong_ans_bookmark);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_start = (Button) findViewById(R.id.btn_wrong_ans_bookmark_start);
        btn_start.setOnClickListener(this);
        findViewById(R.id.btn_wrong_ans_bookmark_quit).setOnClickListener(this);

        adapter = new WrongANSAdapter(ENG_list, KOR_list);
        recyclerView.setAdapter(adapter);

        adapter.setOnWrongtItemClickListener(new OnWrongItemClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                databaseReference = database.getInstance().getReference();
                databaseReference.child("user").child(MainActivity.userEmail).child("Bookmark").child(ENG_list.get(position)).removeValue();
            }
        });

        putAchieveToDB();
    }

    private void putAchieveToDB() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(MainActivity.userEmail).child("achievement");
        if(WrongANS_bookmark_main.bookmarkWordMap.size() >= 100) {
            Boolean check = new Boolean(true);
            databaseReference.child("45").child("check").setValue(check);
        }
        else if(WrongANS_bookmark_main.bookmarkWordMap.size() >= 80) {
            Boolean check = new Boolean(true);
            databaseReference.child("44").child("check").setValue(check);
        }
        else if(WrongANS_bookmark_main.bookmarkWordMap.size() >= 50) {
            Boolean check = new Boolean(true);
            databaseReference.child("43").child("check").setValue(check);
        }
        else if(WrongANS_bookmark_main.bookmarkWordMap.size() >= 30) {
            Boolean check = new Boolean(true);
            databaseReference.child("42").child("check").setValue(check);
        }
        else if(WrongANS_bookmark_main.bookmarkWordMap.size() >= 10) {
            Boolean check = new Boolean(true);
            databaseReference.child("41").child("check").setValue(check);
        }
    };

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