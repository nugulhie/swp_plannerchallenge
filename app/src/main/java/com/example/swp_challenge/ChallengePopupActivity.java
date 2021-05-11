//package com.example.swp_challenge;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.DialogFragment;
//
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.RatingBar;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//import com.example.swp_challenge.controller.UserController;
//import com.example.swp_challenge.controller.ChallengeController;
//import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
////import com.example.swp_challenge.dataController.swp_databaseOpenHelper;
////
//public class ChallengePopupActivity extends AppCompatActivity {    //popup 인텐트 만들려고 했는데 아직 안만듬
//    //
//    UserController user = UserController.getInstance();
//    ChallengeController challenge = ChallengeController.getInstance();
//
//    Button btn_cancel_chall, btn_submit_chall;
//    ImageButton btn_delete_chall, btn_startDate, btn_endDate;
//    EditText content;
//    String d1, d2, tempD;
//    Date date1, date2, tempDate;
//    RatingBar ratingbar;
//    TimePicker mtimePicker;
//    String hour, minute;
//    boolean b;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(getApplicationContext());
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//popup 타이틀제거
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_popup);
//
//        btn_cancel_chall = findViewById(R.id.button_cancel_chall);
//        btn_submit_chall = findViewById(R.id.button_submit_chall);
//        btn_delete_chall = findViewById(R.id.button_delete_chall);
//        btn_startDate = findViewById(R.id.button_date1_chall); //기간1
//        btn_endDate = findViewById(R.id.button_date2_chall);   //기간2
//        ratingbar = findViewById(R.id.ratingBar_challenge);   //도전과제 중요도
//        content = findViewById(R.id.content_challenge); //도전과제 내용
//        mtimePicker = findViewById(R.id.timePicker);
//
//        mtimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {   // 도전과제 시각
//            @Override
//            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
//
//            }
//        });
//
//        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {  // 도전과제 중요도 별점
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                if(ratingbar.getRating() < 1.0f) {
//                    ratingbar.setRating(1); //중요도 최소 별 1개로 설정.
//                }
//            }
//        });
//
//        btn_startDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = new DatePickerFragment();
//                newFragment.show(getSupportFragmentManager(),"datePicker");
//                Log.d("zzz123", "onClick: datePicker1_challenge");
//            }
//        });
//        btn_endDate.setOnClickListener(new View.OnClickListener() {  // 달력버튼2 선택시 달력2 dialog 생성 이벤트
//            @Override
//            public void onClick(View v) {
//                DialogFragment newFragment = new DatePicker2Fragment();
//                newFragment.show(getSupportFragmentManager(),"datePicker");
//                Log.d("zzz123", "onClick: datePicker2_challenge");
//            }
//        });
//
//        btn_cancel_chall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//                Log.d("zzz123", "onClick: cancel_challenge");
//            }
//        });
//        btn_delete_chall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //데이터 제거해주는 메소드 추가해주어야함.
//                Toast.makeText(getApplicationContext(),"Data deleted!", Toast.LENGTH_SHORT).show();
//                finish();
//                Log.d("zzz123", "onClick: delete_challenge");
//            }
//        });
//        btn_submit_chall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //challenge.setChallenge(/*rating, contents, chall_pass*/); //Todo 여기에다가 인텐트값 넘겨서 setChallenge 메소드 안에 넣는거 구현해야함.
//                if (content.length() > 0 & d1 != null & d2 != null)
//                {
//                    // ↓ date 크기 비교해서 순서 바꿔주기 ↓
//                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                    date1 = null;
//                    date2 = null;
//                    try {
//                        date1 = dateFormat.parse(d1);
//                        date2 = dateFormat.parse(d2);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    int compare = date1.compareTo(date2);
//                    if(compare > 0){
//                        tempDate = date1;   //date 형 바꾸기
//                        date1 = date2;
//                        date2 = tempDate;
//                        tempD = d1;         //str 형 바꾸기
//                        d1 = d2;
//                        d2 = tempD;
//                    }
//                    // ↑ date 크기 비교해서 순서 바꿔주기 ↑
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //tmepicker spinner 도전과제 시각 불러오기
//                        hour = mtimePicker.getHour() + "";
//                        minute = mtimePicker.getMinute() + "";
//                    } else {
//                        hour = mtimePicker.getCurrentHour() + "";
//                        minute = mtimePicker.getCurrentMinute() + "";
//                    }
//                    challenge.setChallenge(ratingbar.getRating(),content.getText().toString());
//                    dbHelper.insertChallenge(challenge.getContents(), challenge.getDate(), challenge.getRating());
//                    Log.d("159753", "onClick: insertChallenge"+challenge.getContents());
//                    Toast.makeText(getApplicationContext(), "할일: " + content.getText().toString() +", 중요도: "+ (int)ratingbar.getRating() +
//                            ", 기간(str): " +d1 +" ~ "+ d2+", 시각: "+hour+":"+minute+", 기간(date):"+date1.toString() +" ~ "+ date2.toString(), Toast.LENGTH_SHORT).show();
//                    MainActivity.mActivity.finish();
//                    Intent intent = new Intent(ChallengePopupActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    Log.d("zzz123", "onClick: " + "insert_challenge");
//                    finish();
//                }
//                else {
//                    //dbHelper.updateChallenge(challenge.getContents(),"운동들어오기",challenge.getDate(),2.5f);
//                    //Log.d("159753", "onClick: updateChallenge"+"운동들어오기");
//                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }
//        });
//    }
//    //↓ 시작일, 종료일 불러오기 ↓
//    public void processDatePickerResult(int year, int month, int day){  //기간1 날짜데이터 불러오기 이벤트
//        String month_string = Integer.toString(month+1);
//        String day_string = Integer.toString(day);
//        String year_string = Integer.toString(year);
//        String dateMessage = (year_string + "-" + month_string + "-" + day_string);
//
//        d1 = dateMessage;
//        ((TextView) findViewById(R.id.textView_date1_challenge)).setText(d1);
//        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
//    }
//    public void processDatePicker2Result(int year, int month, int day){  //기간2 날짜데이터 불러오기 이벤트
//        String month_string = Integer.toString(month+1);
//        String day_string = Integer.toString(day);
//        String year_string = Integer.toString(year);
//        String dateMessage = (year_string + "-" + month_string + "-" + day_string);
//
//        d2 = dateMessage;
//        ((TextView) findViewById(R.id.textView_date2_challenge)).setText(d2);
//        Toast.makeText(this,"Date: " + dateMessage,Toast.LENGTH_SHORT).show();
//    }
//    // ↑ 시작일, 종료일 불러오기 ↑
//    public boolean onTouchEvent(MotionEvent event) { //바깥 레이어 클릭해도 팝업 안 닫히게 하기.
//        if(event.getAction()==MotionEvent.ACTION_OUTSIDE) {
//            return false;
//        }
//        return true;
//    }
//
//    public void onBackPressed() {   //백스페이스 막기
//        return;
//    }
//}
