package com.example.swp_challenge;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.recyclerPlanData;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
import com.prolificinteractive.materialcalendarview.CalendarDay;

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
    Dialog plan_dialog;

    final String[] category = {"약속", "공부", "운동", "시험", "기타"};
    String category_item;
    int mYear, mMonth, mDay;
    PlannerController plan = PlannerController.getInstance();
    UserController user = UserController.getInstance();

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

        // ↓ 이거 주석 풀어야함!!
        /*cal = findViewById(R.id.calendarView);
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
                        showDialog();
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
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

        plan_dialog = new Dialog(CalendarActivity.this);       // 일정 다이얼로그 설정
        plan_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
        plan_dialog.setContentView(R.layout.activity_popup2);       //           "

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

    public void showDialog() {  //일정 다이얼로그 생성 함수
        plan_dialog.show();

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // ↓ 일정 카테고리 스피너 ↓
        Spinner spinner_category = plan_dialog.findViewById(R.id.spinner_categoryItem_plan);
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(categoryAdapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_item = (String) spinner_category.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // ↑ 일정 카테고리 스피너 ↑
        EditText content = plan_dialog.findViewById(R.id.content_plan);     //일정 본문
        ImageButton btn_delete = plan_dialog.findViewById(R.id.button_delete_plan); //삭제 버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   //데이터 삭제 메소드 추가할 것
                plan_dialog.dismiss();
                Toast.makeText(getApplicationContext(),"일정 삭제", Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: " + "delete_plan");
            }
        });
        Button btn_cancel = plan_dialog.findViewById(R.id.button_cancel_plan);  //취소 버튼
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_dialog.dismiss();
                Log.d("zzz123", "onClick: " + "cancel_plan");
            }
        });
        Button btn_submit = plan_dialog.findViewById(R.id.button_submit_plan);  //제출 버튼
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.length() > 0) {
                    plan.setPlan(content.getText().toString(), category_item);
                    dbHelper.insertPlan(plan.getPlanContents(),plan.getCategory(),plan.getDate(),mYear,mMonth,mDay);
                    Toast.makeText(getApplicationContext(), "날짜 : "+mYear+"년 "+mMonth+"월 "+mDay+"일 "
                            + ", 카테고리 : "+category_item + ", 내용 : "+ content.getText().toString(), Toast.LENGTH_SHORT).show();
                    plan_dialog.dismiss();
                    Log.d("zzz123", "onClick: " + "insert_plan");
                }
                else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    plan_dialog.dismiss();
                }
            }
        });
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