package com.example.kimparkjoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemorizeListAdapter extends RecyclerView.Adapter<MemorizeListAdapter.ViewHolder>{
    private ArrayList<String> ENG_Data = null;
    private ArrayList<String> KOR_Data = null;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MemorizeListAdapter(ArrayList<String> ENG_list, ArrayList<String> KOR_list){
        ENG_Data = ENG_list;
        KOR_Data = KOR_list;
    }

    // 뷰홀더
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ENG, tv_KOR;
        ImageButton btn_bookmark;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ENG = itemView.findViewById(R.id.tv_memorize_list_ENG);
            tv_KOR = itemView.findViewById(R.id.tv_memorize_list_KOR);
            btn_bookmark = itemView.findViewById(R.id.btn_memorize_list_bookmark);
        }
    }


    @NonNull
    @Override
    public MemorizeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_memorize_list, parent, false);
        MemorizeListAdapter.ViewHolder viewHolder = new MemorizeListAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MemorizeListAdapter.ViewHolder holder, int position) {
        String ENG_word = ENG_Data.get(position);
        holder.tv_ENG.setText(ENG_word);
        String KOR_word = KOR_Data.get(position);
        holder.tv_KOR.setText(KOR_word);
        //TODO : 즐겨찾기 IMAGE BUTTON 넣기
    }

    @Override
    public int getItemCount() {
        return ENG_Data.size();
    }

}
