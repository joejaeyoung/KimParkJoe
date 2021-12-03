package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.dynamic.FragmentWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Friend_main extends Fragment implements View.OnClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PersonItemList> arrayList = new ArrayList<>();
    private FirebaseDatabase database, friendDatabase;
    private DatabaseReference databaseReference, friendDatabaseReference;

    private View view;
    private ImageButton requestButton;
    private ImageButton addButton;

    public static int friendNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("친구 메인 전환!");

        friendDatabaseReference = friendDatabase.getInstance().getReference();

        friendDatabaseReference.child("user").child(MainActivity.userEmail).child("friendNum").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int number = dataSnapshot.getValue(int.class);
                friendNum = number;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });

        if(friendNum == 0) {
            view=inflater.inflate(R.layout.activity_friend_zero,container,false);

            requestButton = view.findViewById(R.id.btn_friend_zero_request);
            requestButton.setOnClickListener(this);
            addButton = view.findViewById(R.id.btn_friend_zero_add);
            addButton.setOnClickListener(this);
        }
        else {
            view=inflater.inflate(R.layout.activity_friend_main,container,false);

            requestButton = view.findViewById(R.id.btn_friend_request);
            requestButton.setOnClickListener(this);
            addButton = view.findViewById(R.id.btn_friend_add);
            addButton.setOnClickListener(this);

            recyclerView = (RecyclerView)view.findViewById(R.id.friend_recyclerView); // 아디 연결

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            arrayList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)

            databaseReference = database.getInstance().getReference();

            databaseReference.child("user").child(MainActivity.userEmail).child("friend").child("accept");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                        PersonItemList FriendList = dataSnapshot.getValue(PersonItemList.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                        arrayList.add(FriendList); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                    }
                    adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 디비를 가져오던중 에러 발생 시
                    Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                }
            });

            adapter = new PersonListAdapter(arrayList, getActivity());
            recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        }
        return view;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_friend_request:
            case R.id.btn_friend_zero_request:
                //FragmentDialog dialog = new FragmentWrapper()
                getActivity().startActivity(new Intent(getActivity(), Friend_request.class));
                break;
            case R.id.btn_friend_add:
            case R.id.btn_friend_zero_add:
                getActivity().startActivity(new Intent(getActivity(), Friend_addFriend.class));
                break;
        }
    }

}