package com.example.swp_challenge.Activity;

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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.UserAchivementController;
import com.example.swp_challenge.dataController.AchivementsAdpater;
import com.example.swp_challenge.dataController.recyclerAchivementsData;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AchivementActivity extends AppCompatActivity {
    //
    ImageButton img_cal;
    TextView textdate, textAchive;
    ImageButton btn_menu;
    public String achive;
    public static int[] achiveList;
    public int position;
    AchivementsAdpater achiveAdapter;
    List<String> achiveString = new ArrayList<>();
    List<ImageView> achiveImage = new ArrayList<>();
    UserAchivementController AC = UserAchivementController.getInstance();
    public String currentAchive, username;
    Dialog apply_dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_achivement);
        img_cal = findViewById(R.id.button_calendar_achieve);
        btn_menu = findViewById(R.id.button_menu_achieve); //메뉴 더보기 버튼
        //banner set date in korean
        textdate = findViewById(R.id.textView_dateOfToday);
        textAchive = findViewById(R.id.textView_selectedAchieve);
        apply_dialog = new Dialog(AchivementActivity.this);
        apply_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        apply_dialog.setContentView(R.layout.activity_popup_applyachivement);


        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        Date date = Calendar.getInstance().getTime();
        textdate.setText(korDate.format(date));

        loadDB();
        textAchive.setText("현재 칭호 : "+"["+currentAchive+"]");
        achiveList = AC.giveAchivements(achive);
        for(int i =0; i<achiveList.length;i++){
            achiveString.add(AC.getAchivements(achiveList[i]));
        }
        init_recycler();
        getData_recycler(achiveString, achiveImage);

        achiveAdapter.setOnItemClickListener(new AchivementsAdpater.OnItemClickListner(){
            @Override
            public void onItemClick(View v, int pos){
                position = pos;
                showDialog();
            }
        });
// 밑으로 전부 인텐트 넘기는 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchivementActivity.this, CalendarActivity.class);
                startActivity(intent);
                Log.d("zzz123", "onClick: " + "calendarButton_achieve");
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AchivementActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    public void showDialog(){
        apply_dialog.show();
        Button apply = apply_dialog.findViewById(R.id.button_apply);
        Button cancel = apply_dialog.findViewById(R.id.button_cancel);
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentAchive = achiveString.get(position);
                swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(AchivementActivity.this);
                textAchive.setText("현재 칭호 : "+"["+currentAchive+"]");
                dbHelper.updateCurrentAchive(username,achiveString.get(position));
                apply_dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply_dialog.dismiss();
            }
        });

    }
    public void onPopupMenuButtonClick(View button) {     //더보기 버튼 클릭 시 팝업메뉴 생성
        PopupMenu popupMenu = new PopupMenu(this, btn_menu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu1:
                        Intent intent = new Intent(AchivementActivity.this, BoxActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: boxMenu_achieve");
                        finish();
                        break;
                    case R.id.action_menu2:
                        Toast.makeText(getApplicationContext(),"현재 페이지입니다!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(AchivementActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: settingMenu_achieve");
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void loadDB() {
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(AchivementActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        UserAchivementController AC = new UserAchivementController();
        AC.initAchivement();
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
            currentAchive = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.CURRENT_ACHIVE));
            achive = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.USER_ACHIVE));
            username = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.USER_NAME));
        }
    }

    private void init_recycler(){
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(AchivementActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        RecyclerView recyclerView_achivement = findViewById(R.id.recyclerView_Ach);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_achivement.setLayoutManager(linearLayoutManager);
        achiveAdapter = new AchivementsAdpater();
        recyclerView_achivement.setAdapter(achiveAdapter);
    }
    private void getData_recycler(List achiveContents, List achiveImg){
        List<String> Contents = achiveContents;
        List<ImageView> Img = achiveImg;

        for(int i =0; i<Contents.size();i++){
            recyclerAchivementsData achiveData = new recyclerAchivementsData();
            achiveData.setContent(Contents.get(i));
            achiveAdapter.addItem(achiveData);
        }
        achiveAdapter.notifyDataSetChanged();
    }


}
//