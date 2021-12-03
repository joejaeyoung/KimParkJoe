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

public class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.ViewHolder> {

    private ArrayList<RankingItemList> arrayList;
    private Context context;

    //생성자로부터
    RankingListAdapter(ArrayList<RankingItemList> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
    }

    //뷰홀더
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_pager_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(holder.image_ranking);
        holder.name_ranking.setText(arrayList.get(position).getName());
        holder.score_ranking.setText(arrayList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_ranking;
        TextView name_ranking;
        TextView score_ranking;

        public ViewHolder (@NonNull View itemView){
            super(itemView);

            this.image_ranking = itemView.findViewById(R.id.ranking_image);
            this.name_ranking = itemView.findViewById(R.id.ranking_name);
            this.name_ranking = itemView.findViewById(R.id.ranking_score);
        }

    }
}
