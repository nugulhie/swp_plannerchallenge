package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.controller.ChallengeController;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.ChallengeRecyclerAdapter;
import com.example.swp_challenge.dataController.PlanRecyclerAdapter;
import com.example.swp_challenge.dataController.recyclerChallengeData;
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
public class MainActivity extends AppCompatActivity {
    KeyController key = KeyController.getInstance();
    UserController user =UserController.getInstance();
    PlannerController plan = PlannerController.getInstance();
    ChallengeController challenge = ChallengeController.getInstance();
    //
    public static Activity mActivity;
    private PlanRecyclerAdapter adapterplan;
    private ChallengeRecyclerAdapter adapterchallenge;
    public ImageButton button_Add_challenge;
    ImageButton btn_menu, img_cal;
    TextView textdate;
    int count = 0;

    private long backKeyPressedTime = 0;    //마지막으로 뒤로가기 눌렀던 시간 저장
    private Toast toast;    //첫번째 뒤로가기 버튼 누를때 표시
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mActivity = MainActivity.this;

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat();

//------------------------------------------------------------------------------------------------------
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
        List challenge_id = new ArrayList<>();
        List challenge_contents = new ArrayList<>();
        List challenge_ratings = new ArrayList<>();
        List challenge_dates = new ArrayList<>();


        while (challengecursor.moveToNext()){
            String challenge_date = challengecursor.getString(
                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DATE));
            challenge_dates.add(challenge_date);
            String challenge_content = challengecursor.getString(
                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_CONTENTS));
            challenge_contents.add(challenge_content);
            Float challenge_rating = challengecursor.getFloat(
                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_RATING));
            challenge_ratings.add(challenge_rating);
            int challengeItems = challengecursor.getInt(
                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_ID));
            challenge_id.add(challengeItems);
        }


        init_recycler();
        getData_recycler(plan_contents, plan_categorys, plan_dates, challenge_ratings, challenge_contents, challenge_dates);

        //------------------------------------------------------------------------------------------------------
        dbHelper.updatePass(challenge.getContents(),1);

        Log.d("159753", "onCreate: main"+user.getCnt_key());
        Date date = Calendar.getInstance().getTime();
        textdate = findViewById(R.id.textView_dateOfToday);
        button_Add_challenge = findViewById(R.id.button_addChall_main);
        //button_detail = findViewById(R.id.button_detail_main);
        img_cal = findViewById(R.id.button_calendar_main);

        //도전과제 진행도 예시
        Button btn_minus = findViewById(R.id.button_minus);
        Button btn_plus = findViewById(R.id.button_plus);
        ProgressBar progressBar = findViewById(R.id.progressBar_todayChallenge);

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count < 0) {
                    count = 0;
                    progressBar.setProgress(0);
                }
                else
                    progressBar.setProgress(--count);
            }
        });
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > 3) {
                    count = 3;
                    progressBar.setProgress(3);
                }
                else
                    progressBar.setProgress(++count);
            }
        });//도전과제 진척도 예시

        //banner set date in korean
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));

        btn_menu = findViewById(R.id.button_menu_main);

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
                Intent intent = new Intent(MainActivity.this, ChallengePopupActivity.class);
                startActivity(intent);
                Log.d("zzz123", "onClick:" + "addChallengeButton_main");
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

}