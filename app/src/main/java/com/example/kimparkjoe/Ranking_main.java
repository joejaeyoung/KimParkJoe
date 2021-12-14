package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class Ranking_main extends Fragment  {

    private View view;
    private Button testStartButton;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<RankingItemList> arrayList = new ArrayList<>();

    //View Pager
    private static final int NUM_PAGES = 16;
    private int pageNum=1;
    private ImageButton nextButton;
    private ImageButton prevButton;

    private FirebaseDatabase database, friendDatabase, userDatabase;
    private DatabaseReference databaseReference, friendReference, userReference;
    private TextView rankingWeeks, myRank;

    private int my_rank = 1;


    //Instance 반환 메소드
    public static Ranking_main newInstance(){
        return new Ranking_main();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("랭킹 메인 전환!");
        Log.d("final_log","랭킹 탭 전환");
        view = inflater.inflate(R.layout.activity_ranking_main,container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.ranking_recycler);
        recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        rankingDB();

        myRank = view.findViewById(R.id.tv_my_ranking);
        nextButton = view.findViewById(R.id.btn_next_ranking);
        prevButton = view.findViewById(R.id.btn_prev_ranking);
        rankingWeeks = view.findViewById(R.id.rankign_weeks);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.d("final_log","다음 랭킹 버튼 클릭됨");
                if((pageNum)>15){
                    Toast toast = Toast.makeText(getActivity(), "마지막 랭킹입니다.", Toast.LENGTH_SHORT);
                    toast.show();
               }else {
                    pageNum++; //주차 저장
                    rankingWeeks.setText(pageNum+"단계 랭킹");
                    rankingDB();
                }
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("final_log","이번 랭킹 버튼 클릭됨");
                if((pageNum)<=1){
                    Toast toast = Toast.makeText(getActivity(), "이전 랭킹이 없습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    pageNum--; //주차 저장
                    rankingWeeks.setText(pageNum+"단계 랭킹");
                    rankingDB();
                }
            }
        });

        my_rank = RankingListAdapter.pos;
        System.out.println("나의 등수 " + my_rank + "등");
        myRank.setText("나의 등수 " + my_rank + "등");

        putAchieveToDB();

        return view;
    }

    private void rankingDB(){
        Log.d("final_log","랭킹 DB에서 불러오는 중...");
        System.out.println(pageNum + "주차");
        arrayList = new ArrayList<>();

        if(Setting_main.RankTypeNum == 0) {
            friendReference = friendDatabase.getInstance().getReference().child("user").child(MainActivity.userEmail).child("friend");
            friendReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot friendInfo : dataSnapshot.getChildren()) {
                        FriendRequestItemList friendList = friendInfo.getValue(FriendRequestItemList.class);

                        String friendEmail = friendList.getEmail();

                        Query scoreQuery = FirebaseDatabase.getInstance().getReference().child("rank").child(String.valueOf(pageNum)).child(friendEmail).orderByChild("score");
                        scoreQuery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                arrayList.clear();
                                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                                if(dataSnapshot.exists()) {
                                    RankingItemList rankingItemList = dataSnapshot.getValue(RankingItemList.class); // 만들어뒀던 User 객체에 데이터를 담는다.
                                    arrayList.add(rankingItemList);
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    System.out.println(friendEmail + "의 Ranking이 존재하지 않습니다.");
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // 디비를 가져오던중 에러 발생 시
                                Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                            }
                        });
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 디비를 가져오던중 에러 발생 시
                    Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                }
            });
        }
        else if(Setting_main.RankTypeNum == 1) {
            Query scoreQuery = FirebaseDatabase.getInstance().getReference().child("rank").child(String.valueOf(pageNum)).orderByChild("score");
            scoreQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    arrayList.clear();
                    // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                            RankingItemList rankingItemList = snapshot.getValue(RankingItemList.class); // 만들어뒀던 User 객체에 데이터를 담는다.

                            arrayList.add(rankingItemList);
                        }
                        adapter.notifyDataSetChanged();
                    }
                    else {
                        isNotExists();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 디비를 가져오던중 에러 발생 시
                    Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
                }
            });
        }

        Collections.sort(arrayList, new Comparator<RankingItemList>() {
            @Override
            public int compare(RankingItemList r1, RankingItemList r2) {
                if(r1.getScore() > r2.getScore()) {
                    return 1;
                }
                else if(r1.getScore() < r2.getScore()) {
                    return -1;
                }
                else {
                    return 0;
                }
            }
        });

        Collections.reverse(arrayList);

        adapter = new RankingListAdapter(arrayList, getContext());
        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        testStartButton = view.findViewById(R.id.btn_test_start);
        testStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), Test_selectweeks_main.class);
                startActivity(intent);

            }
        });
    }

    public void putAchieveToDB() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(MainActivity.userEmail).child("achievement");
        if(my_rank == 1) {
            Boolean check = new Boolean(true);
            databaseReference.child("29").child("check").setValue(check);
            databaseReference.child("30").child("check").setValue(check);
            databaseReference.child("31").child("check").setValue(check);
            databaseReference.child("32").child("check").setValue(check);
            databaseReference.child("33").child("check").setValue(check);
        }
        else if(my_rank <= 3) {
            Boolean check = new Boolean(true);
            databaseReference.child("30").child("check").setValue(check);
            databaseReference.child("31").child("check").setValue(check);
            databaseReference.child("32").child("check").setValue(check);
            databaseReference.child("33").child("check").setValue(check);
        }
        else if(my_rank <= 5) {
            Boolean check = new Boolean(true);
            databaseReference.child("31").child("check").setValue(check);
            databaseReference.child("32").child("check").setValue(check);
            databaseReference.child("33").child("check").setValue(check);
        }
        else if(my_rank <= 7) {
            Boolean check = new Boolean(true);
            databaseReference.child("32").child("check").setValue(check);
            databaseReference.child("33").child("check").setValue(check);
        }
        else if(my_rank <= 10) {
            Boolean check = new Boolean(true);
            databaseReference.child("33").child("check").setValue(check);
        }
    }

    private void isNotExists() {
        Toast.makeText(getContext(), "랭킹이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
        System.out.println("DID NOT RANK");
    }
}