package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.TreeMap;

public class Setting_main extends Fragment implements View.OnClickListener {

    private View view;
    private String[] rankingType = {"친구만","전체 사용자"};
    private AlertDialog rankTypeSelectDialog;
    public static String selectedRankType;
    public static TreeMap<String, Boolean> achieveMap = new TreeMap<>();    // 도전과제 <"도전과제명",달성여부>

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("세팅 메인 전환!");
        getAchievementsFromDB();
        view=inflater.inflate(R.layout.activity_setting_main,container,false);

        view.findViewById(R.id.tv_setting_main_rankTypeSelect).setOnClickListener(this);
        view.findViewById(R.id.Linear_setting_achieve).setOnClickListener(this);

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

    // <"도전과제명",달성여부>
    // --> DB 도전과제명에 1. <-이렇게 번호 붙여주세요 그래야 순서대로됨
    // --> 달성여부 true : 달성함
    private void getAchievementsFromDB(){
        achieveMap.clear();
        achieveMap.put("1.first achieve",false);
        achieveMap.put("2.second achieve",true);
        achieveMap.put("3.third achieve",false);
        achieveMap.put("4.forth achieve",true);
        achieveMap.put("5.fifth achieve",false);
    }
}