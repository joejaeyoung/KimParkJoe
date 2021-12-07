package com.example.kimparkjoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

interface OnWrongItemClickListener{
    void onDeleteClick(View view, int position);//삭제
}

public class WrongANSAdapter extends RecyclerView.Adapter<WrongANSAdapter.ViewHolder>{
    //리스너 객체 참조를 어댑터에 전달 메서드
    private OnWrongItemClickListener wListener = null;
    public void setOnWrongtItemClickListener(OnWrongItemClickListener listener) {
        this.wListener = listener;
    }

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
        Button btn_delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ENG = itemView.findViewById(R.id.tv_wrong_ans_item_ENG);
            tv_KOR = itemView.findViewById(R.id.tv_wrong_ans_item_KOR);
            btn_delete = itemView.findViewById(R.id.btn_wrong_ans_item_delete);

            btn_delete.setClickable(true);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position!=RecyclerView.NO_POSITION){
                        if (wListener!=null){
                            ENG_Data.remove(getAdapterPosition());
                            KOR_Data.remove(getAdapterPosition());

                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), ENG_Data.size());

                            wListener.onDeleteClick(view, position);
                        }
                    }
                }
            });
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
    }

    @Override
    public int getItemCount() {
        return ENG_Data.size();
    }

}
