package com.example.swp_challenge;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
////
public class SettingsActivity extends AppCompatActivity {
    ImageButton img_cal;
    Spinner spinner;
    ImageButton btn_menu;
    String menu_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);
        /*if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        img_cal=findViewById(R.id.img_cal_set);
        //@@@@@메뉴 스피너@@@@@@@//
        btn_menu = findViewById(R.id.btn_more_set);
        final String[] menu = {"상자", "칭호", "설정"};  //메뉴 아이템 항목
        spinner = findViewById(R.id.spinner_set);  //스피너 초기화
        ArrayAdapter menuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menu);  //menu 어댑터 생성
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(menuAdapter);
        spinner.setSelection(2);
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
                        Intent intent = new Intent(SettingsActivity.this, BoxActivity.class);
                        startActivity(intent); //동일 페이지라 설정X
                        finish();
                        break;
                    case "칭호":
                        intent = new Intent(SettingsActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "설정":
                        /*intent = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent);*/
                        Toast.makeText(getApplicationContext(),"This is that page!", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        //@@@@@메뉴 스피너 끝@@@@@2//
        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CalendarActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}