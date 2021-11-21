package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

//TODO : 단어 파일 확정 되면 리사이클러에 뿌리기 & 랜덤/순서대로 구현
public class Memorize_list extends AppCompatActivity implements View.OnClickListener {

    private Button hideEng, hideKOR;
    private View engHider, korHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_list);

        hideEng = (Button)findViewById(R.id.btn_memorize_hide_ENG);
        hideKOR = (Button)findViewById(R.id.btn_memorize_hide_KOR);
        hideEng.setOnClickListener(this);
        hideKOR.setOnClickListener(this);

        engHider = (View) findViewById(R.id.view_memorize_list_ENG_hider);
        korHider = (View) findViewById(R.id.view_memorize_list_KOR_hider);
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