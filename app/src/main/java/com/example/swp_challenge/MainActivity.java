package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText text_curAchi = findViewById(R.id.text_curAchi_main);
    private Button button_Addchall = findViewById(R.id.button_Addchall_main);
    private Button button_detail = findViewById(R.id.button_detail_main);
    private Button img_cal = findViewById(R.id.img_cal_main);
    private Button img_setting = findViewById(R.id.img_setting_main);
    private Button img_box = findViewById(R.id.img_box_main);
    View text_Curdate = findViewById(R.id.text_Curdate_main);
    ProgressBar progressBar_reward = findViewById(R.id.progressBar_reward_main);
    RecyclerView recyclerView_Plan = findViewById(R.id.recycler_plan_main);
    RecyclerView recyclerView_Chall = findViewById(R.id.recycler_Chall_main);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        img_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                startActivity(intent);
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
}


