package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
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
        init_recycler();
        getData_recycler();
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat();

        plan.setPlan("upd",1); //임시값
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
    private void getData_recycler(){
        List<String> listPlanCategory = Arrays.asList("약속", "약속","약속");
        List<String> listPlanContent = Arrays.asList(
                "10시 객체 과제마감",
                "13시 점심 약속(인혁이랑)",
                "19시 술약속(꾸븐)"
        );
        List<Float> listChallengeRating = Arrays.asList(1.5f, 2.5f, 3.0f);
        List<String> listChallengeContent = Arrays.asList(
                "7시에 기상",
                "러닝 3키로 뛰기",
                "빨래하기"
        );

        List<Date> listDate = Arrays.asList(
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime(),
                Calendar.getInstance().getTime()
        );
        for(int i=0;i<listPlanCategory.size();i++){
            recyclerPlanData plandata = new recyclerPlanData();
            plandata.setTitle(listPlanCategory.get(i));
            plandata.setContent(listPlanContent.get(i));
            plandata.setDate(listDate.get(i));
            adapterplan.addItem(plandata);
        }
        for (int i=0;i<listChallengeRating.size();i++){
            recyclerChallengeData challengedata = new recyclerChallengeData();

            challengedata.setRating(listChallengeRating.get(i));
            challengedata.setContent(listChallengeContent.get(i));
            challengedata.setDate(listDate.get(i));

            adapterchallenge.addItem(challengedata);
        }
        adapterplan.notifyDataSetChanged();
        adapterchallenge.notifyDataSetChanged();
    }
}