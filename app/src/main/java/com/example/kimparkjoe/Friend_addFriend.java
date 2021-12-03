package com.example.kimparkjoe;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Friend_addFriend extends AppCompatActivity implements View.OnClickListener {

    private EditText et_keyword;
    private TextView tv_name, tv_status;
    private ImageView iv_profile;
    private ImageButton btn_search, btn_send_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_add_friend);

        et_keyword = (EditText) findViewById(R.id.et_add_friend_keyword);
        tv_name = (TextView) findViewById(R.id.tv_add_friend_result_name);
        tv_status = (TextView) findViewById(R.id.tv_add_friend_result_status);
        iv_profile = (ImageView) findViewById(R.id.iv_add_friend_result_image);
        btn_search = (ImageButton) findViewById(R.id.btn_add_friend_search);
        btn_search.setOnClickListener(this);
        btn_send_request = (ImageButton) findViewById(R.id.btn_send_friend_request);
        btn_send_request.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_add_friend_search:
                getUserFromDB();
                break;
            case R.id.btn_send_friend_request:
                if(isNotEmpty()) {
                    sendFriendRequest();
                    Toast.makeText(this, "친구 요청을 보냈습니다!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // TODO : DB에서 et_keyword에 입력되어 있는 것으로 유저 정보를 불러옴
    private void getUserFromDB(){
        // 불러와서


        // 띄우기 까지
        {
            tv_name.setText("TestUser");
            tv_status.setText("TestStatus");
            iv_profile.setImageDrawable(getDrawable(R.drawable.profile_sample));
        }
    }

    // TODO : 위에서 찾은 유저에게 친구 요청을 보냄
    private void sendFriendRequest(){

    }

    private Boolean isNotEmpty() {
        if(tv_name.getText().equals("")){
            Toast.makeText(this, "먼저 친구요청을 보낼 사용자를 검색해 주세요!", Toast.LENGTH_SHORT).show();
            System.out.println("DID NOT SEARCHED");
            return false;
        }
        return true;
    }
}