package com.example.kimparkjoe;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AchieveAdapter extends RecyclerView.Adapter<AchieveAdapter.ViewHolder> {

    private ArrayList<String> name_data=null;
    private ArrayList<Boolean> isCompleted_data=null;

    AchieveAdapter(ArrayList<String> achieveName, ArrayList<Boolean> isCompleted){
        name_data = achieveName;
        isCompleted_data = isCompleted;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        ImageView img_isCompleted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_achieve_name);
            img_isCompleted = itemView.findViewById(R.id.img_achieve_is_completed);
        }
    }
    @NonNull
    @Override
    public AchieveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_achievement_list,parent,false);
        AchieveAdapter.ViewHolder viewHolder = new AchieveAdapter.ViewHolder(view);

                return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AchieveAdapter.ViewHolder holder, int position) {
        String achieve_name = name_data.get(position);
        holder.tv_name.setText(achieve_name);

        boolean isCom = isCompleted_data.get(position);
        if(isCom){
            holder.img_isCompleted.setImageResource(R.drawable.icon_check_mark);
        }
        else
            holder.img_isCompleted.setImageResource(R.drawable.icon_x_mark);
    }

    @Override
    public int getItemCount() {
        return name_data.size();
    }
}

