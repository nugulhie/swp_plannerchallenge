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
import com.example.swp_challenge.CalendarDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    public Button img_cal;
    public Button img_box;
    public Button img_setting;
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