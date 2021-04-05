package com.example.swp_challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //    TextView text_curAchi = findViewById(R.id.text_curAchi_main);
    Button button_Add_challenge;
    Button button_detail;
    Button img_cal;
    Button img_setting;
    Button img_box;
    Button img_home;
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


//이 밑으로 전부 intent 함수
        button_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);  //자세히 보기 누를시 캘린더 화면으로 넘기기
            }
        });

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent); // 캘린더 아이콘 누를시 캘린더 화면으로 넘기기
            }
        });
        img_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                startActivity(intent); // 상자 아이콘 누를시 상자 화면으로 넘기기
            }
        });
        img_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent); // 설정 아이콘 누를시 설정 화면으로 넘기기
            }
        });
        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PopupActivity.class);
                intent.putExtra("title", "공지사항");
                startActivityForResult(intent, 1); // +버튼 누르면 팝업 생성
            }
        });
        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });
    }

    void show() { //팝업 메소드
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("AlertDialog Title");
        builder.setMessage("AlertDialog Content");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "예를 선택했습니다.", Toast.LENGTH_LONG).show();
                    }
    });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
        }
    });
        builder.show();
    }

}


