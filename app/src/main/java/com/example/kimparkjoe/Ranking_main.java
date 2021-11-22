package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

public class Ranking_main extends Fragment  {

    private View view;
    private Button testStartButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("랭킹 메인 전환!");
        view=inflater.inflate(R.layout.activity_ranking_main,container,false);

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