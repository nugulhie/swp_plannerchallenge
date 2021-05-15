package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

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

public class ChallhistoryActivity extends AppCompatActivity {
    //////
    ImageButton img_cal;
    TextView textdate;
    List challenge_id_pass = new ArrayList<>();
    List challenge_contents_pass = new ArrayList<>();
    List challenge_ratings_pass = new ArrayList<>();
    List challenge_dates_pass = new ArrayList<>();
    List challenge_pass_pass = new ArrayList<>();

    List challenge_id_false = new ArrayList<>();
    List challenge_contents_false = new ArrayList<>();
    List challenge_ratings_false = new ArrayList<>();
    List challenge_dates_false = new ArrayList<>();
    List challenge_pass_false = new ArrayList<>();
    private ChallengeRecyclerAdapter adapterchallenge_pass;
    private ChallengeRecyclerAdapter adapterchallenge_false;
    public static Context temp;

    public static int day;
    public static int month;
    public static int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_challhistory);
        //banner set date in korean
        temp = ChallhistoryActivity.this;
        textdate = findViewById(R.id.textView_dateOfToday);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));
        img_cal = findViewById(R.id.img_cal_history);
        Intent intent = getIntent();
        day = intent.getIntExtra("selectday",0);
        month = intent.getIntExtra("selectmonth", 0);
        year = intent.getIntExtra("selectyear", 0);
        Log.d("222222", "onCreate: "+day);
        init_recycler();
        loadDB(temp,day);
        getData_recycler();

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


    public void loadDB(Context temp, int day) {
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(temp);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Date date = Calendar.getInstance().getTime();

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



        while (challengecursor.moveToNext()) {
            int day1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY1));
            int day2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DAY2));
            int month1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH1));
            int month2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_MONTH2));
            int year1 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR1));
            int year2 = challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_YEAR2));
            if(day1 > day2){
                int tempday = day1;
                day1 = day2;
                day2 = tempday;
            }
            if(month1 > month2){
                int tempmonth = month1;
                month1 = month2;
                month2 = tempmonth;
            }
            if(year1 > year2){
                int tempyear = year1;
                year1 = year2;
                year2 = tempyear;
            }
            if (year1 <= year && year <= year2) { // year1 과 year2 사이의 현재 날짜가 있는가?
                if (month1 <= month && month <= month2) { // month1 과 month2 사이의 현재 날짜가 있는가?
                    if (day1 <= day && day <= day2) { // day1과 day2사이의 현재 날짜가 있는가? (도전과제 범위 안에서의 출력
                        if (challengecursor.getInt(challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS)) == 1) {
                            String challenge_date = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DUE));
                            challenge_dates_pass.add(challenge_date);
                            String challenge_content = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_CONTENTS));
                            challenge_contents_pass.add(challenge_content);
                            Float challenge_rating = challengecursor.getFloat(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_RATING));
                            challenge_ratings_pass.add(challenge_rating);
                            int challengeItems = challengecursor.getInt(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_ID));
                            challenge_id_pass.add(challengeItems);
                            int challenge_pass = challengecursor.getInt(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS));
                            challenge_pass_pass.add(challenge_pass);
                        } else {
                            String challenge_date = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_DUE));
                            challenge_dates_false.add(challenge_date);
                            String challenge_content = challengecursor.getString(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_CONTENTS));
                            challenge_contents_false.add(challenge_content);
                            Float challenge_rating = challengecursor.getFloat(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_RATING));
                            challenge_ratings_false.add(challenge_rating);
                            int challengeItems = challengecursor.getInt(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_ID));
                            challenge_id_false.add(challengeItems);
                            int challenge_pass = challengecursor.getInt(
                                    challengecursor.getColumnIndexOrThrow(swp_database.ChallengeDB.CHALLENGE_PASS));
                            challenge_pass_false.add(challenge_pass);
                        }
                    }
                }
            }
        }
    }

    private void init_recycler(){ //RecyclerView initiate method
        RecyclerView recyclerView_false = findViewById(R.id.recyclerView_history_false);
        RecyclerView recyclerView_pass = findViewById(R.id.recyclerView_history_pass);


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        recyclerView_false.setLayoutManager(linearLayoutManager1);
        recyclerView_pass.setLayoutManager(linearLayoutManager2);

        adapterchallenge_pass = new ChallengeRecyclerAdapter();
        adapterchallenge_false = new ChallengeRecyclerAdapter();

        recyclerView_pass.setAdapter(adapterchallenge_pass);
        recyclerView_false.setAdapter(adapterchallenge_false);
    }
    private void getData_recycler(){
        List<Float> listChallengeRating_pass = challenge_ratings_pass;
        List<String> listChallengeContent_pass = challenge_contents_pass;
        List<String> listChallengeDate_pass = challenge_dates_pass;
        List<Integer> listChallengePass_pass = challenge_pass_pass;

        List<Float> listChallengeRating_false = challenge_ratings_false;
        List<String> listChallengeContent_false = challenge_contents_false;
        List<String> listChallengeDate_false = challenge_dates_false;
        List<Integer> listChallengePass_false = challenge_pass_false;


        for (int i=0;i<listChallengeRating_pass.size();i++) {
            recyclerChallengeData challengepassdata = new recyclerChallengeData();

            challengepassdata.setRating(listChallengeRating_pass.get(i));
            challengepassdata.setContent(listChallengeContent_pass.get(i));
            challengepassdata.setDate(listChallengeDate_pass.get(i));
            adapterchallenge_pass.addItem(challengepassdata);
        }

        for(int i =0;i<listChallengeRating_false.size();i++){
            recyclerChallengeData challengefalsedata = new recyclerChallengeData();

            challengefalsedata.setRating(listChallengeRating_false.get(i));
            challengefalsedata.setContent(listChallengeContent_false.get(i));
            challengefalsedata.setDate(listChallengeDate_false.get(i));
            adapterchallenge_false.addItem(challengefalsedata);
        }
        adapterchallenge_pass.notifyDataSetChanged();
        adapterchallenge_false.notifyDataSetChanged();
        }
    }


