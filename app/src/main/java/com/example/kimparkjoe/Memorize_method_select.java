package com.example.kimparkjoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Memorize_method_select extends AppCompatActivity {

    private String[] type = {"목록", "터치_뜻 맞추기","터치_영어 맞추기","선택_뜻맞추기","선택_영어맞추기"};
    private String[] method = {"랜덤", "순서대로"};
    private TextView  typeSelect, methodSelect;
    private AlertDialog typeSelectDialog, methodSelectDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memorize_method_select);

        typeSelect = (TextView)findViewById(R.id.tv_memorize_type_select);
        methodSelect = (TextView)findViewById(R.id.tv_memorize_method_select);

        findViewById(R.id.tv_memorize_type_select).setOnClickListener(onClickListener);    // 유형 선택 버튼
        findViewById(R.id.tv_memorize_method_select).setOnClickListener(onClickListener);  // 방식 선택 버튼
        findViewById(R.id.btn_memorize_start).setOnClickListener(onClickListener);          // 시작 버튼

        typeSelectDialog = new AlertDialog.Builder(Memorize_method_select.this)
                .setItems(type, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        typeSelect.setText(type[i]);
                    }
                })
                .setTitle("유형 선택")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

        methodSelectDialog = new AlertDialog.Builder(Memorize_method_select.this)
                .setItems(method, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        methodSelect.setText(type[i]);
                    }
                })
                .setTitle("방식 선택")
                .setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_memorize_start:
                    myStartActivity(Memorize_list.class);
                    finish();
                    break;
                case R.id.tv_memorize_type_select:

            }
        }

    };



    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}