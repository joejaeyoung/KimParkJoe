package com.example.kimparkjoe;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.dynamic.FragmentWrapper;

import java.util.ArrayList;

public class Friend_main extends Fragment implements View.OnClickListener{

    private View view;
    private ImageButton requestButton;
    private ImageButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("친구 메인 전환!");
        view=inflater.inflate(R.layout.activity_friend_main,container,false);

        requestButton = view.findViewById(R.id.btn_friend_request);
        requestButton.setOnClickListener(this);
        addButton = view.findViewById(R.id.btn_friend_add);
        addButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn_friend_request:
                //FragmentDialog dialog = new FragmentWrapper()
                getActivity().startActivity(new Intent(getActivity(), Friend_request.class));
                return ;
            case R.id.btn_friend_add:
                getActivity().startActivity(new Intent(getActivity(), Friend_addFriend.class));
                break;
        }
    }

}