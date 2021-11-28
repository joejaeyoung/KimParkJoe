package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Word_main extends Fragment implements View.OnClickListener {

    private View view;
//    private Button btn_temp;

    public static int curr_week;
    //private Button btn_week1, btn_week2, btn_week3, btn_week4, btn_week5, btn_week6, btn_week7, btn_week8, btn_week9, btn_week10;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("암기 메인 전환!");
        view=inflater.inflate(R.layout.activity_memorize_main,container,false);
//        btn_temp = (Button) view.findViewById(R.id.temp_btn_to_method_select);
//        btn_temp.setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_1).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_2).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_3).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_4).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_5).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_6).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_7).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_8).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_9).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_10).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_11).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_12).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_13).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_14).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_15).setOnClickListener(this);
        view.findViewById(R.id.tv_memorize_week_16).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.temp_btn_to_method_select:
//                ((MainActivity)MainActivity.context_main).putWordsFromDB(1);
//
//                getActivity().startActivity(new Intent(getActivity(),Memorize_method_select.class));
//                break;
            default:
                String weekString = (String) ((TextView)view).getText();
                String[] week_array = weekString.split(" ");
                curr_week = Integer.parseInt(week_array[1]);
                ((MainActivity)MainActivity.context_main).putWordsFromDB(curr_week);
                getActivity().startActivity(new Intent(getActivity(),Memorize_method_select.class));
        }
    }


}