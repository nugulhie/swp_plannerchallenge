package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AchivementActivity extends AppCompatActivity {
    //
    ImageButton img_cal;
    TextView textdate;
    ImageButton btn_menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_achivement);

        img_cal = findViewById(R.id.img_cal_ach);
        btn_menu = findViewById(R.id.btn_more_ach); //메뉴 더보기 버튼
        //banner set date in korean
        textdate = findViewById(R.id.textview_today);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat korDate = new SimpleDateFormat("MM월 dd일 E요일", Locale.KOREAN);
        textdate.setText(korDate.format(date));

// 밑으로 전부 인텐트 넘기는 함수
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AchivementActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
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
                        finish();
                        break;
                    case R.id.action_menu2:
                        Toast.makeText(getApplicationContext(),"현재 페이지입니다!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                    case R.id.action_menu3:
                        intent = new Intent(AchivementActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
//