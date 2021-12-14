package com.example.kimparkjoe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

interface OnRequestItemClickListener{
    void onAcceptClick(View view, int position); //추가
    void onRefuseClick(View view, int position);//삭제
}

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {

    //리스너 객체 참조를 어댑터에 전달 메서드
    private OnRequestItemClickListener rListener = null;
    public void setOnRequestItemClickListener(OnRequestItemClickListener listener) {
        this.rListener = listener;
    }

    private ArrayList<PersonItemList> FRIENDPROF_data;
    Context context;


    public FriendRequestAdapter(ArrayList<PersonItemList> FRIENDPROF_list, Context context) {
        FRIENDPROF_data = FRIENDPROF_list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_profile;
        ImageButton btn_accept, btn_refuse;

        public ViewHolder(@NonNull View view) {
            super(view);

            this.tv_name = view.findViewById(R.id.tv_item_friend_request_name);
            this.iv_profile = view.findViewById(R.id.iv_item_friend_request_profile);
            this.btn_accept = view.findViewById(R.id.btn_item_accept_friend);
            this.btn_refuse = view.findViewById(R.id.btn_item_refuse_friend);

            btn_accept.setClickable(true);
            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (rListener!=null){
                            rListener.onAcceptClick(view, position);
                        }
                    }
                    System.out.println(tv_name.getText()+"요청 수락!");
                }
            });

            btn_refuse.setClickable(true);
            btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (rListener!=null){
                            rListener.onRefuseClick(view, position);
                        }
                    }
                    System.out.println(tv_name.getText()+"요청 거절!");
                }
            });
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_request, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(Uri.parse(FRIENDPROF_data.get(position).getProfile()))
                .into(holder.iv_profile);
        holder.tv_name.setText(FRIENDPROF_data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return FRIENDPROF_data.size();
    }
}