package com.example.swp_challenge;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.swp_challenge.controller.UserController;

import androidx.appcompat.app.AppCompatActivity;
import com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
//import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
////
public class PlanPopupActivity extends AppCompatActivity {    //popup 인텐트 만들려고 했는데 아직 안만듬
    Button btn_cancel_schedule, btn_submit_schedule;
    ImageButton btn_delete_schedule;
    String category_item;
    int mYear, mMonth, mDay;
    EditText content;
    Spinner spinner_category;
    PlannerController plan = PlannerController.getInstance();
    UserController user = UserController.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup 타이틀제거

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup2);

        content = findViewById(R.id.content_plan);
        btn_cancel_schedule = findViewById(R.id.btn_cancel_schedule);
        btn_submit_schedule = findViewById(R.id.btn_submit_schedule);
        btn_delete_schedule = findViewById(R.id.btn_delete_schedule);

        Intent getIntent = getIntent();
        mYear = getIntent.getIntExtra("year", 0);
        mMonth = getIntent.getIntExtra("month", 0);
        mDay = getIntent.getIntExtra("day", 0);

        Toast.makeText(getApplicationContext(), "선택한 날짜 : " + mYear +"-"+ mMonth +"-"+ mDay, Toast.LENGTH_SHORT).show(); //선택한 날짜 출력

        // ↓ 일정 카테고리 스피너 ↓ //
        final String[] category = {"약속", "공부", "운동", "시험", "기타"};
        spinner_category = findViewById(R.id.spinner_popup2);
        ArrayAdapter categoryAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(categoryAdapter);
        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_item = (String) spinner_category.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // ↑ 일정 카테고리 스피너 ↑ //

        //버튼 클릭리스너 일단은 뒤로가기로 설정했는데 입력이나 삭제에서는 새 액티비티 생성하는 것으로 바꾸어야 할 것 같음
        btn_cancel_schedule.setOnClickListener(new View.OnClickListener() { //취소버튼
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_delete_schedule.setOnClickListener(new View.OnClickListener() { //삭제버튼
            @Override
            public void onClick(View v) {
                //데이터 제거해주는 메소드 추가해주어야함.
                Toast.makeText(getApplicationContext(),"Data deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btn_submit_schedule.setOnClickListener(new View.OnClickListener() { //입력버튼
            @Override
            public void onClick(View v) {
                //데이터 추가해주는 메소드 추가해주어야함. //Todo 팝업 인텐트에서 setplan 메소드에 값 넘겨주는 구문 작성 필요
                //plan.setPlan(content, category); //일정 추가 메소드
                if (content.length() > 0) {
                    plan.setPlan(content.getText().toString(), category_item);
                    dbHelper.insertPlan(plan.getPlanContents(),plan.getCategory(),plan.getDate());
                    Toast.makeText(getApplicationContext(), "날짜 : "+mYear+"년 "+mMonth+"월 "+mDay+"일 "
                            + ", 카테고리 : "+category_item + ", 내용 : "+ content.getText().toString(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }



    public boolean onTouchEvent(MotionEvent event) { //바깥 레이어 클릭해도 팝업 안 닫히게 하기.
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
    public void onBackPressed() {   //백스페이스 막기
        return;
    }

}