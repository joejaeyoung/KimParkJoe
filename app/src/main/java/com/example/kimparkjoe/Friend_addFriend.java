package com.example.kimparkjoe;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Friend_addFriend extends AppCompatActivity implements View.OnClickListener {

    private EditText et_keyword;
    private TextView tv_name, tv_status;
    private ImageView iv_profile;
    private ImageButton btn_search, btn_send_request;
    private String friendEmail, FriendImage, FriendName, FriendMessage, userName;
    private int isFriendExist = 0;
    private FirebaseDatabase database, addDatabase;
    private DatabaseReference databaseReference, addReference;

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

        userName = Setting_main.DBName;

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
        friendEmail = String.valueOf(et_keyword.getText());

        friendEmail = MainActivity.encodeUserEmail(friendEmail);

        addReference = addDatabase.getInstance().getReference().child("user").child(friendEmail).child("profile");
        addReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                if(dataSnapshot.exists()) {
                    PersonItemList friendInfo = dataSnapshot.getValue(PersonItemList.class);

                    FriendName = friendInfo.getName();
                    FriendMessage = friendInfo.getMessage();
                    FriendImage = friendInfo.getProfile();

                    isFriendExist = 1;
                }
                else {
                    isFriendExist = 0;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


        Glide.with(this)
                .load(FriendImage)
                .into(iv_profile);

        tv_name.setText(FriendName);
        if(Objects.equals(FriendMessage, "null")) {
            tv_status.setText("");
        }
        else {
            tv_status.setText(FriendMessage);
        }
    }

    // TODO : 위에서 찾은 유저에게 친구 요청을 보냄
    private void sendFriendRequest(){
        FriendRequestItemList userInfo = new FriendRequestItemList(MainActivity.userEmail, userName);
        System.out.println("User email is " + MainActivity.userEmail + " User name is " + userName);

        databaseReference = database.getInstance().getReference();

        databaseReference.child("user").child(friendEmail).child("friend").child("request").child(MainActivity.userEmail).setValue(userInfo);

        System.out.println("Complete!!");
    }

    private Boolean isNotEmpty() {
        if(tv_name.getText().equals("")){
            Toast.makeText(this, "먼저 친구요청을 보낼 사용자를 검색해 주세요!", Toast.LENGTH_SHORT).show();
            System.out.println("DID NOT SEARCHED");
            return false;
        }
        else if(isFriendExist == 0) {
            Toast.makeText(this, "존재하지 않는 사용자 입니다.", Toast.LENGTH_SHORT).show();
            System.out.println("DID NOT SEARCHED");
            return false;
        }
        return true;
    }
}