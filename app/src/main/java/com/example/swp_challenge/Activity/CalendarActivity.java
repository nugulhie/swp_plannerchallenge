package com.example.swp_challenge.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.CustomCalendarView.CustomCalendar;
import com.example.swp_challenge.CustomCalendarView.Helpers.Badge;
import com.example.swp_challenge.CustomCalendarView.Helpers.CalenderDate;
import com.example.swp_challenge.CustomCalendarView.Helpers.ClickInterface;
import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.SwipeController1;
import com.example.swp_challenge.dataController.SwipeControllerActions;
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

    Button btn_challHistory;
    ImageButton img_cal, btn_add_cal;
    ImageButton btn_menu;
    public static CustomCalendar mcalendarView;
    private PlanRecyclerAdapter adapterplan;
    TextView textdate;
    Dialog plan_dialog, plan_edit_dialog;
    public static int count=0;
    final String[] category = {"약속", "공부", "운동", "시험", "기타"};
    String category_item;
    public static int mYear, mMonth, mDay;
    public static List<Integer> plan_id = new ArrayList<>();
    public static List plan_contents = new ArrayList<>();
    public static List plan_categorys = new ArrayList<>();
    public static List plan_dates = new ArrayList<>();
    public static List plan_days = new ArrayList<>();
    public static int size_of_recycler;

    PlannerController plan = PlannerController.getInstance();
    UserController user = UserController.getInstance();
    public Context temp = CalendarActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);
        SimpleDateFormat daychanger = new SimpleDateFormat("dd");

        SimpleDateFormat monthchanger = new SimpleDateFormat("MM");
        SimpleDateFormat yearchanger = new SimpleDateFormat("yyyy");
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        img_cal = findViewById(R.id.button_calendar_cal);
        btn_add_cal = findViewById(R.id.button_addCalendar_cal);
        btn_challHistory = findViewById(R.id.button_challengeHistory_cal);
        textdate = findViewById(R.id.textView_dateOfToday);
        Date date = Calendar.getInstance().getTime();
        mDay = Integer.parseInt(daychanger.format(date));
        textdate.setText(korDate.format(date));
        btn_menu = findViewById(R.id.button_menu_cal);
        mcalendarView = (CustomCalendar)findViewById(R.id.calendarView);

        plan_edit_dialog = new Dialog(CalendarActivity.this);       // 일정 다이얼로그 설정
        plan_edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
        plan_edit_dialog.setContentView(R.layout.activity_popup2);       //           "

        loadDB(temp, Integer.parseInt(daychanger.format(date)), Integer.parseInt(monthchanger.format(date)), Integer.parseInt(yearchanger.format(date)));
        loadEvent(temp);
        init_recycler();
        getData_recycler(plan_contents, plan_categorys, plan_dates, plan_days);

        user.setContext(temp);


        // 밑으로 전부 인텐트 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mcalendarView.setOnClickDate(new ClickInterface() {
            @Override
            public void setDateClicked(CalenderDate date) {
                Toast.makeText(getApplicationContext(),date.getDay()+"일",Toast.LENGTH_SHORT).show();
                int dayOfMonth = date.getDay();
                int month = date.getMonth();
                int year = date.getYear();
                init_recycler();
                loadDB(temp, dayOfMonth, month, year);

                getData_recycler(plan_contents, plan_categorys, plan_dates, plan_days);

                btn_add_cal.setOnClickListener(new View.OnClickListener() {         //일정 팝업 액티비티 이동
                    @Override
                    public void onClick(View v) {
                        showDialog();
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;

                    }
                });

                btn_challHistory.setOnClickListener(new View.OnClickListener() {    //도전과제 내역 액티비티 이동
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CalendarActivity.this, ChallhistoryActivity.class);
                        intent.putExtra("selectday", dayOfMonth);
                        intent.putExtra("selectmonth", month);
                        intent.putExtra("selectyear", year);
                        startActivity(intent);
                    }
                });
            }

        });
        btn_add_cal.setOnClickListener(new View.OnClickListener() {         //날짜 선택 안하고 버튼 클릭시 알림
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_challHistory.setOnClickListener(new View.OnClickListener() {    //날짜 선택 안하고 버튼 클릭시 알림
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
            }
        });



        btn_add_cal.setOnClickListener(new View.OnClickListener() {         //날짜 선택 안하고 버튼 클릭시 알림
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });

                btn_challHistory.setOnClickListener(new View.OnClickListener() {    //날짜 선택 안하고 버튼 클릭시 알림
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    }
                });

                plan_dialog = new Dialog(CalendarActivity.this);       // 일정 다이얼로그 설정
                plan_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
                plan_dialog.setContentView(R.layout.activity_popup2);       //           "

            }

    private long backKeyPressedTime = 0;
    public void onBackPressed() {   //뒤로가기 두번 눌러서 앱 종료
        //super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Intent intent = getIntent();
                Intent intents = new Intent(CalendarActivity.this,MainActivity.class);
                startActivity(intents);
                finish();
            return;
        }
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
                        finish();
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(CalendarActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(CalendarActivity.this, SettingsActivity.class);
                        startActivity(intent);
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
        EditText content = plan_dialog.findViewById(R.id.content_plan);
        content.setText(""); //일정 본문
        Button btn_cancel = plan_dialog.findViewById(R.id.button_cancel_plan);  //취소 버튼
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_dialog.dismiss();
            }
        });
        Button btn_submit = plan_dialog.findViewById(R.id.button_submit_plan);  //제출 버튼
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.length() > 0) {
                    plan.setPlan(content.getText().toString(), category_item);
                    dbHelper.insertPlan(plan.getPlanContents(), plan.getCategory(), mYear, mMonth, mDay);
                    loadDB(temp, mDay, mMonth, mYear);
                    init_recycler();
                    getData_recycler(plan_contents, plan_categorys, plan_dates, plan_days);
                    loadEvent(temp);
                    plan_dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    plan_dialog.dismiss();
                }
            }
        });
    }
    public void showDialog1(){
        plan_edit_dialog.show();

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        Button submit = plan_edit_dialog.findViewById(R.id.button_submit_plan);
        Button cancel = plan_edit_dialog.findViewById(R.id.button_cancel_plan);
        EditText contents = plan_edit_dialog.findViewById(R.id.content_plan);
        contents.setText("");
        Spinner spinner_category = plan_edit_dialog.findViewById(R.id.spinner_categoryItem_plan);
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = contents.getText().toString();
                dbHelper.updatePlan(plan_id.get(count),temp,category_item);
                plan_edit_dialog.dismiss();
                loadDB(CalendarActivity.this, mDay, mMonth, mYear);
                init_recycler();
                getData_recycler(plan_contents, plan_categorys, plan_dates, plan_days);
                loadEvent(CalendarActivity.this);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_edit_dialog.dismiss();
            }
        });


    }
    private void init_recycler() { //RecyclerView initiate method
        RecyclerView recyclerView_calendar = findViewById(R.id.recycler_calendar);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        recyclerView_calendar.setLayoutManager(linearLayoutManager1);
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);

        adapterplan = new PlanRecyclerAdapter();
        recyclerView_calendar.setAdapter(adapterplan);
        SwipeController1 swipeController1 = new SwipeController1(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                count = position;
                adapterplan.removeItem(position);
                adapterplan.notifyItemRemoved(position);
                adapterplan.notifyItemRangeChanged(position, adapterplan.getItemCount());
                dbHelper.plandelete(plan_id.get(position));
                loadDB(temp, mDay, mMonth, mYear);
                init_recycler();
                getData_recycler(plan_contents, plan_categorys, plan_dates, plan_days);
                loadEvent(temp);
            }

            @Override
            public void onLeftClicked(int position) {   //리사이클러 수정
                showDialog1();
                count = position;

            }
        });
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(swipeController1);
        itemTouchHelper1.attachToRecyclerView(recyclerView_calendar);
        recyclerView_calendar.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController1.onDraw(c);

            }
        });
    }

    private void getData_recycler(List plan_contents, List plan_categorys, List plan_dates, List plan_days) {

        List<String> listPlanCategory = plan_categorys;
        List<String> listPlanContent = plan_contents;
        List<String> listplanDate = plan_dates;

        for (int i = 0; i < listPlanCategory.size(); i++) {
            recyclerPlanData plandata = new recyclerPlanData();
            plandata.setTitle(listPlanCategory.get(i));
            plandata.setContent(listPlanContent.get(i));
            plandata.setDate(listplanDate.get(i));

            adapterplan.addItem(plandata);
        }
        size_of_recycler = listPlanCategory.size();
        adapterplan.notifyDataSetChanged();

    }

    public static void loadDB(Context temp, int day, int mMonth, int mYear) {
        SimpleDateFormat dayChanger = new SimpleDateFormat("dd");
        SimpleDateFormat monthChanger = new SimpleDateFormat("MM");
        SimpleDateFormat yearChanger = new SimpleDateFormat("yyyy");
        Date date = Calendar.getInstance().getTime();
        List plan_id_temp = new ArrayList<>();
        List plan_contents_temp = new ArrayList<>();
        List plan_categorys_temp = new ArrayList<>();
        List plan_dates_temp = new ArrayList<>();
        List plan_days_temp = new ArrayList<>();
        //------------------------------------------------------------------------------------------
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = swp_database.PlanDB.PLAN_DAY + " = ?";
        String[] selectionArgs = {Integer.toString(day)};
        String sortOrder = swp_database.PlanDB.PLAN_ID + " DESC";

        Cursor plancursor = db.query(
                swp_database.PlanDB.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );


        while (plancursor.moveToNext()) {
            if (plancursor.getInt(plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_MONTH)) ==
                    mMonth && plancursor.getInt(plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_YEAR)) == mYear) {
                String plan_date = plancursor.getString(
                        plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_DATE));
                plan_dates_temp.add(plan_date);
                String plan_category = plancursor.getString(
                        plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_CATEGORY));
                plan_categorys_temp.add(plan_category);
                String plan_content = plancursor.getString(
                        plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_CONTENTS));
                plan_contents_temp.add(plan_content);
                int planItems = plancursor.getInt(
                        plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_ID));
                plan_id_temp.add(planItems);
                int plandays = plancursor.getInt(plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_DAY));
                plan_days_temp.add(plandays);
            }
        }

        plancursor.close();
        plan_id = plan_id_temp;
        plan_contents = plan_contents_temp;
        plan_categorys = plan_categorys_temp;
        plan_dates = plan_dates_temp;
        plan_days = plan_days_temp;
        //------------------------------------------------------------------------------------------

    }

    public static void loadEvent(Context temp){
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List plan_month= new ArrayList<>();
        List plan_day = new ArrayList<>();
        int[][] counts = new int[13][32];
        for(int j = 0; j<13;j++) {
            for (int i = 0; i < 32; i++) {
                counts[j][i] = 0;
            }
        }
        Cursor plancursor = db.query(
                swp_database.PlanDB.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
                );
        while (plancursor.moveToNext()) {

            int month = plancursor.getInt(plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_MONTH));
            plan_month.add(month);
            int day = plancursor.getInt(plancursor.getColumnIndexOrThrow(swp_database.PlanDB.PLAN_DAY));
            plan_day.add(day);
            counts[month][day]++;

        }
        List<Badge> badges = new ArrayList<>();
        for(int i =0 ;i<plan_day.size();i++){ // 각 날짜의 일정 개수가 몇개인지 생각해야함
            badges.add(new Badge(counts[(int)plan_month.get(i)][(int)plan_day.get(i)],(int)plan_day.get(i),(int)plan_month.get(i)));
        }
        plancursor.close();
        mcalendarView.setBadgeDateList(badges);
    }
}
