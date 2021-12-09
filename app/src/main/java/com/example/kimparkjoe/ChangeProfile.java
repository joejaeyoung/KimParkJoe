package com.example.kimparkjoe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ChangeProfile extends AppCompatActivity {

    private ImageView ChangeImage;
    private EditText ChangeName, ChangeMessage;
    private String NewImage, NewName, NewMessage;
    private int isGallery = 0;

    private final int REQUEST_CODE = 0;
    private Uri uri;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        storage = FirebaseStorage.getInstance();

        ChangeImage = findViewById(R.id.change_image);
        ChangeName = findViewById(R.id.et_change_name);
        ChangeMessage = findViewById(R.id.et_change_message);

        findViewById(R.id.btn_change_profile_save).setOnClickListener(onClickListener); // 저장 버튼
        findViewById(R.id.btn_change_profile_quit).setOnClickListener(onClickListener); //뒤로가기 버튼

        //개인정보 DB에서 받아오기
        Glide.with(this)
                .load(MainActivity.userImage)
                .into(ChangeImage);

        ChangeName.setText(MainActivity.userName);
        if(Objects.equals(MainActivity.userMessage, "null")) {
        }
        else {
            ChangeMessage.setText(MainActivity.userMessage);
        }

        ChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getApplicationContext(), v);
                getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.basics:
                                //기본 이미지로 설정
                                uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/memorymate-d8aa5.appspot.com/o/profile_sample.png?alt=media&token=b2428a1a-fcd2-4e9e-a229-13dcecd709ff");
                                Glide.with(getApplicationContext())
                                        .load(uri)
                                        .into(ChangeImage);

                                isGallery = 0;
                                break;

                            case R.id.gallery:
                                //갤러리에서 선택
                                isGallery = 1;
                                getImageToAlbum();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(isGallery == 1) {
                putImageToDB();
            }
            switch(v.getId()){
                case R.id.btn_change_profile_save:
                    saveChange();
                    onBackPressed();
                    break;
                case R.id.btn_change_profile_quit:
                    onBackPressed();
                    break;
            }
        }

    };

    //갤러리에서 이미지 가져오기
    private void getImageToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE) {
            System.out.println("1st Check");
            try {
                uri = data.getData();
                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(ChangeImage);
            } catch (Exception e) {                }
        }
        else if(resultCode == RESULT_CANCELED) {
        }
    }


    //popup창 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == 1) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //변경 데이터 저장 및 DB 업데이트트
    private void saveChange() {
        NewName = String.valueOf(ChangeName.getText());
        NewMessage = String.valueOf(ChangeMessage.getText());

        MainActivity.userName = NewName;
        MainActivity.userMessage = NewMessage;

        System.out.println(Setting_main.myImage);

        //개인정보 DB에서 받아오기
        ChangeImage = findViewById(R.id.change_image);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)ChangeImage.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Setting_main.myImage.setImageBitmap(bitmap);

        Setting_main.myName.setText(MainActivity.userName);
        if(Objects.equals(MainActivity.userMessage, "null")) {
        }
        else {
            Setting_main.myMessage.setText(MainActivity.userMessage);
        }
    }

    private void putImageToDB() {
        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child(MainActivity.userEmail).child(uri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(ChangeProfile.this, "사진이 정상적으로 업로드되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ChangeProfile.this, "사진이 정상적으로 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                FirebaseStorage newStorage = FirebaseStorage.getInstance();
                StorageReference newRef = newStorage.getReference();
                StorageReference pathRef = newRef.child(MainActivity.userEmail).child(uri.getLastPathSegment());
                pathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        NewImage = String.valueOf(uri);
                        MainActivity.userImage = NewImage;
                        System.out.println("finish ChangeProf");
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = database.getReference();

                        PersonItemList newProfile = new PersonItemList(MainActivity.userImage, MainActivity.userName, MainActivity.userMessage);
                        databaseReference.child("user").child(MainActivity.userEmail).child("profile").setValue(newProfile);
                    }
                });
            }
        });
    }
}