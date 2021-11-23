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

import java.util.HashMap;
import java.util.Map;

public class Word_main extends Fragment implements View.OnClickListener {

    private View view;
    private Button btn_temp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("암기 메인 전환!");
        view=inflater.inflate(R.layout.activity_memorize_main,container,false);
        btn_temp = (Button) view.findViewById(R.id.temp_btn_to_method_select);
        btn_temp.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.temp_btn_to_method_select:
                ((MainActivity)MainActivity.context_main).putWordsFromDB(1);

                getActivity().startActivity(new Intent(getActivity(),Memorize_method_select.class));
                break;
        }
    }


}