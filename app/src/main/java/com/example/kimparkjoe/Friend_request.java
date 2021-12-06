package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
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
    private FirebaseDatabase database, friendDatabase, acceptDatabase, numDatabase;
    private DatabaseReference databaseReference, friendReference, acceptReference, numRefernece;
    private FriendRequestAdapter adapter;

    private String fEmail, fName, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_request);


        database = FirebaseDatabase.getInstance();

        String path = "user/" + MainActivity.userEmail + "/request";

        databaseReference = database.getReference(path);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    FRIENDPROF_list.clear();
                    for (DataSnapshot friendInfo : dataSnapshot.getChildren()) {
                        FriendRequestItemList friendList = friendInfo.getValue(FriendRequestItemList.class);

                        fEmail = friendList.getEmail();

                        System.out.println(fEmail);

                        friendReference = friendDatabase.getInstance().getReference("user").child(fEmail).child("profile");
                        friendReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                                PersonItemList profile = snapshot.getValue(PersonItemList.class);

                                String image = profile.getProfile();
                                fName = profile.getName();

                                System.out.println(fName);

                                FRIENDPROF_list.add(profile);
                                adapter.notifyDataSetChanged();
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
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        RecyclerView recyclerView = findViewById(R.id.rv_received_request_container);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        adapter = new FriendRequestAdapter(FRIENDPROF_list, this);
        recyclerView.setAdapter(adapter);

        //클릭 이벤트 처리
        adapter.setOnItemClickListener(new OnItemClickListener() {
            String requestPath = "user/" + MainActivity.userEmail + "/request";
            @Override
            public void onAcceptClick(View view, int position) {
                int Num = MainActivity.friendNum++;

                databaseReference = database.getInstance().getReference();
                databaseReference.child(requestPath).child(fEmail).removeValue();

                acceptDatabase = FirebaseDatabase.getInstance();
                acceptReference = acceptDatabase.getReference("user").child(MainActivity.userEmail).child("friend");
                acceptReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FriendRequestItemList acceptInfo = new FriendRequestItemList(fEmail, fName);

                        acceptReference.child(fEmail).setValue(acceptInfo);

                        numRefernece = FirebaseDatabase.getInstance().getReference();
                        numRefernece.child("user").child(MainActivity.userEmail).child("friendNum").setValue(Num);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                    }
                });

                friendDatabase = FirebaseDatabase.getInstance();
                friendReference = friendDatabase.getReference("user").child(fEmail).child("friend");
                friendReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        FriendRequestItemList userInfo = new FriendRequestItemList(MainActivity.userEmail, userName);

                        friendReference.child(MainActivity.userEmail).setValue(userInfo);

                        numRefernece = FirebaseDatabase.getInstance().getReference();
                        numRefernece.child("user").child(fEmail).child("friendNum").setValue(Num);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                    }
                });

            }

            @Override
            public void onRefuseClick(View view, int position) {
                databaseReference = database.getInstance().getReference();
                databaseReference.child(requestPath).child(fEmail).removeValue();
            }
        });
    }

    private void isNotRequest() {
        Toast.makeText(this, "친구요청이 없습니다.", Toast.LENGTH_SHORT).show();
        System.out.println("NO REQUEST");
    }
}