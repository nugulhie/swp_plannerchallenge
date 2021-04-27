package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.controller.ChallengeController;
import  com.example.swp_challenge.controller.PlannerController;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
//import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

public class PopupActivity extends AppCompatActivity {    //popup 인텐트 만들려고 했는데 아직 안만듬

    Button btn_cancel_chall, btn_submit_chall;
    ImageButton btn_delete_chall;
    UserController user = new UserController();
    ChallengeController challenge = new ChallengeController();
    PlannerController plan = new PlannerController();
    swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
    SQLiteDatabase db = dbHelper.getWritableDatabase();

//    swp_databaseOpenHelper db = new swp_databaseOpenHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup 타이틀제거

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup);

        btn_cancel_chall = findViewById(R.id.btn_cancel_chall);
        btn_submit_chall = findViewById(R.id.btn_submit_chall);
        btn_delete_chall = findViewById(R.id.btn_delete_chall);

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
                //user.insertChallenge(/*challenge.getChall_id(), challenge.getRating(), challenge.getContents(), challenge.getChall_pass(), challenge.getDate()*/); //Todo 확인 버튼을 누르면 이 메소드가 실행되게
                Toast.makeText(getApplicationContext(), "Data is added!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

//    public void insertChallenge(){
//        db.challenge_insertColumn(challenge.getChall_id(), challenge.getRating(), challenge.getContents(), challenge.getChall_pass(), challenge.getDate());
//    }

    public boolean onTouchEvent(MotionEvent event) { //바깥 레이어 클릭해도 팝업 안 닫히게 하기.
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }
    public void insertChallenge(String contents, Date date, int challenge_id, float rating ){
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        values.put(swp_database.ChallengeDB.CHALLENGE_CONTENTS,contents);
        values.put(swp_database.ChallengeDB.CHALLENGE_DATE,dateFormat.format(date));
        values.put(swp_database.ChallengeDB.CHALLENGE_ID, challenge_id);
        values.put(swp_database.ChallengeDB.CHALLENGE_PASS,0);
        values.put(swp_database.ChallengeDB.CHALLENGE_RATING, rating);
        long newRowId = db.insert(swp_database.ChallengeDB.TABLE_NAME, null, values);
    }
    public void onBackPressed() {   //백스페이스 막기
        return;
    }
}