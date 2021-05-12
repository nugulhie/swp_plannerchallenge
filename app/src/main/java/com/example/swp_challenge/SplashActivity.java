package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

////
public class SplashActivity extends AppCompatActivity { //스플래시 화면 메소드 여기는 개발 완료 수정 x
        boolean temp = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        temp = PreferenceManager.getBoolean(this,"check");
        Log.d("159753", "onCreate: "+Boolean.toString(PreferenceManager.getBoolean(this,"check")));

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(temp){
                    Intent intent = new Intent(getApplicationContext(),IntroActivity.class); //사용자가 처음 어플을 사용할 때
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent =new Intent(getApplicationContext(), MainActivity.class); //사용자가 어플을 사용 해봤을 때
                    startActivity(intent);
                    finish();
                }
            }
        },3000);
    }
    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }
}