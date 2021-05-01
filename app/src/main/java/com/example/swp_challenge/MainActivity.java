package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    Spinner spinner;
    ImageButton btn_menu, img_cal;
    String menu_item;


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
        plan.setPlan("upd","운동"); //임시값
        challenge.setChallenge(3.5f,"운동들어오기");//임시값
        user.setCnt_key(9); //임시값
        key.givekey(user, 1); //임시값
        dbHelper.updatePass(challenge.getContents(),1);

        Log.d("159753", "onCreate: main"+user.getCnt_key());
        Date date = Calendar.getInstance().getTime();

        button_Add_challenge = findViewById(R.id.button_Addchall_main);
        //button_detail = findViewById(R.id.button_detail_main);
        img_cal = findViewById(R.id.img_cal_main);

        //@@@@@메뉴 스피너@@@@@@@//
        btn_menu = findViewById(R.id.btn_more_main);
        final String[] menu = {"상자", "칭호", "설정"};  //메뉴 아이템 항목
        spinner = findViewById(R.id.spinner_main);  //스피너 초기화
        ArrayAdapter menuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menu);  //menu 어댑터 생성
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(menuAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menu_item = (String) spinner.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //선택한 드롭다운에서 버튼 선택 시 - 액티비티 이동//
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(menu_item) {
                    case "상자":
                        Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                        startActivity(intent);
                        break;
                    case "칭호":
                        intent = new Intent(MainActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        break;
                    case "설정":
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
        //@@@@@메뉴 스피너 끝@@@@@2//

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChallengePopupActivity.class);
                startActivity(intent);
            }
        });

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