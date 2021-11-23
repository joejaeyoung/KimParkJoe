package com.example.kimparkjoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Memorize_method_select extends AppCompatActivity {

    private String[] type = {"목록", "터치_뜻 맞추기","터치_영어 맞추기","선택_뜻 맞추기","선택_영어 맞추기"};
    private String[] method = {"랜덤", "순서대로"};
    private TextView  typeSelect, methodSelect;
    private AlertDialog typeSelectDialog, methodSelectDialog;
    private String selectedType, selectedMethod;


    public static boolean isRandom;                                       // 암기 방식 랜덤 선택이면 true
    public boolean isRandom() { return isRandom; }                  // getter
    public void setRandom(boolean random) { isRandom = random; }    // setter


    public static boolean isAskingEng;    // 암기 유형 영어 맞추기면 true
    public boolean isEng() {
        return isAskingEng;
    }                        // getter
    public void setEng(boolean eng) {
        isAskingEng = eng;
    }                // setter

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
                //.setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();

        methodSelectDialog = new AlertDialog.Builder(Memorize_method_select.this)
                .setItems(method, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        methodSelect.setText(method[i]);
                    }
                })
                .setTitle("방식 선택")
                //.setPositiveButton("확인",null)
                .setNegativeButton("취소",null)
                .create();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_memorize_start:
                    checkStart();
                    //myStartActivity(Memorize_list.class);

                    //finish();
                    break;
                case R.id.tv_memorize_type_select:
                    typeSelectDialog.show();
                    break;
                case R.id.tv_memorize_method_select:
                    methodSelectDialog.show();
                    break;

            }
        }

    };

    private void checkStart(){
        selectedType = (String)typeSelect.getText();
        selectedMethod = (String)methodSelect.getText();
        System.out.println("type : "+selectedType);
        System.out.println("method : "+selectedMethod);
        if(selectedType.equals("유형 선택")||selectedMethod.equals("방식 선택")){
            Toast.makeText(this,"유형과 방식을 선택하세요.",Toast.LENGTH_LONG).show();
            return;
        }
        // 랜덤 or 순서대로 설정
        if(selectedMethod.equals("랜덤")){ setRandom(true); }
        else{ setRandom(false); }

        switch(selectedType){
            case "목록" :
                myStartActivity(Memorize_list.class);
                return;
            case "터치_뜻 맞추기" :
                setEng(false);
                myStartActivity(Memorize_touch.class);
                return;
            case "터치_영어 맞추기" :
                setEng(true);
                myStartActivity(Memorize_touch.class);
                return;
            case "선택_뜻 맞추기" :
                setEng(false);
                myStartActivity(Memorize_select.class);
                return;
            case "선택_영어 맞추기" :
                setEng(true);
                myStartActivity(Memorize_select.class);
                return;
        }

    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}