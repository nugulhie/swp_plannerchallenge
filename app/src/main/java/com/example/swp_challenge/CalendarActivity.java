package com.example.swp_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;

import com.example.swp_challenge.CalendarDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    public ImageButton img_cal;
    public MaterialCalendarView cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        cal = findViewById(R.id.calendarView);
        cal.setSelectedDate(CalendarDay.today());
        cal.addDecorators(
                new CalendarDecorator.SundayDecorator(),
                new CalendarDecorator.SaturdayDecorator(),
                new CalendarDecorator.OneDayDecorator()
                //new CalendarDecorator.MySelectorDecorator(this)
        );


        img_cal = findViewById(R.id.img_cal_cal);
        // 밑으로 전부 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
    }


}