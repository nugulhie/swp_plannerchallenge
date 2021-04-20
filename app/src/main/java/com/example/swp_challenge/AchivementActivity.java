package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AchivementActivity extends AppCompatActivity {

    ImageButton img_cal;

    Spinner spinner;
    ImageButton btn_menu;
    String menu_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_achivement);

        img_cal = findViewById(R.id.img_cal_ach);

        //@@@@@메뉴 스피너@@@@@@@//
        btn_menu = findViewById(R.id.btn_more_ach);
        final String[] menu = {"홈", "상자", "칭호", "설정"};  //메뉴 아이템 항목
        spinner = findViewById(R.id.spinner_ach);  //스피너 초기화
        ArrayAdapter menuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menu);  //menu 어댑터 생성
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(menuAdapter);
        spinner.setSelection(2);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                menu_item = (String) spinner.getSelectedItem();
                Toast.makeText(getApplicationContext(),"Selected menu : " + menu[position], Toast.LENGTH_SHORT).show();

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
                    case "홈":
                        Intent intent = new Intent (AchivementActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "상자":
                        intent = new Intent(AchivementActivity.this, BoxActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "칭호":
                        /*intent = new Intent(AchivementActivity.this, AchivementActivity.class);
                        startActivity(intent);*/ //동일 페이지라 설정X
                        Toast.makeText(getApplicationContext(),"This is that page!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                    case "설정":
                        intent = new Intent(AchivementActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
        //@@@@@메뉴 스피너 끝@@@@@2//

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
}
