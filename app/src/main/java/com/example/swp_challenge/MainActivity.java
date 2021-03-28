package com.example.swp_challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

//    TextView text_curAchi = findViewById(R.id.text_curAchi_main);
    Button button_Add_challenge;
    Button button_detail;
    Button img_cal;
    Button img_setting;
    Button img_box;
  //  TextView text_Curdate = findViewById(R.id.text_Curdate_main);
 //   ProgressBar progressBar_reward = findViewById(R.id.progressBar_reward_main);
  //  RecyclerView recyclerView_Plan = findViewById(R.id.recycler_plan_main);
  //  RecyclerView recyclerView_Chall = findViewById(R.id.recycler_Chall_main);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        button_Add_challenge = findViewById(R.id.button_Addchall_main);
        button_detail = findViewById(R.id.button_detail_main);
        img_cal = findViewById(R.id.img_cal_main);
        img_setting = findViewById(R.id.img_setting_main);
        img_box = findViewById(R.id.img_box_main);

        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder add_challenge = new AlertDialog.Builder(MainActivity.this);
                add_challenge.setIcon(R.mipmap.ic_launcher);
                add_challenge.setTitle("제목");
                add_challenge.setMessage("testing");
                final EditText challengeName = new EditText(MainActivity.this);
                final EditText challengeContents = new EditText(MainActivity.this);
                final EditText challengeDate = new EditText(MainActivity.this);
                RatingBar challengeRate = new RatingBar(MainActivity.this);
                add_challenge.setPositiveButton("추가하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String challname = challengeName.getText().toString();
                        String challcontents = challengeContents.getText().toString();
                        String challdate = challengeDate.getText().toString();
                        dialog.dismiss();
                    }
                });
                add_challenge.setPositiveButton("삭제하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
        });
//이 밑으로 전부 intent 함수
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


