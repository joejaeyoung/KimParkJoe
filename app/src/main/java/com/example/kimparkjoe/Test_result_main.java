package com.example.kimparkjoe;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Test_result_main extends AppCompatActivity {

    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result_main);

        result = (TextView) findViewById(R.id.tv_test_result_score);

        result.setText(Test_testing_main.correct_ans_num+"/"+MainActivity.wordMap.size());
    }
}