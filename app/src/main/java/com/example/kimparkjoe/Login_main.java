package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_main extends AppCompatActivity {

    private String TAG = "Login_main";
    private FirebaseAuth firebaseAuth;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        if(SaveSharedPreference.getUserName(Login_main.this).length() == 0) {
            // call Login Activity
        }
        else {
            // Call Next Activity
            intent = new Intent(Login_main.this, MainActivity.class);
            intent.putExtra("STD_NUM", SaveSharedPreference.getUserName(this).toString());
            startActivity(intent);
            this.finish();
        }
        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.btn_login_register).setOnClickListener(onClickListener);

        firebaseAuth = firebaseAuth.getInstance();
    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_login_register:
                    myStartActivity(Register_main.class);
                    finish();
                    break;
                case R.id.btn_login:
                    login();
                    break;
            }
        }
    };

    private void login(){
        String email = ((EditText)findViewById(R.id.et_login_email)).getText().toString().trim();
        String password = ((EditText)findViewById(R.id.et_login_pass)).getText().toString().trim();
        CheckBox checkBox = findViewById(R.id.auto_login_check) ;

        //자동로그인
        if(checkBox.isChecked() == true) SaveSharedPreference.setUserName(Login_main.this, email);

        if(email.length() >0 && password.length() >0){
            email = MainActivity.decodeUserEmail(email);
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        startToast("로그인 성공");
                        myStartActivity(MainActivity.class);
                        finish();
                    }
                    else{
                        if(task.getException() != null){
                            startToast("Email 또는 비밀번호를 확인하세요");
                        }
                    }
                }
            });
        }
        else{
            startToast("Email 또는 비밀번호를 입력해주세요");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    private void myStartActivity(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}