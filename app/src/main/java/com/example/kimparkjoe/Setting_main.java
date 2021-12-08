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

import com.bumptech.glide.Glide;
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
import java.util.Objects;
import java.util.TreeMap;

public class Setting_main extends Fragment implements View.OnClickListener {

    private FirebaseDatabase database, achieveDatabase;
    private DatabaseReference databaseReference, achieveReference;

    private ImageView myImage;
    private TextView myName, myMessage, AchieveNum;
    private String Num;
    private View view;
    private String[] rankingType = {"친구만","전체 사용자"};
    private AlertDialog rankTypeSelectDialog;
    private static String selectedRankType;
    public static int RankTypeNum;
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

        myImage = (ImageView)view.findViewById(R.id.my_image);
        myName = (TextView)view.findViewById(R.id.my_name);
        myMessage = (TextView)view.findViewById(R.id.my_message);
        AchieveNum = (TextView)view.findViewById(R.id.achieve_num);

        //개인정보 DB에서 받아오기
        Glide.with(this)
                .load(MainActivity.userImage)
                .into(myImage);

        myName.setText(MainActivity.userName);
        if(Objects.equals(MainActivity.userMessage, "null")) {
        }
        else {
            myMessage.setText(MainActivity.userMessage);
        }

        AchieveNum.setText(Num);

        if(selectedRankType != null){
            ((TextView)view.findViewById(R.id.tv_setting_main_rankTypeSelect)).setText(selectedRankType);
        }

        rankTypeSelectDialog = new AlertDialog.Builder(this.getActivity())
                .setItems(rankingType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TextView)view.findViewById(R.id.tv_setting_main_rankTypeSelect)).setText(rankingType[i]);
                        selectedRankType = rankingType[i];
                        RankTypeNum = i;
                    }
                })
                .setTitle("랭킹 표시 설정")
                .setNegativeButton("취소",null)
                .create();

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

        achieveReference = achieveDatabase.getReference("user").child(MainActivity.userEmail).child("achievement");
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

        Num = Integer.toString(achieveNum) + " / 50";
        System.out.println(Num);
    }
}