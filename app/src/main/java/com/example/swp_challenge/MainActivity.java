package com.example.swp_challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.swp_challenge.controller.ChallengeController;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//
public class MainActivity extends AppCompatActivity {
    KeyController key = KeyController.getInstance();
    UserController user =UserController.getInstance();
    PlannerController plan = PlannerController.getInstance();
    ChallengeController challenge = ChallengeController.getInstance();
    swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
    SQLiteDatabase db = dbHelper.getReadableDatabase();
    SimpleDateFormat dateFormat = new SimpleDateFormat();

    public ImageButton button_Add_challenge;
    Spinner spinner;
    ImageButton btn_menu, img_cal;
    String menu_item;
    //----------------database-------------------


    private long backKeyPressedTime = 0;    //마지막으로 뒤로가기 눌렀던 시간 저장
    private Toast toast;    //첫번째 뒤로가기 버튼 누를때 표시
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        key.givekey(user, challenge.getChall_pass());
        user.setCnt_key(9);
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
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
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

    public void onData(swp_database db, swp_databaseOpenHelper dbhelper, UserController user, PlannerController plan, ChallengeController challenge){

    }

}