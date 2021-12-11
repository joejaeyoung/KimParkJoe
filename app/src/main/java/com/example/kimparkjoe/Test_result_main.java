package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Test_result_main extends AppCompatActivity implements View.OnClickListener {

    private TextView result;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private Test_testing_main testing_activity;
    private Test_selectweeks_main test_selectweeks_activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_main);


        result = (TextView) findViewById(R.id.tv_test_result_score);
        result.setText(Test_testing_main.correct_ans_num+"/"+MainActivity.wordMap.size());

        findViewById(R.id.btn_test_result_yes).setOnClickListener(this);
        findViewById(R.id.btn_test_result_no).setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_test_result_yes:
                ((MainActivity)MainActivity.context_main).putWrongWordsToDB(Test_testing_main.WrongWord);
                putRankToDB();
                finish();
                testing_activity = (Test_testing_main) Test_testing_main.test_testing_activity;
                testing_activity.finish();
                test_selectweeks_activity = (Test_selectweeks_main) Test_selectweeks_main.test_select_weeks_activity;
                test_selectweeks_activity.finish();
                break;
            case R.id.btn_test_result_no:
                // 랭킹탭으로 나가기
                putRankToDB();
                finish();
                testing_activity = (Test_testing_main) Test_testing_main.test_testing_activity;
                testing_activity.finish();
                test_selectweeks_activity = (Test_selectweeks_main) Test_selectweeks_main.test_select_weeks_activity;
                test_selectweeks_activity.finish();
                break;
        }
    }

    //Ranking DB에 집어넣는 함수
    public void putRankToDB(){
        System.out.println("put Ranking words to DB...");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("rank").child(String.valueOf(Test_selectweeks_main.curr_week));

        RankingItemList rankingItemList = new RankingItemList(MainActivity.userName, Test_testing_main.correct_ans_num);

        databaseReference.child(MainActivity.userEmail).setValue(rankingItemList);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        //배경 클릭시 꺼지는거 막음
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막음
        return ;
    }

}