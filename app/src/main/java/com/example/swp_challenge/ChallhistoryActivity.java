package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class ChallhistoryActivity extends AppCompatActivity {

    Button img_box;
    Button img_cal;
    Button img_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_challhistory);

        img_box = findViewById(R.id.img_box_history);
        img_cal = findViewById(R.id.img_cal_history);
        img_setting = findViewById(R.id.img_setting_history);

// 밑으로 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallhistoryActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        img_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallhistoryActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChallhistoryActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}