package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//
public class CalendarActivity extends AppCompatActivity {
//
    Button btn_challHistory;
    ImageButton img_cal, btn_add_cal;
    String menu_item;
    ImageButton btn_menu;
    Spinner spinner;
    public MaterialCalendarView cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        /*cal = findViewById(R.id.calendarView_cal);
        cal.setSelectedDate(CalendarDay.today());
        cal.addDecorators(
                new CalendarDecorator.SundayDecorator(),
                new CalendarDecorator.SaturdayDecorator(),
                new CalendarDecorator.OneDayDecorator()
                //new CalendarDecorator.MySelectorDecorator(this)
        );*/

        img_cal = findViewById(R.id.img_cal_cal);
        btn_add_cal = findViewById(R.id.btn_addCal_cal);
        btn_challHistory = findViewById(R.id.btn_challHistory);

        //@@@@@메뉴 스피너@@@@@@@//
        btn_menu = findViewById(R.id.btn_more_cal);
        final String[] menu = {"상자", "칭호", "설정"};  //메뉴 아이템 항목
        spinner = findViewById(R.id.spinner_cal);  //스피너 초기화
        ArrayAdapter menuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menu);  //menu 어댑터 생성
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(menuAdapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menu_item = (String) spinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {    //선택한 드롭다운에서 버튼 선택 시 - 액티비티 이동//
            @Override
            public void onClick(View v) {
                switch(menu_item) {
                    case "상자":
                        Intent intent = new Intent(CalendarActivity.this, BoxActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "칭호":
                        intent = new Intent(CalendarActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "설정":
                        intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });
        //@@@@@메뉴 스피너 끝@@@@@2//

        // 밑으로 전부 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_add_cal.setOnClickListener(new View.OnClickListener() { //일정 팝업 액티비티 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, PlanPopupActivity.class);
                startActivity(intent);
            }
        });

        btn_challHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, ChallhistoryActivity.class);
                startActivity(intent);
            }
        });
    }


}