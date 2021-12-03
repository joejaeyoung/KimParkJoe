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

public class WrongANS_wrongquestion_main extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ArrayList<String> ENG_list, KOR_list;
    private Button btn_start;

    public static TreeMap<String, String> wrongWordMap = new TreeMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wong_ans_wrongquestion_main);


        //TODO : DB에 정보 추가 후 아래 코드 삭제
        {
            wrongWordMap.put("temp_1.1","temp_1.2");
            wrongWordMap.put("temp_2.1","temp_2.2");
            wrongWordMap.put("temp_3.1","temp_3.2");
            wrongWordMap.put("temp_4.1","temp_4.2");
            wrongWordMap.put("temp_5.1","temp_5.2");
        }

        btn_start = (Button) findViewById(R.id.btn_wrong_ans_wrong_question_start);
        btn_start.setOnClickListener(this);
        findViewById(R.id.btn_wrong_ans_wrong_question_quit).setOnClickListener(this);

        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : wrongWordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(wrongWordMap.get(key));
        }
        RecyclerView recyclerView = findViewById(R.id.rv_wrong_ans_wrong_question);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WrongANSAdapter adapter = new WrongANSAdapter(ENG_list, KOR_list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_wrong_ans_wrong_question_start:
                myStartActivity(WrongAnsTest.class);
                break;
            case R.id.btn_wrong_ans_wrong_question_quit:
                finish();
                break;
        }
    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}