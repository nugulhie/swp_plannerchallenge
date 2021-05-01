package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.controller.ChallengeController;
import  com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
//import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
//
public class ChallengePopupActivity extends AppCompatActivity {    //popup 인텐트 만들려고 했는데 아직 안만듬
    //
    UserController user = UserController.getInstance();
    ChallengeController challenge = ChallengeController.getInstance();

    Button btn_cancel_chall, btn_submit_chall;
    ImageButton btn_delete_chall, btn_startDate, btn_endDate;
    EditText content;
    String d1, d2;
    RatingBar ratingbar;
    boolean b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup 타이틀제거

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        btn_cancel_chall = findViewById(R.id.btn_cancel_chall);
        btn_submit_chall = findViewById(R.id.btn_submit_chall);
        btn_delete_chall = findViewById(R.id.btn_delete_chall);
        btn_startDate = findViewById(R.id.btn_date1_chall); //기간1
        btn_endDate = findViewById(R.id.btn_date2_chall);   //기간2
        ratingbar = findViewById(R.id.ratingBar);   //도전과제 중요도
        content = findViewById(R.id.content_chall); //도전과제 내용

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  //중요도 별점
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(ratingbar.getRating() < 1.0f) {
                    ratingbar.setRating(1); //중요도 최소 별 1개로 설정.
                }
            }
        });

        btn_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });
        btn_endDate.setOnClickListener(new View.OnClickListener() {  // 달력버튼2 선택시 달력2 dialog 생성 이벤트
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePicker2Fragment();
                newFragment.show(getSupportFragmentManager(),"datePicker");
            }
        });

        btn_cancel_chall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_delete_chall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 제거해주는 메소드 추가해주어야함.
                Toast.makeText(getApplicationContext(),"Data deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btn_submit_chall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 추가해주는 메소드 추가해주어야함.
                //challenge.setChallenge(/*rating, contents, chall_pass*/); //Todo 여기에다가 인텐트값 넘겨서 setChallenge 메소드 안에 넣는거 구현해야함.
                if (content.length() > 0 )//나중에 날짜 지정 조건 추가해주기
                {
                    dbHelper.insertChallenge(challenge.getContents(), challenge.getDate(), challenge.getRating());
                    Log.d("159753", "onClick: insertChallenge"+challenge.getContents());
                    Toast.makeText(getApplicationContext(), content.getText().toString() +", 중요도 : "+ ratingbar.getRating() + "Data is added!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    dbHelper.updateChallenge(challenge.getContents(),"운동들어오기",challenge.getDate(),2.5f);
                    Log.d("159753", "onClick: updateChallenge"+"운동들어오기");
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    //↓ 시작일, 종료일 불러오기 ↓
    public void processDatePickerResult(int year, int month, int day){  //기간1 날짜데이터 불러오기 이벤트
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d1 = dateMessage;
        ((TextView) findViewById(R.id.startDate)).setText(d1);
        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
    }
    public void processDatePicker2Result(int year, int month, int day){  //기간2 날짜데이터 불러오기 이벤트
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (year_string + "-" + month_string + "-" + day_string);

        d2 = dateMessage;
        ((TextView) findViewById(R.id.endDate)).setText(d2);
        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
    }
    // ↑ 시작일, 종료일 불러오기 ↑
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
