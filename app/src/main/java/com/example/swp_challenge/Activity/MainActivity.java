package com.example.swp_challenge.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.ChallengeController;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserAchivementController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.ChallengeRecyclerAdapter;
import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.SwipeController;
import com.example.swp_challenge.dataController.SwipeController1;
import com.example.swp_challenge.dataController.SwipeControllerActions;
import com.example.swp_challenge.dataController.recyclerChallengeData;
import com.example.swp_challenge.dataController.recyclerPlanData;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//
public class MainActivity extends AppCompatActivity {
    UserController user = UserController.getInstance();
    ChallengeController challenge = ChallengeController.getInstance();
    //
    private PlanRecyclerAdapter adapterplan;
    private ChallengeRecyclerAdapter adapterchallenge;
    public Button button_Add_challenge, button_getReward;
    ImageButton btn_menu, img_cal;
    ImageView img_currentimg;
    String d1, d2, tempD, hour, minute;
    Date date1, date2, tempDate;
    TextView textdate, textAchive;
    public static int count = 0, selectDay1, selectDay2, selectMonth1, selectMonth2, selectYear1, selectYear2;
    Dialog challenge_dialog, challenge_edit_dialog, plan_dialog;
    public static Context temp;
    public String achive, username;
    String category_item;
    final String[] category = {"약속", "공부", "운동", "시험", "기타"};
    public int keys, achiveimg;
    public static List plan_id = new ArrayList<>();
    public static List plan_contents = new ArrayList<>();
    public static List plan_categorys = new ArrayList<>();
    public static List plan_dates = new ArrayList<>();
    public static List challenge_id = new ArrayList<>();
    public static List challenge_contents = new ArrayList<>();
    public static List challenge_ratings = new ArrayList<>();
    public static List challenge_dates = new ArrayList<>();
    public static List<Integer> challenge_pass = new ArrayList<>();


    private long backKeyPressedTime = 0;    //마지막으로 뒤로가기 눌렀던 시간 저장
    private Toast toast;    //첫번째 뒤로가기 버튼 누를때 표시

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        Date date = Calendar.getInstance().getTime();
        textdate = findViewById(R.id.textView_dateOfToday);
        button_Add_challenge = findViewById(R.id.button_addChall_main);
        img_cal = findViewById(R.id.button_calendar_main);
        btn_menu = findViewById(R.id.button_menu_main);
        textAchive = findViewById(R.id.textView_selectedAchieve);
        button_getReward = findViewById(R.id.button_getreward);
        img_currentimg = findViewById(R.id.imageView_selectedAchieve);
        achive = "칭호없음";
        textdate.setText(korDate.format(date));


        challenge_dialog = new Dialog(MainActivity.this);       // 도전과제 다이얼로그 설정
        challenge_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
        challenge_dialog.setContentView(R.layout.activity_popup);       //           "

        challenge_edit_dialog = new Dialog(MainActivity.this);  // 도전과제 편집창
        challenge_edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        challenge_edit_dialog.setContentView(R.layout.activity_challengepopup_edit);

        plan_dialog = new Dialog(MainActivity.this);
        plan_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        plan_dialog.setContentView(R.layout.activity_popup2);

        temp = MainActivity.this;
        loadDB(temp);
        textAchive.setText("< " + achive + " >");
        img_currentimg.setImageResource(achiveimg);
        init_recycler();
        getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);

        //------------------------------------------------------------------------------------------------------

        Log.d("159753", "onCreate: main" + user.getCnt_key());

        button_getReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkChellenge();
            }
        });
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
                Log.d("zzz123", "onClick:" + "calendarButton_main");
            }
        });
        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                Log.d("zzz123", "onClick:" + "addChallengeButton_main");
            }
        });

    }

    public void showDialog3() {
        plan_dialog.show();

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        Button submit = plan_dialog.findViewById(R.id.button_submit_plan);
        Button cancel = plan_dialog.findViewById(R.id.button_cancel_plan);
        EditText contents = plan_dialog.findViewById(R.id.content_plan);
        contents.setText("");
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

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = contents.getText().toString();
                dbHelper.updatePlan(plan_contents.get(count).toString(), temp, category_item);
                plan_dialog.dismiss();
                init_recycler();
                loadDB(MainActivity.this);
                getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);
                Toast.makeText(getApplicationContext(), "일정 수정", Toast.LENGTH_SHORT).show();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plan_dialog.dismiss();
            }
        });


    }

    public void showDialog() {  //도전과제 다이얼로그 생성 함수
        challenge_dialog.show();

        //중요도, 내용, 날짜1, 날짜2, 시각
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        RatingBar ratingBar = challenge_dialog.findViewById(R.id.ratingBar_challenge);  //중요도
        ratingBar.setRating(2.0f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  // 도전과제 중요도 별점
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1); //중요도 최소 별 1개로 설정.
                }
            }
        });
        EditText content = challenge_dialog.findViewById(R.id.content_challenge);   //내용
        content.setText(null);
        ((TextView) challenge_dialog.findViewById(R.id.textView_date1_challenge)).setText("date");
        ((TextView) challenge_dialog.findViewById(R.id.textView_date2_challenge)).setText("date");
        ImageButton btn_date1 = challenge_dialog.findViewById(R.id.button_date1_chall); //날짜버튼1
        btn_date1.setOnClickListener(new View.OnClickListener() {   // 달력버튼1 선택시 달력1 dialogFragment 생성 이벤트
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                Log.d("zzz123", "onClick: datePicker1_challenge");
            }
        });
        ImageButton btn_date2 = challenge_dialog.findViewById(R.id.button_date2_chall); //날짜버튼2
        btn_date2.setOnClickListener(new View.OnClickListener() {  // 달력버튼2 선택시 달력2 dialogFragment 생성 이벤트
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker2Fragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                Log.d("zzz123", "onClick: datePicker2_challenge");
            }
        });
        Button btn_cancel = challenge_dialog.findViewById(R.id.button_cancel_chall);    //취소 버튼
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challenge_dialog.dismiss();
                Log.d("zzz123", "onClick: cancel_challenge");
            }
        });
        Button btn_submit = challenge_dialog.findViewById(R.id.button_submit_chall);    //제출 버튼
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.length() > 0 & d1 != null & d2 != null) {
                    // ↓ date 크기 비교해서 순서 바꿔주기 ↓
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date1 = null;
                    date2 = null;
                    try {
                        date1 = dateFormat.parse(d1);
                        date2 = dateFormat.parse(d2);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int compare = date1.compareTo(date2);
                    if (compare > 0) {
                        tempDate = date1;   //date 형 바꾸기
                        date1 = date2;
                        date2 = tempDate;
                        tempD = d1;         //str 형 바꾸기
                        d1 = d2;
                        d2 = tempD;
                    }
                    // ↑ date 크기 비교해서 순서 바꿔주기 ↑
                    challenge.setChallenge(ratingBar.getRating(), content.getText().toString());
                    if (selectDay1 > selectDay2) {
                        int tempday = selectDay1;
                        selectDay1 = selectDay2;
                        selectDay2 = tempday;
                    }
                    if (selectMonth1 > selectMonth2) {
                        int tempmonth = selectMonth1;
                        selectMonth1 = selectMonth2;
                        selectMonth2 = tempmonth;
                    }
                    if (selectYear1 > selectYear2) {
                        int tempyear = selectYear1;
                        selectYear1 = selectYear2;
                        selectYear2 = tempyear;
                    }

                    dbHelper.insertChallenge(challenge.getContents(), challenge.getDate(), challenge.getRating(), selectDay1, selectDay2, selectMonth1, selectMonth2, selectYear1, selectYear2);
                    loadDB(temp);
                    init_recycler();
                    getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);
                    Log.d("159753", "onClick: insertChallenge" + challenge.getContents());
                    Log.d("zzz123", "onClick: " + "insert_challenge");
                    challenge_dialog.dismiss();
                } else {
                    //dbHelper.updateChallenge(challenge.getContents(),"운동들어오기",challenge.getDate(),2.5f);
                    //Log.d("159753", "onClick: updateChallenge"+"운동들어오기");
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    challenge_dialog.dismiss();
                }
            }
        });
    }

    public void showDialog2() { //도전과제 편집창
        challenge_edit_dialog.show();
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        RatingBar ratingBar;//여기 클릭한 리사이클러 데이터 불러와줘야함...
        EditText content;   //


        ratingBar = challenge_edit_dialog.findViewById(R.id.ratingBar_challenge_challedit);  //중요도
        ratingBar.setRating(2.0f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  // 도전과제 중요도 별점
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1); //중요도 최소 별 1개로 설정.
                }
            }
        });

        content = challenge_edit_dialog.findViewById(R.id.content_challenge_challedit);   //내용
        content.setText("");
        ImageButton btn_delete = challenge_edit_dialog.findViewById(R.id.button_delete_chall2);   //삭제 버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.challengedelete(challenge_contents.get(count).toString());
                challenge_edit_dialog.dismiss();
                loadDB(temp);
                init_recycler();
                getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);
                Toast.makeText(getApplicationContext(), "도전과제 삭제", Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: delete_challenge");
            }
        });


        Button btn_submit = challenge_edit_dialog.findViewById(R.id.button_submit_challedit);    //수정 버튼
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents;
                float ratings;
                contents = content.getText().toString();
                if (content.length() > 0) {
                    String a = challenge_contents.get(count).toString();
                    ratings = ratingBar.getRating();
                    dbHelper.updateChallenge(a, contents, ratings);
                    challenge_edit_dialog.dismiss();
                    init_recycler();
                    loadDB(temp);
                    getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);
                    Toast.makeText(getApplicationContext(), "도전과제 수정", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    challenge_edit_dialog.dismiss();
                }
            }
        });
    }

    public void onPopupMenuButtonClick(View button) {     //더보기 버튼 클릭 시 팝업메뉴 생성
        PopupMenu popupMenu = new PopupMenu(this, btn_menu);    //popup 메뉴 객체 생성
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());    //팝업메뉴 xml 지정
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu1:
                        Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                        startActivity(intent);
                        finish();
                        Log.d("zzz123", "onMenuItemClick: " + "boxMenu_main");
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(MainActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        Log.d("zzz123", "onMenuItemClick: " + "achieveMenu_main");
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        Log.d("zzz123", "onMenuItemClick: " + "settingMenu_main");
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void onResume() {
        super.onResume();
        adapterchallenge.notifyDataSetChanged();
        adapterplan.notifyDataSetChanged();
    }

    public void onBackPressed() {   //뒤로가기 두번 눌러서 앱 종료
        //super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    //↓ 시작일, 종료일 불러오기 ↓
    public void processDatePickerResult(int year, int month, int day) {  //기간1 날짜데이터 불러오기 이벤트
        selectDay1 = day;
        selectMonth1 = month + 1;
        Log.d("123123", "processDatePickerResult: " + month);
        selectYear1 = year;
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        Log.d("123123", "processDatePickerResult: " + day_string);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d1 = dateMessage;
        ((TextView) challenge_dialog.findViewById(R.id.textView_date1_challenge)).setText(dateMessage);
    }

    public void processDatePicker2Result(int year, int month, int day) {  //기간2 날짜데이터 불러오기 이벤트
        selectDay2 = day;
        selectMonth2 = month + 1;
        selectYear2 = year;
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        Log.d("123123", "processDatePicker2Result: " + day_string);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d2 = dateMessage;
        ((TextView) challenge_dialog.findViewById(R.id.textView_date2_challenge)).setText(dateMessage);
    }
    // ↑ 시작일, 종료일 불러오기 ↑

    private void init_recycler() { //RecyclerView initiate method
        RecyclerView recyclerView_plan = findViewById(R.id.recycler_plan);
        RecyclerView recyclerView_challenge = findViewById(R.id.recycler_challenge);
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView_plan.setLayoutManager(linearLayoutManager1);
        recyclerView_challenge.setLayoutManager(linearLayoutManager2);

        adapterplan = new PlanRecyclerAdapter();
        adapterchallenge = new ChallengeRecyclerAdapter();
        recyclerView_plan.setAdapter(adapterplan);
        recyclerView_challenge.setAdapter(adapterchallenge);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                count = position;

                adapterchallenge.removeItem(position);
                adapterchallenge.notifyItemRemoved(position);
                adapterchallenge.notifyItemRangeChanged(position, adapterchallenge.getItemCount());
                dbHelper.updatePass(challenge_contents.get(position).toString(), 1);
                //dbHelper.challengedelete(challenge_contents.get(position).toString());

                Toast.makeText(getApplicationContext(), "도전과제 완료!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftClicked(int position) {   //리사이클러 수정
                showDialog2();
                count = position;

            }
        });

        SwipeController1 swipeController1 = new SwipeController1(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                count = position;
                adapterplan.removeItem(position);
                adapterplan.notifyItemRemoved(position);
                adapterplan.notifyItemRangeChanged(position, adapterplan.getItemCount());
                dbHelper.plandelete(plan_contents.get(position).toString());
                Toast.makeText(getApplicationContext(), "일정 삭제.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLeftClicked(int position) {   //리사이클러 수정
                showDialog3();
                count = position;

            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView_challenge);
        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(swipeController1);
        itemTouchHelper1.attachToRecyclerView(recyclerView_plan);

        recyclerView_challenge.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
        recyclerView_plan.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController1.onDraw(c);
            }
        });
    }

    private void getData_recycler(List plan_contents, List plan_categorys, List plan_dates, List challenge_ratings, List challenge_contents, List challenge_dates) {
        List<String> listPlanCategory = plan_categorys;
        List<String> listPlanContent = plan_contents;
        List<String> listplanDate = plan_dates;
        List<Float> listChallengeRating = challenge_ratings;
        List<String> listChallengeContent = challenge_contents;
        List<String> listChallengeDate = challenge_dates;

        for (int i = 0; i < listPlanCategory.size(); i++) {
            recyclerPlanData plandata = new recyclerPlanData();
            plandata.setTitle(listPlanCategory.get(i));
            plandata.setContent(listPlanContent.get(i));
            plandata.setDate(listplanDate.get(i));

            adapterplan.addItem(plandata);
        }

        for (int i = 0; i < listChallengeRating.size(); i++) {
            recyclerChallengeData challengedata = new recyclerChallengeData();

            challengedata.setRating(listChallengeRating.get(i));
            challengedata.setContent(listChallengeContent.get(i));
            challengedata.setDate(listChallengeDate.get(i));

            adapterchallenge.addItem(challengedata);
        }
        adapterplan.notifyDataSetChanged();
        adapterchallenge.notifyDataSetChanged();
    }


    public void loadDB(Context temp) {
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SimpleDateFormat daychanger = new SimpleDateFormat("dd");
        SimpleDateFormat monthchanger = new SimpleDateFormat("MM");
        SimpleDateFormat yearchanger = new SimpleDateFormat("yyyy");
        Date date = Calendar.getInstance().getTime();

//------------------------------------------------------------------------------------------------------
        String sortOrder = swp_database.PlanDB.PLAN_ID + " DESC";
        String selection = swp_database.PlanDB.PLAN_DAY + " = ? ";
        String[] selectionArgs = {daychanger.format(date)};
        Cursor plancursor = db.query(
                swp_database.PlanDB.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
        List plan_id_temp = new ArrayList<>();
        List plan_contents_temp = new ArrayList<>();
        List plan_categorys_temp = new ArrayList<>();
        List plan_dates_temp = new ArrayList<>();

        while (plancursor.moveToNext()) {
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
        }

        plan_id = plan_id_temp;
        plan_contents = plan_contents_temp;
        plan_categorys = plan_categorys_temp;
        plan_dates = plan_dates_temp;


        String sortOrder1 = swp_database.ChallengeDB.CHALLENGE_ID + " ASC";
        Cursor challengecursor = db.query(
                swp_database.ChallengeDB.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                sortOrder1
        );
        List challenge_id_temp = new ArrayList<>();
        List challenge_contents_temp = new ArrayList<>();
        List challenge_ratings_temp = new ArrayList<>();
        List challenge_dates_temp = new ArrayList<>();
        List challenge_pass_temp = new ArrayList<>();
        while (challengecursor.moveToNext()) {
            Log.d("159753", "loadDB: " + 123123123);
            int day1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY1));
            int day2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY2));
            int month1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH1));
            int month2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH2));
            int year1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR1));
            int year2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR2));
            int pass = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS));
            if (pass != 1) {
                if (year1 <= Integer.parseInt(yearchanger.format(date)) && Integer.parseInt(yearchanger.format(date)) <= year2) { // year1 과 year2 사이의 현재 날짜가 있는가?
                    if (month1 <= Integer.parseInt(monthchanger.format(date)) && Integer.parseInt(monthchanger.format(date)) <= month2) { // month1 과 month2 사이의 현재 날짜가 있는가?
                        if (day1 <= Integer.parseInt(daychanger.format(date)) && Integer.parseInt(daychanger.format(date)) <= day2) { // day1과 day2사이의 현재 날짜가 있는가? (도전과제 범위 안에서의 출력

                            String challenge_date = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DUE));
                            challenge_dates_temp.add(challenge_date);
                            String challenge_content = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_CONTENTS));
                            challenge_contents_temp.add(challenge_content);
                            Float challenge_rating = challengecursor.getFloat(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_RATING));
                            challenge_ratings_temp.add(challenge_rating);
                            int challengeItems = challengecursor.getInt(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_ID));
                            challenge_id_temp.add(challengeItems);
                            int challengePass = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS));
                            challenge_pass_temp.add(challengePass);
                        }
                    }
                }
            }
        }
        challenge_id = challenge_id_temp;
        challenge_contents = challenge_contents_temp;
        challenge_ratings = challenge_ratings_temp;
        challenge_dates = challenge_dates_temp;
        challenge_pass = challenge_pass_temp;

        Cursor usercursor = db.query(
                swp_database.UserDB.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (usercursor.moveToNext()) {
            achiveimg = usercursor.getInt(usercursor.getColumnIndexOrThrow(swp_database.UserDB.CURRENT_IMG));
            achive = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.CURRENT_ACHIVE));
            username = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.USER_NAME));
            keys = usercursor.getInt(usercursor.getColumnIndexOrThrow(swp_database.UserDB.USER_KEY));
        }
        user.setCnt_key(keys);
    }

    public void checkChellenge() {
        loadDB(temp);
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat yearchanger = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthchanger = new SimpleDateFormat("MM");
        SimpleDateFormat daychanger = new SimpleDateFormat("dd");
        Date date = Calendar.getInstance().getTime();
        List<Integer> challenge_passes = new ArrayList<>();
        List<String> challPassContents = new ArrayList<>();
        String sortOrder1 = swp_database.ChallengeDB.CHALLENGE_ID + " ASC";
        String selection = swp_database.ChallengeDB.CHALLENGE_CHECK + " LIKE ?";
        String[] selectionArgs = {"0"};
        Cursor challengecursor = db.query(
                swp_database.ChallengeDB.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder1
        );
        while (challengecursor.moveToNext()) {
            int day1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY1));
            int day2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY2));
            int month1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH1));
            int month2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH2));
            int year1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR1));
            int year2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR2));
            if (year1 <= Integer.parseInt(yearchanger.format(date)) && Integer.parseInt(yearchanger.format(date)) <= year2) { // year1 과 year2 사이의 현재 날짜가 있는가?
                if (month1 <= Integer.parseInt(monthchanger.format(date)) && Integer.parseInt(monthchanger.format(date)) <= month2) { // month1 과 month2 사이의 현재 날짜가 있는가?
                    if (day1 <= Integer.parseInt(daychanger.format(date)) && Integer.parseInt(daychanger.format(date)) <= day2) { // day1과 day2사이의 현재 날짜가 있는가? (도전과제 범위 안에서의 출력
                        int pass1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS));
                        challenge_passes.add(pass1);
                        if (pass1 == 1) {
                            challPassContents.add(
                                    challengecursor.getString(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_CONTENTS))
                            );
                        }
                    }
                }
            }
        }
        KeyController key = KeyController.getInstance();
        SimpleDateFormat day = new SimpleDateFormat("dd");
        int checkcount = 0;
        for (int i = 0; i < challenge_passes.size(); i++) {
            if (challenge_passes.get(i) == 1) {
                checkcount++;
            }
        }


        if(!PreferenceManager.getBoolean(MainActivity.this,"check")) {
                if (challenge_passes.size() == 0) {
                    Toast.makeText(getApplicationContext(), "도전과제가 없는것 같아요!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (key.givekey(user, checkcount)) {
                        dbHelper.updateUserKeyCount(username, user.getCnt_key());
                        for (int i = 0; i < challPassContents.size(); i++) {
                            dbHelper.updateCheckValue(challPassContents.get(i));
                        }
                        Toast.makeText(getApplicationContext(), "열쇠를 얻었습니다!", Toast.LENGTH_SHORT).show();
                        PreferenceManager.setBoolean(MainActivity.this, "check", true);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "도전과제가 남았어요", Toast.LENGTH_SHORT).show();
                        Log.d("159753", "checkChellenge: " + PreferenceManager.getString(MainActivity.this, "today"));
                        Log.d("159753", "checkChellenge: " + daychanger.format(date));
                    }
                }
            }
        else{
            Toast.makeText(getApplicationContext()," 이미 오늘의 보상을 받으셨어요!",Toast.LENGTH_SHORT).show();
        }
    }
}