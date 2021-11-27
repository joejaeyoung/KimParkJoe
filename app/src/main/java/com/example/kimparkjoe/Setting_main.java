package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.TreeMap;

public class Setting_main extends Fragment implements View.OnClickListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<PersonItemList> arrayList = new ArrayList<>();
    private FirebaseDatabase userDatabase, achieveDatabase;
    private DatabaseReference databaseReference, achieveReference;

    private View view;
    private String[] rankingType = {"친구만","전체 사용자"};
    private AlertDialog rankTypeSelectDialog;
    public static String selectedRankType;
    public static TreeMap<String, Boolean> achieveMap = new TreeMap<>();    // 도전과제 <"도전과제명",달성여부>
    public int achieveNum = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("세팅 메인 전환!");
        getAchievementsFromDB();
        view=inflater.inflate(R.layout.activity_setting_main,container,false);

        view.findViewById(R.id.tv_setting_main_rankTypeSelect).setOnClickListener(this);
        view.findViewById(R.id.Linear_setting_achieve).setOnClickListener(this);

        recyclerView = (RecyclerView)view.findViewById(R.id.my_profile); // 아디 연결
        adapter = new PersonListAdapter(arrayList, getActivity());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrayList = new ArrayList<>(); // User 객체를 담을 어레이 리스트 (어댑터쪽으로)

        userDatabase = FirebaseDatabase.getInstance();
        databaseReference = userDatabase.getReference("user").child(MainActivity.uid).child("profile");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    PersonItemList myProfile = snapshot.getValue(PersonItemList.class); // 만들어뒀던 User 객체에 데이터를 담는다.

                    arrayList.add(myProfile); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        if(selectedRankType != null){
            ((TextView)view.findViewById(R.id.tv_setting_main_rankTypeSelect)).setText(selectedRankType);
        }

        rankTypeSelectDialog = new AlertDialog.Builder(this.getActivity())
                .setItems(rankingType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TextView)view.findViewById(R.id.tv_setting_main_rankTypeSelect)).setText(rankingType[i]);
                        selectedRankType = rankingType[i];
                    }
                })
                .setTitle("랭킹 표시 설정")
                .setNegativeButton("취소",null)
                .create();

        recyclerView.setAdapter(adapter); // 리사이클러뷰에 어댑터 연결

        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.tv_setting_main_rankTypeSelect:
                rankTypeSelectDialog.show();
                break;
            case R.id.Linear_setting_achieve:
                getActivity().startActivity(new Intent(getActivity(),Achievement_list.class));
                break;

        }
    }

    private void getAchievementsFromDB(){
        achieveDatabase = FirebaseDatabase.getInstance();

        achieveReference = achieveDatabase.getReference("user").child(MainActivity.uid).child("achievement");
        achieveReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                achieveMap.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) { // 반복문으로 데이터 List를 추출해냄
                    AchieveItemList achieve = dataSnapshot.getValue(AchieveItemList.class);

                    String Achieve = achieve.getAchievement();
                    Boolean Check = achieve.getCheck();

                    Log.d("TAG", "Achieve is " + Achieve + "Check is " + Check);

                    if(Check == true) {
                        achieveNum++;
                    }

                    achieveMap.put(Achieve, Check);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });

    }
}