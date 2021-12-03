package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TreeMap;

public class Friend_request extends AppCompatActivity {

    private ArrayList<PersonItemList> FRIENDPROF_list = new ArrayList<>();
    private ArrayList<Number> PROFILE_list = new ArrayList<>();
    private FirebaseDatabase database, friendDatabase;
    private DatabaseReference databaseReference, friendReference;

    public static TreeMap<PersonItemList, Number> friendRequestMap = new TreeMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("user").child(MainActivity.userEmail).child("friend").child("request");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    friendRequestMap.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    int num = 0;
                    for (DataSnapshot friendInfo : dataSnapshot.getChildren()) {
                        FriendRequestItemList friendList = friendInfo.getValue(FriendRequestItemList.class);

                        String friendEmail = friendList.getEmail();

                        friendReference = friendDatabase.getReference(friendEmail).child("profile");
                        friendReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                                PersonItemList profile = snapshot.getValue(PersonItemList.class);


                                FRIENDPROF_list.add(profile);
                                friendRequestMap.put(profile, (Number) num);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // 디비를 가져오던중 에러 발생 시
                                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                            }
                        });
                    }
                }
                else {
                    isNotRequest();
                    finish();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });


        //TODO : DB에서 친구 요청을 보낸 사용자의 프로필 사진 숫자와 이름을 불러옴
        /*{
            friendRequestMap.put("REQUESTER_1",1);
            friendRequestMap.put("REQUESTER_2",2);
            friendRequestMap.put("REQUESTER_3",3);
            friendRequestMap.put("REQUESTER_4",4);
            friendRequestMap.put("REQUESTER_5",5);
        }

         */

        for(PersonItemList key : friendRequestMap.keySet()){
            FRIENDPROF_list.add(key);
            PROFILE_list.add(friendRequestMap.get(key));
        }

        RecyclerView recyclerView = findViewById(R.id.rv_received_request_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FriendRequestAdapter adapter = new FriendRequestAdapter(FRIENDPROF_list, PROFILE_list);
        recyclerView.setAdapter(adapter);
    }

    private void isNotRequest() {
        Toast.makeText(this, "친구요청이 없습니다.", Toast.LENGTH_SHORT).show();
        System.out.println("NO REQUEST");
    }
}