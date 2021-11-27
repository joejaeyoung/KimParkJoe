package com.example.kimparkjoe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_main extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EditText userName_edit, userEmail_edit, userPassword_edit, userPassCheck_edit;
    Button register_button;
    private boolean isCompleted = false;
    private BackPress_main backPressHandler = new BackPress_main(this);
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);

        userName_edit = findViewById(R.id.et_register_name);
        userEmail_edit = findViewById(R.id.et_register_email);
        userPassword_edit = findViewById(R.id.et_register_pass);
        userPassCheck_edit = findViewById(R.id.et_register_passcheck);
        register_button = findViewById(R.id.btn_register);

        findViewById(R.id.btn_register).setOnClickListener(onClickListener);
        findViewById(R.id.back_login).setOnClickListener(onClickListener);
        firebaseAuth = FirebaseAuth.getInstance();

    }

    View.OnClickListener onClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {

            switch(view.getId()){
                case R.id.btn_register:
                    signUp();
                    break;

                case R.id.back_login:
                    myStartActivity(Login_main.class);
                    finish();
                    break;
            }
        }
    };
    public void onBackPressed(){

        backPressHandler.onBackPressed("종료하려면 뒤로가기 버튼을 한번 더 누르세요", 3000);

    }

    private void signUp(){
        name = userName_edit.getText().toString().trim();
        String email = userEmail_edit.getText().toString().trim();
        String password = userPassword_edit.getText().toString().trim();
        String passwordCheck = userPassCheck_edit.getText().toString().trim();


        if(name.length() > 0 && email.length() >0 && password.length() >0 && passwordCheck.length()>0){

            if(password.length() < 6 || password.length() > 13){
                startToast("비밀번호를 6~13자리로 입력해 주십시오.");
            }
            else{
                isCompleted = true;
            }
        }
        if(isCompleted){
            if(password.equals(passwordCheck)){
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =  firebaseAuth.getCurrentUser();
                            startToast("회원가입 성공");
                            myStartActivity(RegisterAccept_main.class);
                            finish();
                        }
                        else{
                            if(task.getException() != null){
                                startToast("이미 등록된 이메일입니다.");
                            }
                        }
                    }
                });
            }else{
                startToast("비밀번호가 일치하지 않습니다.");
                userPassCheck_edit.requestFocus();
            }
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(Register_main.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
            userName_edit.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)){
            Toast.makeText(Register_main.this, "email을 입력하세요", Toast.LENGTH_SHORT).show();
            userEmail_edit.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(Register_main.this, "password를 입력하세요", Toast.LENGTH_SHORT).show();
            userPassword_edit.requestFocus();
            return;
        }else if(TextUtils.isEmpty(passwordCheck)){
            Toast.makeText(Register_main.this, "password를 한번 더 입력하세요", Toast.LENGTH_SHORT).show();
            userPassCheck_edit.requestFocus();
            return;
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