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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.ChallengeController;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.ChallengeRecyclerAdapter;
import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.SwipeController;
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
    KeyController key = KeyController.getInstance();
    UserController user =UserController.getInstance();
    PlannerController plan = PlannerController.getInstance();
    ChallengeController challenge = ChallengeController.getInstance();
    //
    public static Activity mActivity;
    private PlanRecyclerAdapter adapterplan;
    private ChallengeRecyclerAdapter adapterchallenge;
    public Button button_Add_challenge;
    ImageButton btn_menu, img_cal;
    String d1, d2, tempD, hour, minute;
    Date date1, date2, tempDate;
    TextView textdate;
    public static int count = 0, selectDay1, selectDay2, selectMonth1, selectMonth2, selectYear1, selectYear2;
    Dialog challenge_dialog, challenge_edit_dialog;
    public static Context temp;

    public static List plan_id = new ArrayList<>();
    public static List plan_contents = new ArrayList<>();
    public static List plan_categorys = new ArrayList<>();
    public static List plan_dates = new ArrayList<>();
    public static List challenge_id = new ArrayList<>();
    public static List challenge_contents = new ArrayList<>();
    public static List challenge_ratings = new ArrayList<>();
    public static List challenge_dates = new ArrayList<>();


    private long backKeyPressedTime = 0;    //마지막으로 뒤로가기 눌렀던 시간 저장
    private Toast toast;    //첫번째 뒤로가기 버튼 누를때 표시
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        SimpleDateFormat daychanger = new SimpleDateFormat("dd");
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        Date date = Calendar.getInstance().getTime();
        textdate = findViewById(R.id.textView_dateOfToday);
        button_Add_challenge = findViewById(R.id.button_addChall_main);
        img_cal = findViewById(R.id.button_calendar_main);
        btn_menu = findViewById(R.id.button_menu_main);

        textdate.setText(korDate.format(date));


        challenge_dialog = new Dialog(MainActivity.this);       // 도전과제 다이얼로그 설정
        challenge_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
        challenge_dialog.setContentView(R.layout.activity_popup);       //           "

        challenge_edit_dialog = new Dialog(MainActivity.this);  // 도전과제 편집창
        challenge_edit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        challenge_edit_dialog.setContentView(R.layout.activity_challengepopup_edit);

        temp = MainActivity.this;
        loadDB(temp);
        init_recycler();
        getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);

        //------------------------------------------------------------------------------------------------------
        dbHelper.updatePass(challenge.getContents(),1);

        Log.d("159753", "onCreate: main"+user.getCnt_key());

        //banner set date in korean

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
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

    public void showDialog() {  //도전과제 다이얼로그 생성 함수
        challenge_dialog.show();

        //중요도, 내용, 날짜1, 날짜2, 시각
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        RatingBar ratingBar = challenge_dialog.findViewById(R.id.ratingBar_challenge);  //중요도
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  // 도전과제 중요도 별점
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1); //중요도 최소 별 1개로 설정.
                }
            }
        });
        EditText content = challenge_dialog.findViewById(R.id.content_challenge);   //내용
        ImageButton btn_date1 = challenge_dialog.findViewById(R.id.button_date1_chall); //날짜버튼1
        btn_date1.setOnClickListener(new View.OnClickListener() {   // 달력버튼1 선택시 달력1 dialogFragment 생성 이벤트
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
                Log.d("zzz123", "onClick: datePicker1_challenge");
            }
        });
        ImageButton btn_date2 = challenge_dialog.findViewById(R.id.button_date2_chall); //날짜버튼2
        btn_date2.setOnClickListener(new View.OnClickListener() {  // 달력버튼2 선택시 달력2 dialogFragment 생성 이벤트
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker2Fragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
                Log.d("zzz123", "onClick: datePicker2_challenge");
            }
        });


        ImageButton btn_delete = challenge_dialog.findViewById(R.id.button_delete_chall);   //삭제 버튼
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //추후 데이터 삭제 메소드 추가해줄 것!
                challenge_dialog.dismiss();
                Toast.makeText(getApplicationContext(),"도전과제 삭제", Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: delete_challenge");
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
                if (content.length() > 0 & d1 != null & d2 != null)
            {
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
                if(compare > 0){
                    tempDate = date1;   //date 형 바꾸기
                    date1 = date2;
                    date2 = tempDate;
                    tempD = d1;         //str 형 바꾸기
                    d1 = d2;
                    d2 = tempD;
                }
                // ↑ date 크기 비교해서 순서 바꿔주기 ↑
                challenge.setChallenge(ratingBar.getRating(),content.getText().toString());
                if(selectDay1 > selectDay2){
                    int tempday = selectDay1;
                    selectDay1 = selectDay2;
                    selectDay2 = tempday;
                }
                if(selectMonth1 > selectMonth2){
                    int tempmonth = selectMonth1;
                    selectMonth1 = selectMonth2;
                    selectMonth2 = tempmonth;
                }
                if(selectYear1 > selectYear2){
                    int tempyear = selectYear1;
                    selectYear1 = selectYear2;
                    selectYear2 = tempyear;
                }
                dbHelper.insertChallenge(challenge.getContents(), challenge.getDate(), challenge.getRating(), selectDay1, selectDay2, selectMonth1, selectMonth2, selectYear1, selectYear2);
                loadDB(temp);
                init_recycler();
                getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);
                Log.d("159753", "onClick: insertChallenge"+challenge.getContents());
                Toast.makeText(getApplicationContext(), "할일: " + content.getText().toString() +", 중요도: "+ (int)ratingBar.getRating() +
                        ", 기간(str): " +d1 +" ~ "+ d2+", 시각: "+hour+":"+minute+", 기간(date):"+date1.toString() +" ~ "+ date2.toString(), Toast.LENGTH_SHORT).show();
                Log.d("zzz123", "onClick: " + "insert_challenge");
                challenge_dialog.dismiss();
            }
            else {
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

        RatingBar ratingBar;//여기 클릭한 리사이클러 데이터 불러와줘야함...
        EditText content;   //

        ratingBar = challenge_edit_dialog.findViewById(R.id.ratingBar_challenge_challedit);  //중요도
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  // 도전과제 중요도 별점
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingBar.getRating() < 1.0f) {
                    ratingBar.setRating(1); //중요도 최소 별 1개로 설정.
                }
            }
        });

        content = challenge_edit_dialog.findViewById(R.id.content_challenge_challedit);   //내용

        Button btn_submit = challenge_edit_dialog.findViewById(R.id.button_submit_challedit);    //수정 버튼
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.length() > 0)
                {
                    //해당 데이터 수정해주는 메소드...집어넣어야합니다1
                    challenge_dialog.dismiss();
                }
                else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    challenge_dialog.dismiss();
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
                        Log.d("zzz123", "onMenuItemClick: " + "boxMenu_main");
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(MainActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: " + "achieveMenu_main");
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
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
    public void processDatePickerResult(int year, int month, int day){  //기간1 날짜데이터 불러오기 이벤트
        selectDay1 = day;
        selectMonth1 = month+1;
        Log.d("123123", "processDatePickerResult: "+month);
        selectYear1 = year;
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        Log.d("123123", "processDatePickerResult: "+day_string);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d1 = dateMessage;
        ((TextView) challenge_dialog.findViewById(R.id.textView_date1_challenge)).setText(dateMessage);
        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
    }
    public void processDatePicker2Result(int year, int month, int day){  //기간2 날짜데이터 불러오기 이벤트
        selectDay2 = day;
        selectMonth2 = month+1;
        selectYear2 = year;
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        Log.d("123123", "processDatePicker2Result: "+day_string);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d2 = dateMessage;
        ((TextView) challenge_dialog.findViewById(R.id.textView_date2_challenge)).setText(dateMessage);
        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
    }
    // ↑ 시작일, 종료일 불러오기 ↑

    private void init_recycler(){ //RecyclerView initiate method
        RecyclerView recyclerView_plan = findViewById(R.id.recycler_plan);
        RecyclerView recyclerView_challenge = findViewById(R.id.recycler_challenge);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView_plan.setLayoutManager(linearLayoutManager1);
        recyclerView_challenge.setLayoutManager(linearLayoutManager2);

        adapterplan = new PlanRecyclerAdapter();
        adapterchallenge = new ChallengeRecyclerAdapter();
        recyclerView_plan.setAdapter(adapterplan);
        recyclerView_challenge.setAdapter(adapterchallenge);

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {    //리사이클러 삭제
            @Override
            public void onRightClicked(int position) {
                adapterchallenge.removeItem(position);
                adapterchallenge.notifyItemRemoved(position);
                adapterchallenge.notifyItemRangeChanged(position, adapterchallenge.getItemCount());
            }

            @Override
            public void onLeftClicked(int position) {   //리사이클러 수정
                showDialog2();

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView_challenge);

        recyclerView_challenge.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
    private void getData_recycler(List plan_contents, List plan_categorys, List plan_dates, List challenge_ratings, List challenge_contents, List challenge_dates){
        List<String> listPlanCategory = plan_categorys;
        List<String> listPlanContent = plan_contents;
        List<String> listplanDate = plan_dates;
        List<Float> listChallengeRating = challenge_ratings;
        List<String> listChallengeContent = challenge_contents;
        List<String> listChallengeDate = challenge_dates;

        for(int i=0;i<listPlanCategory.size();i++){
            recyclerPlanData plandata = new recyclerPlanData();
            plandata.setTitle(listPlanCategory.get(i));
            plandata.setContent(listPlanContent.get(i));
            plandata.setDate(listplanDate.get(i));

            adapterplan.addItem(plandata);
        }

        for (int i=0;i<listChallengeRating.size();i++){
            recyclerChallengeData challengedata = new recyclerChallengeData();

            challengedata.setRating(listChallengeRating.get(i));
            challengedata.setContent(listChallengeContent.get(i));
           challengedata.setDate(listChallengeDate.get(i));

            adapterchallenge.addItem(challengedata);
        }
        adapterplan.notifyDataSetChanged();
        adapterchallenge.notifyDataSetChanged();
    }


    public void loadDB(Context temp){
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

        while (plancursor.moveToNext()){
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
        plan_categorys= plan_categorys_temp;
        plan_dates = plan_dates_temp;

        plancursor.close();

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
        while (challengecursor.moveToNext()){
            Log.d("159753", "loadDB: "+123123123);
            int day1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY1));
            int day2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY2));
            int month1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH1));
            int month2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH2));
            int year1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR1));
            int year2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR2));
            if(year1 <= Integer.parseInt(yearchanger.format(date))&& Integer.parseInt(yearchanger.format(date))<=year2) { // year1 과 year2 사이의 현재 날짜가 있는가?
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
                    }
                }
            }
        }
        challenge_id = challenge_id_temp;
        challenge_contents = challenge_contents_temp;
        challenge_ratings = challenge_ratings_temp;
        challenge_dates = challenge_dates_temp;

        challengecursor.close();

    }
}