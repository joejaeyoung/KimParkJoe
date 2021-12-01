package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Ranking_main extends Fragment  {

    private View view;
    private Button testStartButton;

    //View Pager
    private static final int NUM_PAGES = 16;
    private static int pageNum=1;
    private Button nextButton;
    private Button prevButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("랭킹 메인 전환!");
        view=inflater.inflate(R.layout.activity_ranking_main,container,false);

        nextButton= view.findViewById(R.id.btn_next_ranking);
        prevButton= view.findViewById(R.id.btn_prev_ranking);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Ranking_1weeks.class);
                startActivity(intent);
            }
        });


        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast toast = Toast.makeText(getActivity(), "이전 페이지가 없습니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });



        testStartButton = view.findViewById(R.id.btn_test_start);
        testStartButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), Test_selectweeks_main.class);
                startActivity(intent);

            }
        });


        return view;
    }


}