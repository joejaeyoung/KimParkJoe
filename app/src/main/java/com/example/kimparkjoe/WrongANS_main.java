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

public class WrongANS_main extends Fragment {

    private View view;
    private Button bookmarkButton;
    private Button wrongButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("오답 메인 전환!");
        view=inflater.inflate(R.layout.activity_wrong_ans_main,container,false);

        bookmarkButton = view.findViewById(R.id.btn_wrong_bookmark);
        wrongButton = view.findViewById(R.id.btn_wrong_wrong);

        bookmarkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent1 = new Intent(getActivity(), WrongANS_bookmark_main.class);
                startActivity(intent1);
            }
        });

        wrongButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(), WrongANS_wrongquestion_main.class);
                startActivity(intent2);
            }
        });


        return view;
    }

}