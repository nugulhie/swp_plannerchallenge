package com.example.swp_challenge;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.swp_challenge.controller.UserController;
import androidx.appcompat.app.AppCompatActivity;
import com.example.swp_challenge.controller.BoxController;
public class PopupBoxActivity extends AppCompatActivity {

    Button btn_popup;
    BoxController box = new BoxController();
    UserController user = new UserController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup 타이틀제거

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_popup_box);

        btn_popup = findViewById(R.id.btn_ok_popupbox);
        btn_popup.setOnClickListener(new OnClickListener() {
            @Override

            public void onClick(View v) {

                box.boxOpen(user);
                finish();
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