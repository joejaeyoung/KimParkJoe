package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class Ranking_main extends Fragment  {

    private View view;
    private Button testStartButton;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<RankingItemList> arrayList = new ArrayList<RankingItemList>();

    //View Pager
    private static final int NUM_PAGES = 16;
    private int pageNum=1;
    private Button nextButton;
    private Button prevButton;


    //Instance 반환 메소드
    public static Ranking_main newInstance(){
        return new Ranking_main();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("랭킹 메인 전환!");
        view = inflater.inflate(R.layout.activity_ranking_main,container,false);

        nextButton = view.findViewById(R.id.btn_next_ranking);
        prevButton = view.findViewById(R.id.btn_prev_ranking);

        recyclerView = (RecyclerView)view.findViewById(R.id.ranking_recycler);
        adapter = new RankingListAdapter(arrayList, getActivity());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                if((++pageNum)>16){
                    Toast toast = Toast.makeText(getActivity(), "마지막 랭킹입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        prevButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if((--pageNum)<1){
                    Toast toast = Toast.makeText(getActivity(), "이전 랭킹이 없습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }
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