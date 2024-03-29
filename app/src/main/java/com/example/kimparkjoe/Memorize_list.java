package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;

public class Memorize_list extends AppCompatActivity implements View.OnClickListener {

    private Button hideEng, hideKOR;
    private View engHider, korHider;
    private ArrayList<WordItemList> arrayList;
    private ArrayList<String> ENG_list, KOR_list;
    private TextView upperBar;
    private FirebaseDatabase bookmarkDatabase;
    private DatabaseReference bookmarkReference;

    MainActivity mainActivity;
    Word_main wordMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("final_log","목록형 암기 전환");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_list);

        hideEng = (Button)findViewById(R.id.btn_memorize_hide_ENG);
        hideKOR = (Button)findViewById(R.id.btn_memorize_hide_KOR);
        hideEng.setOnClickListener(this);
        hideKOR.setOnClickListener(this);
        findViewById(R.id.btn_memorize_list_quit).setOnClickListener(this);

        engHider = (View) findViewById(R.id.view_memorize_list_ENG_hider);
        korHider = (View) findViewById(R.id.view_memorize_list_KOR_hider);

        upperBar = (TextView) findViewById(R.id.memorize_list_upper_bar);
        upperBar.setText(" "+Word_main.curr_week+"주차");

        ENG_list = new ArrayList<>();
        KOR_list = new ArrayList<>();

        for(String key : MainActivity.wordMap.keySet()){
            ENG_list.add(key);
            KOR_list.add(MainActivity.wordMap.get(key).getKor());
        }
        if(Memorize_method_select.isRandom){
            shuffleList();
        }
        RecyclerView recyclerView = findViewById(R.id.rv_memorize_list_content );
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MemorizeListAdapter adapter = new MemorizeListAdapter(ENG_list, KOR_list);
        recyclerView.setAdapter(adapter);

        adapter.setOnMemItemClickListener(new OnMemItemClickListener() {
            @Override
            public void onBookMarkClick(View view, int position) {
                ((MainActivity)MainActivity.context_main).putBookmarkWordsToDB(ENG_list.get(position), KOR_list.get(position));
            }

            @Override
            public void onDelBookMarkClick(View view, int position) {
                bookmarkReference = bookmarkDatabase.getInstance().getReference();
                bookmarkReference.child("user").child(MainActivity.userEmail).child("Bookmark").removeValue();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_memorize_hide_ENG:
                toggleENGVisibility();
                break;
            case R.id.btn_memorize_hide_KOR:
                toggleKORVisibility();
                break;
            case R.id.btn_memorize_list_quit:
                onBackPressed();
                break;
        }
    }

    private void shuffleList(){
        Collections.shuffle(ENG_list);
        KOR_list.clear();
        for(String key : ENG_list){
            KOR_list.add(MainActivity.wordMap.get(key).getKor());
        }
    }

    private void toggleENGVisibility(){
        if(engHider.getVisibility() == View.VISIBLE){
            engHider.setVisibility(View.INVISIBLE);
            hideEng.setText("영어 가리기");
        }
        else{
            engHider.setVisibility(View.VISIBLE);
            hideEng.setText("영어 보이기");
        }
    }

    private void toggleKORVisibility(){
        if(korHider.getVisibility() == View.VISIBLE){
            korHider.setVisibility(View.INVISIBLE);
            hideKOR.setText("뜻 가리기");
        }
        else{
            korHider.setVisibility(View.VISIBLE);
            hideKOR.setText("뜻 보이기");
        }
    }
}