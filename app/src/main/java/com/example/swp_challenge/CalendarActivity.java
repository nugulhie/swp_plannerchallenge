package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    Button img_cal;
    Button img_box;
    Button img_setting;
    CalendarView cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        img_cal = findViewById(R.id.img_cal_cal);
        img_box = findViewById(R.id.img_box_cal);
        img_setting = findViewById(R.id.img_setting_cal);

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        img_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }
}