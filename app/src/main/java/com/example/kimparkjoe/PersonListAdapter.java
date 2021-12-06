package com.example.kimparkjoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

public class PersonListAdapter extends RecyclerView.Adapter<PersonListAdapter.ViewHolder>{

    private ArrayList<PersonItemList> arrayList;
    private Context context;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    PersonListAdapter(ArrayList<PersonItemList> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    // 뷰홀더
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    //position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arrayList.get(position).getProfile())
                .into(holder.image_friend);
        holder.name_friend.setText(arrayList.get(position).getName());
        if(Objects.equals(arrayList.get(position).getMessage(), "null")) {
        }
        else {
            holder.message_friend.setText(arrayList.get(position).getMessage());
        }
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0); // RecyclerView의 size return
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_friend;
        TextView name_friend;
        TextView message_friend;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);

            this.image_friend = itemView.findViewById(R.id.image_friend);
            this.name_friend = itemView.findViewById(R.id.name_friend);
            this.message_friend = itemView.findViewById(R.id.message_friend);
        }

    }

}
