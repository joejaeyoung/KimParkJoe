package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Test_result_main extends AppCompatActivity implements View.OnClickListener {

    private TextView result;

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
                Toast.makeText(this,"아직 미구현!!",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_test_result_no:
                // 랭킹탭으로 나가기
                finish();
                Test_testing_main testing_activity = (Test_testing_main) Test_testing_main.test_testing_activity;
                testing_activity.finish();
                Test_selectweeks_main test_selectweeks_activity = (Test_selectweeks_main) Test_selectweeks_main.test_select_weeks_activity;
                test_selectweeks_activity.finish();
                break;
        }
    }
}