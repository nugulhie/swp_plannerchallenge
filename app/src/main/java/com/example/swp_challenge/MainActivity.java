package com.example.swp_challenge;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //    TextView text_curAchi = findViewById(R.id.text_curAchi_main);
    public ImageButton button_Add_challenge;
    //public ImageButton button_detail;
    public ImageButton img_cal;
    //  TextView text_Curdate = findViewById(R.id.text_Curdate_main);
    //   ProgressBar progressBar_reward = findViewById(R.id.progressBar_reward_main);
    //  RecyclerView recyclerView_Plan = findViewById(R.id.recycler_plan_main);
    //  RecyclerView recyclerView_Chall = findViewById(R.id.recycler_Chall_main);

    Spinner spinner;
    ImageButton btn_menu;
    String menu_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        button_Add_challenge = findViewById(R.id.button_Addchall_main);
        //button_detail = findViewById(R.id.button_detail_main);
        img_cal = findViewById(R.id.img_cal_main);

        //@@@@@메뉴 스피너@@@@@@@//
        btn_menu = findViewById(R.id.btn_more_main);
        final String[] menu = {"홈", "상자", "칭호", "설정"};  //메뉴 아이템 항목
        spinner = findViewById(R.id.spinner_main);  //스피너 초기화
        ArrayAdapter menuAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, menu);  //menu 어댑터 생성
        menuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(menuAdapter);
        spinner.setSelection(0);

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
                        /*Intent intent = new Intent (MainActivity.this, MainActivity.class);
                        startActivity(intent);*/ //동일 페이지라 설정X
                        Toast.makeText(getApplicationContext(),"This is that page!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                    case "상자":
                        Intent intent = new Intent(MainActivity.this, BoxActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "칭호":
                        intent = new Intent(MainActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case "설정":
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;

                }
            }
        });
        //@@@@@메뉴 스피너 끝@@@@@2//


//이 밑으로 전부 intent 함수
//        button_detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
//                startActivity(intent);  //자세히 보기 누를시 캘린더 화면으로 넘기기
//            }
//        });

        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent); // 캘린더 아이콘 누를시 캘린더 화면으로 넘기기
                finish();
            }
        });
        /*
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
        });*/
        button_Add_challenge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                startActivity(intent);
            }
        });


    }
/*
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
    }*/

}

