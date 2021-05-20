package com.example.swp_challenge.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.swp_challenge.R;
import com.example.swp_challenge.controller.UserController;
import com.example.swp_challenge.dataController.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//
////
public class SplashActivity extends AppCompatActivity { //스플래시 화면 메소드 여기는 개발 완료 수정 x
        boolean temp = true;
        Date date = Calendar.getInstance().getTime();
        UserController user = UserController.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        temp = PreferenceManager.getBoolean(this,"check");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        PreferenceManager.setString(this,"today",day.format(date));
        Log.d("159753", "onCreate: "+day.format(date));

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