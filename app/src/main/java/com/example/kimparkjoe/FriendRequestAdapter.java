package com.example.kimparkjoe;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder> {


    private ArrayList<PersonItemList> FRIENDPROF_data = null;
    private ArrayList<Number> PROFILE_data = null;
    private FirebaseDatabase database, acceptDatabase, numDatabase;
    private DatabaseReference databaseReference, acceptReference, numRefernece;

    private String friendEmail, friendName, userName;


    public FriendRequestAdapter(ArrayList<PersonItemList> FRIENDPROF_list, ArrayList<Number> PROFILE_list) {
        FRIENDPROF_data = FRIENDPROF_list;
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

            userName = Setting_main.DBName;

            databaseReference = database.getReference("user").child(MainActivity.userEmail).child("friend").child("request");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    FriendRequestItemList friendInfo = snapshot.getValue(FriendRequestItemList.class);

                    friendEmail = snapshot.getValue(String.class);
                    friendName = snapshot.getValue(String.class);

                    databaseReference.child(friendEmail).removeValue();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                }
            });

            Friend_main.friendNum++;

            btn_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO : 친구 요청 수락 처리
                    databaseReference = database.getReference("user").child(MainActivity.userEmail).child("friend").child("accept");

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            FriendRequestItemList friendInfo = new FriendRequestItemList(friendEmail, friendName);

                            databaseReference.child(friendEmail).setValue(friendInfo);

                            numRefernece = FirebaseDatabase.getInstance().getReference();
                            numRefernece.child("user").child(MainActivity.userEmail).child("friendNum").setValue(Friend_main.friendNum);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("TestActivity", String.valueOf(error.toException())); // 에러문 출력
                        }
                    });

                    acceptReference = acceptDatabase.getReference("user").child(friendEmail).child("friend").child("accept");

                    acceptReference.addValueEventListener(new ValueEventListener() {
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
        holder.tv_name.setText(FRIENDPROF_data.get(position).getName());
        Glide.with(holder.itemView)
                .load(Uri.parse(FRIENDPROF_data.get(position).getProfile()))
                .into(holder.iv_profile);
    }

    @Override
    public int getItemCount() {
        return FRIENDPROF_data.size();
    }
}