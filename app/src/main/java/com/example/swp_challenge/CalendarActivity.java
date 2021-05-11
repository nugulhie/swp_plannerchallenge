package com.example.swp_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.recyclerPlanData;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//
public class CalendarActivity extends AppCompatActivity {
//
    Button btn_challHistory;
    ImageButton img_cal, btn_add_cal;
    ImageButton btn_menu;
    CalendarView mcalendarView;
    private PlanRecyclerAdapter adapterplan;
    TextView textdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat();



        String sortOrder = swp_database.PlanDB.PLAN_ID + " DESC";

        Cursor plancursor = db.query(
                swp_database.PlanDB.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List plan_id = new ArrayList<>();
        List plan_contents = new ArrayList<>();
        List plan_categorys = new ArrayList<>();
        List plan_dates = new ArrayList<>();

        while (plancursor.moveToNext()){
            String plan_date = plancursor.getString(
                    plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_DATE));
            plan_dates.add(plan_date);
            String plan_category = plancursor.getString(
                    plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_CATEGORY));
            plan_categorys.add(plan_category);
            String plan_content = plancursor.getString(
                    plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_CONTENTS));
            plan_contents.add(plan_content);
            int planItems = plancursor.getInt(
                    plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_ID));
            plan_id.add(planItems);
        }

        plancursor.close();


        init_recycler();
        getData_recycler(plan_contents, plan_categorys, plan_dates);

        /*cal = findViewById(R.id.calendarView_cal);
        cal.setSelectedDate(CalendarDay.today());
        cal.addDecorators(
                new CalendarDecorator.SundayDecorator(),
                new CalendarDecorator.SaturdayDecorator(),
                new CalendarDecorator.OneDayDecorator()
                //new CalendarDecorator.MySelectorDecorator(this)
        );*/

        img_cal = findViewById(R.id.button_calendar_cal);
        btn_add_cal = findViewById(R.id.button_addCalendar_cal);
        btn_challHistory = findViewById(R.id.button_challengeHistory_cal);

        //banner set date in korean
        textdate = findViewById(R.id.textView_dateOfToday);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));

        btn_menu = findViewById(R.id.button_menu_cal);

        // 밑으로 전부 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
                Log.d("zzz123", "onClick: calendarButton_calendar");
                finish();
            }
        });

        mcalendarView = findViewById(R.id.calendarView);
        mcalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {  //캘린더뷰 선택한 날짜에 해당하는
                btn_add_cal.setOnClickListener(new View.OnClickListener() {         //일정 팝업 액티비티 이동
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CalendarActivity.this, PlanPopupActivity.class);
                        intent.putExtra("year", year);
                        intent.putExtra("month", month+1);
                        intent.putExtra("day", dayOfMonth);
                        startActivity(intent);
                        Log.d("zzz123", "onClick: addPlanButton_calendar");
                    }
                });

                btn_challHistory.setOnClickListener(new View.OnClickListener() {    //도전과제 내역 액티비티 이동
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CalendarActivity.this, ChallhistoryActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onClick: challengeHistoryButton_calendar");
                    }
                });
            }
        });
        btn_add_cal.setOnClickListener(new View.OnClickListener() {         //날짜 선택 안하고 버튼 클릭시 알림
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: !unselected_date!");
            }
        });

        btn_challHistory.setOnClickListener(new View.OnClickListener() {    //날짜 선택 안하고 버튼 클릭시 알림
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: !unselected_date!");
            }
        });
    }

    public void onPopupMenuButtonClick(View button) {     //더보기 버튼 클릭 시 팝업메뉴 생성
        PopupMenu popupMenu = new PopupMenu(this, btn_menu);    //popup 메뉴 객체 생성
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());    //팝업메뉴xml 지정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu1:
                        Intent intent = new Intent(CalendarActivity.this, BoxActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: boxMenu_calendar");
                        finish();
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(CalendarActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: achieveMenu_calendar");
                        finish();
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: settingMenu_calendar");
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void init_recycler(){ //RecyclerView initiate method
        RecyclerView recyclerView_calendar = findViewById(R.id.recycler_calendar);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView_calendar.setLayoutManager(linearLayoutManager1);

        adapterplan = new PlanRecyclerAdapter();
        recyclerView_calendar.setAdapter(adapterplan);
    }

    private void getData_recycler(List plan_contents, List plan_categorys, List plan_dates){
        List<String> listPlanCategory = plan_categorys;
        List<String> listPlanContent = plan_contents;
        List<String> listplanDate = plan_dates;

        for(int i=0;i<listPlanCategory.size();i++){
            recyclerPlanData plandata = new recyclerPlanData();
            plandata.setTitle(listPlanCategory.get(i));
            plandata.setContent(listPlanContent.get(i));
            plandata.setDate(listplanDate.get(i));
            adapterplan.addItem(plandata);
        }

        adapterplan.notifyDataSetChanged();
    }


}