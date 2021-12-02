package com.example.kimparkjoe;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {


    private ArrayList<String> NAME_data = null;
    private ArrayList<Number> PROFILE_data = null;


    public FriendRequestAdapter(ArrayList<String> NAME_list, ArrayList<Number> PROFILE_list) {
        NAME_data = NAME_list;
        PROFILE_data = PROFILE_list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_profile;
        ImageButton btn_accept, btn_refuse;

        public ViewHolder(@NonNull View view) {
            super(view);

            tv_name = itemView.findViewById(R.id.tv_item_friend_request_name);
            iv_profile = itemView.findViewById(R.id.iv_item_friend_request_profile);
            btn_accept = (ImageButton)itemView.findViewById(R.id.btn_item_accept_friend);
            btn_refuse = itemView.findViewById(R.id.btn_item_refuse_friend);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO : 친구 요청 수락 처리
                    System.out.println(tv_name.getText()+"요청 수락!");
                }
            });

            btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO : 친구 요청 거절 처리
                    System.out.println(tv_name.getText()+"요청 거절!");
                }
            });



        }
    }


    @NonNull
    @Override
    public FriendRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_friend_request, parent, false);
        FriendRequestAdapter.ViewHolder viewHolder = new FriendRequestAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.ViewHolder holder, int position) {
        String NAME = NAME_data.get(position);
        holder.tv_name.setText(NAME);
        //TODO : 프로필 이미지 골라서 넣기
        holder.iv_profile.setImageResource(R.drawable.profile_sample);
    }

    @Override
    public int getItemCount() {
        return NAME_data.size();
    }
}