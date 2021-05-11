package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChallhistoryActivity extends AppCompatActivity {
////
    ImageButton img_cal;
    TextView textdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_challhistory);
        //banner set date in korean
        textdate = findViewById(R.id.textView_dateOfToday);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));

        img_cal = findViewById(R.id.img_cal_history);

// 밑으로 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(ChallhistoryActivity.this, CalendarActivity.class);
                startActivity(intent);*/ //바로 전 레이아웃이 캘린더 레이아웃이라 나가기
                finish();
                Log.d("zzz123", "onClick: calendarButton_challHistory");
            }
        });
    }
}