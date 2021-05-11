package com.example.swp_challenge;

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
        btn_menu = findViewById(R.id.btn_more_set);

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CalendarActivity.class);
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
                        Intent intent = new Intent(SettingsActivity.this, BoxActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(SettingsActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.action_menu3:
                        Toast.makeText(getApplicationContext(),"현재 페이지입니다!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}