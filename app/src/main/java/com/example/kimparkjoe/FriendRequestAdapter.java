package com.example.kimparkjoe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {


    private ArrayList<PersonItemList> FRIENDPROF_data;
    private FirebaseDatabase database, acceptDatabase, friendDatabase, numDatabase;
    private DatabaseReference databaseReference, acceptReference, friendReference, numRefernece;

    private String friendEmail, friendName, userName;
    private FriendRequestItemList friendInfo;
    Context context;


    public FriendRequestAdapter(ArrayList<PersonItemList> FRIENDPROF_list, Context context) {
        FRIENDPROF_data = FRIENDPROF_list;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_profile;
        Button btn_accept, btn_refuse;

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
                    //TODO : 친구 요청 수락 처리

                    userName = Setting_main.DBName;
                    Friend_main.friendNum++;

                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("user").child(MainActivity.userEmail).child("request");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            friendInfo = snapshot.getValue(FriendRequestItemList.class);

                            friendEmail = snapshot.getValue(String.class);

                            databaseReference.child(friendEmail).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                        }
                    });

                    acceptDatabase = FirebaseDatabase.getInstance();
                    acceptReference = acceptDatabase.getReference("user").child(MainActivity.userEmail).child("friend");
                    acceptReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(friendEmail).setValue(friendInfo);

                            numRefernece = FirebaseDatabase.getInstance().getReference();
                            numRefernece.child("user").child(MainActivity.userEmail).child("friendNum").setValue(Friend_main.friendNum);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                        }
                    });

                    friendDatabase = FirebaseDatabase.getInstance();
                    friendReference = friendDatabase.getReference("user").child(friendEmail).child("friend");
                    friendReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FriendRequestItemList userInfo = new FriendRequestItemList(MainActivity.userEmail, userName);

                            acceptReference.child(MainActivity.userEmail).setValue(userInfo);

                            numRefernece = FirebaseDatabase.getInstance().getReference();
                            numRefernece.child("user").child(friendEmail).child("friendNum").setValue(Friend_main.friendNum);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                        }
                    });


                    System.out.println(tv_name.getText()+"요청 수락!");
                }
            });

            btn_refuse.setClickable(true);

            btn_refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO : 친구 요청 거절 처리
                    database = FirebaseDatabase.getInstance();
                    databaseReference = database.getReference("user").child(MainActivity.userEmail).child("request");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            friendInfo = snapshot.getValue(FriendRequestItemList.class);

                            friendEmail = snapshot.getValue(String.class);

                            databaseReference.child(friendEmail).removeValue();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                        }
                    });

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