package com.example.swp_challenge.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.BoxController;
import com.example.swp_challenge.controller.KeyController;
import com.example.swp_challenge.controller.UserAchivementController;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

////
public class BoxActivity extends AppCompatActivity {
    ImageButton img_cal;
    TextView textdate;
    Button btn_open;
    ImageButton btn_menu;
    TextView textKey, achivetext, textrank;
    Dialog openBox_dialog;
    public static int Userkey, hints, flag = 1;
    public static String tempusername;
    public String achivestr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserController user =  UserController.getInstance();
        BoxController box =BoxController.getInstance();
        UserAchivementController achive = UserAchivementController.getInstance();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_box);
        Log.d("159753", "onCreate: box"+user.getCnt_key());
        img_cal=findViewById(R.id.button_calendar_box);
        textdate = findViewById(R.id.textView_dateOfToday);
        textrank = findViewById(R.id.textView_tier);
        textKey = findViewById(R.id.textView_amountOfKey);
        btn_open = findViewById(R.id.button_openBox);
        btn_menu = findViewById(R.id.button_menu_box); //메뉴 더보기 버튼
        //banner set date in korean
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        achive.initAchivement();

        //DB Load Method sector
       loadDB();
       flag =0;
        textKey.setText("x "+Userkey);
        box.boxOpenCount(user);
        textrank.setText(getrankStr());


        //intent 넘기기 함수 밑으로 인자
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoxActivity.this, CalendarActivity.class);
                startActivity(intent);
                Log.d("zzz123", "onClick: calendarButton_box");
                finish();
            }
        });

        btn_open.setOnClickListener(new View.OnClickListener() {    //상자열기
            @Override
            public void onClick(View v) {
                KeyController key = KeyController.getInstance();

                if(key.checkKey(user)){
                    loadDB();
                    showDialog();
                    hints = box.boxOpen(user);
                    if(hints==1){
                        int randomAchive = (int)(Math.random()*10);
                        achive.setrandoms(randomAchive);
                        achivestr = achive.rewardAchive();
                    };
                    dbHelper.updateUserKeyCount(tempusername, user.getCnt_key());
                    box.boxOpenCount(user);
                    dbHelper.updateUserBoxOpenCnt(tempusername, user.getBoxOpen());
                    dbHelper.updateUserBoxRank(tempusername, user.getBoxRank());
                    dbHelper.updateUserAchive(tempusername, achivestr);
                    swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(BoxActivity.this);
                }
                else{
                    Toast.makeText(getApplicationContext(),"can'topen",Toast.LENGTH_SHORT).show();
                }
            }
        });

        openBox_dialog = new Dialog(BoxActivity.this);       // 뽑기결과 다이얼로그 설정
        openBox_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //           "
        openBox_dialog.setContentView(R.layout.activity_popup_box);       //           "


    }
    public void showDialog() {
        openBox_dialog.show();
        achivetext = openBox_dialog.findViewById(R.id.textView_newAchieve);
        switch (hints){
            case 0:
                achivetext.setText("열쇠 1개");
                break;
            case 1:
                achivetext.setText("칭호 "+"'"+achivestr+"'");
                break;
            case 2:
                achivetext.setText("열쇠 "+hints+"개");
                break;
            case 3:
                achivetext.setText("열쇠 "+hints+"개");
                break;
        }
        Button btn_popup = openBox_dialog.findViewById(R.id.button_check_popupBox);
        btn_popup.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                loadDB();
                textKey.setText("x "+Userkey);
                textrank.setText(getrankStr());
                Log.d("zzz123", "onClick: " + "check_open");
                openBox_dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BoxActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void onPopupMenuButtonClick(View button) {     //더보기 버튼 클릭 시 팝업메뉴 생성
        PopupMenu popupMenu = new PopupMenu(this, btn_menu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu1:
                        Toast.makeText(getApplicationContext(),"현재 페이지입니다!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                    case R.id.action_menu2:
                        Intent intent = new Intent(BoxActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: achieveMenu_box");
                        finish();
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(BoxActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: settingMenu_box");
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    public void loadDB(){

        UserController user = UserController.getInstance();
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(BoxActivity.this);
        UserAchivementController achive = UserAchivementController.getInstance();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String check = PreferenceManager.getString(BoxActivity.this,"username");
        Log.d("159753", "updateDB: "+check);
        List itemids = new ArrayList<>();
        List tempname = new ArrayList<>();
        List box_open = new ArrayList<>();
        List box_rank = new ArrayList<>();
        String selection = swp_database.UserDB.USER_NAME + " = ? ";
        String[] selectionArgs = {check};
        Cursor userCursor = db.query(
                swp_database.UserDB.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        while(userCursor.moveToNext()) {
            int itemId = userCursor.getInt(
                    userCursor.getColumnIndexOrThrow(swp_database.UserDB.USER_KEY));
            itemids.add(itemId);
            String username = userCursor.getString(
                    userCursor.getColumnIndexOrThrow(swp_database.UserDB.USER_NAME));
            tempname.add(username);
            int box_open_cnt = userCursor.getInt(
                    userCursor.getColumnIndexOrThrow(swp_database.UserDB.BOX_OPEN_CNT));
            box_open.add(box_open_cnt);
            int box_ranks = userCursor.getInt(
                    userCursor.getColumnIndexOrThrow(swp_database.UserDB.BOX_RANK));
            box_rank.add(box_ranks);
        }
        //db load
        if(flag == 1){
        for(int i =0; i< itemids.size();i++) {
            Userkey = (int) itemids.get(i);
            Log.d("159753", "dbfrom " + Userkey);
            user.setCnt_key(Userkey);
            user.setBoxOpen((int)box_open.get(i));
            user.setBoxRank((int)box_rank.get(i));
            tempusername = tempname.get(i).toString();

            Log.d("159753", "user" + user.getCnt_key());
            }
        }
        }
        public String getrankStr(){
        UserController user = UserController.getInstance();
            String rankname[] = new String[10];
            rankname[1] = "아이언";
            rankname[2] ="브론즈";
            rankname[3] ="실버";
            rankname[4] ="골드";
            rankname[5] ="플래티넘";
            rankname[6]="다이아";
            rankname[7]="마스터";
            return rankname[user.getBoxRank()];
        }

    }

