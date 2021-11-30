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

public class WrongANSAdapter extends RecyclerView.Adapter<WrongANSAdapter.ViewHolder>{

    private ArrayList<String> ENG_Data = null;
    private ArrayList<String> KOR_Data = null;

    // 생성자에서 데이터 리스트 객체를 전달받음.
    WrongANSAdapter(ArrayList<String> ENG_list, ArrayList<String> KOR_list){
        ENG_Data = ENG_list;
        KOR_Data = KOR_list;
    }

    // 뷰홀더
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ENG, tv_KOR;
        ImageButton btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ENG = itemView.findViewById(R.id.tv_wrong_ans_item_ENG);
            tv_KOR = itemView.findViewById(R.id.tv_wrong_ans_item_KOR);
            btn_delete = itemView.findViewById(R.id.btn_wrong_ans_item_delete);
        }
    }


    @NonNull
    @Override
    public WrongANSAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_wrong_ans_list, parent, false);
        WrongANSAdapter.ViewHolder viewHolder = new WrongANSAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WrongANSAdapter.ViewHolder holder, int position) {
        String ENG_word = ENG_Data.get(position);
        holder.tv_ENG.setText(ENG_word);
        String KOR_word = KOR_Data.get(position);
        holder.tv_KOR.setText(KOR_word);
        //TODO : 삭제 IMAGE BUTTON 넣기
    }

    @Override
    public int getItemCount() {
        return ENG_Data.size();
    }

}
