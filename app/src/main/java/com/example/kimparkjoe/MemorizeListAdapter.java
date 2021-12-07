package com.example.kimparkjoe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

interface OnMemItemClickListener{
    void onBookMarkClick(View view, int position); //즐겨찾기 추가
    void onDelBookMarkClick(View view, int position); //즐겨찾기 삭제
}

public class MemorizeListAdapter extends RecyclerView.Adapter<MemorizeListAdapter.ViewHolder>{

    private ArrayList<String> ENG_Data = null;
    private ArrayList<String> KOR_Data = null;
    Button btn_bookmark;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    //리스너 객체 참조를 어댑터에 전달 메서드
    private OnMemItemClickListener mListener = null;
    public void setOnMemItemClickListener(OnMemItemClickListener listener) {
        this.mListener = listener;
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    MemorizeListAdapter(ArrayList<String> ENG_list, ArrayList<String> KOR_list){
        ENG_Data = ENG_list;
        KOR_Data = KOR_list;
    }

    // 뷰홀더
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ENG, tv_KOR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_ENG = itemView.findViewById(R.id.tv_memorize_list_ENG);
            tv_KOR = itemView.findViewById(R.id.tv_memorize_list_KOR);
            btn_bookmark = itemView.findViewById(R.id.btn_memorize_list_bookmark);

            // 북마크 추가 버튼
            btn_bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position!=RecyclerView.NO_POSITION){
                        if (mListener!=null){
                            if(btn_bookmark.isSelected() == false) {
                                btn_bookmark.setSelected(true);
                                mListener.onBookMarkClick(view, position);
                            }
                            else if(btn_bookmark.isSelected() == true) {
                                btn_bookmark.setSelected(false);
                                mListener.onDelBookMarkClick(view, position);
                            }
                        }
                    }
                }
            });
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
    public void onBindViewHolder(@NonNull MemorizeListAdapter.ViewHolder viewHolder, int position) {
        String ENG_word = ENG_Data.get(position);
        viewHolder.tv_ENG.setText(ENG_word);
        String KOR_word = KOR_Data.get(position);
        viewHolder.tv_KOR.setText(KOR_word);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("user").child(MainActivity.userEmail).child("Bookmark").child(ENG_word);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    btn_bookmark.setSelected(true);
                }
                else {
                    btn_bookmark.setSelected(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
            }
        });
    }

    @Override
    public int getItemCount() {
        return ENG_Data.size();
    }

}
