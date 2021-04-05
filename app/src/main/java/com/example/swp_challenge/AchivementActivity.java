package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AchivementActivity extends AppCompatActivity {

    Button img_cal;
    Button img_box;
    Button img_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_achivement);

        img_setting = findViewById(R.id.img_setting_ach);
        img_box = findViewById(R.id.img_box_ach);
        img_cal = findViewById(R.id.img_cal_ach);
// 밑으로 전부 인텐트 넘기는 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchivementActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        img_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchivementActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchivementActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}
