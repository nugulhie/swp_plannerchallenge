package com.example.swp_challenge.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.example.swp_challenge.R;
import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.swp_database;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.util.List;

//////
public class SettingsActivity extends AppCompatActivity {
    ImageButton img_cal;
    TextView user;
    EditText insert_user;
    ImageButton btn_menu;
    Button submit_name;
    TextView achives;
    public String currentAchive, username;
    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";

    private static String CHANNEL_ID2 = "channel2";
    private static String CHANNEL_NAME2 = "Channel2;";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(SettingsActivity.this);
        loadDB();
        /*if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
//-----------------------------------------------------------------------------------------------
 /*       Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();
            }
        });
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                showNoti2();
            }
        });
    }*/
//    Timer timer = new Timer();
//    TimerTask timerTask = new TimerTask() {
//        public void run() {
//            makePostToServer()
//        }
//    };
//timer.scheduleAtFixedRate(timerTask, getDate(), 1000 * 60 * 60 * 24); // 24 h
//    private Date getDate() {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 1);
//        cal.set(Calendar.HOUR_OF_DAY, 2);
//        cal.set(Calendar.MINUTE, 0);
//        cal.set(Calendar.SECOND, 0);
//        return cal.getTime();
//    }


        //   notificationManager.notify(1, builder.build());
//NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//    Intent intent = new Intent(this, MainActivity.class);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//    Notification.Builder builder = new Notification.Builder(this);
        insert_user = findViewById(R.id.editText_userName);
        achives = findViewById(R.id.textView_selectedAchieve);
        submit_name = findViewById(R.id.button_userName_setting);
        String check = PreferenceManager.getString(SettingsActivity.this, "username");
        user = findViewById(R.id.textView_userName);
        user.setText(check);
        achives.setText(currentAchive);
        //--------------------------------------------------------------------------------------------


        img_cal=findViewById(R.id.button_calendar_setting);
        btn_menu = findViewById(R.id.button_menu_setting);


        img_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CalendarActivity.class);
                startActivity(intent);
                Log.d("zzz123", "onClick: calendarButton_setting");
                finish();
            }
        });
        submit_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insert_name = insert_user.getText().toString();
                PreferenceManager.setString(SettingsActivity.this,"username",insert_name);
                dbHelper.updateUsername(check, insert_name);
                user.setText(insert_name);
                insert_user.setText("");
            }
        });
    }

    public void onPopupMenuButtonClick(View button) {     //더보기 버튼 클릭 시 팝업메뉴 생성
        PopupMenu popupMenu = new PopupMenu(this, btn_menu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_menu1:
                        Intent intent = new Intent(SettingsActivity.this, BoxActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: boxMenu_setting");
                        finish();
                        break;
                    case R.id.action_menu2:
                        intent = new Intent(SettingsActivity.this, AchivementActivity.class);
                        startActivity(intent);
                        Log.d("zzz123", "onMenuItemClick: achieveMenu_setting");
                        finish();
                        break;
                    case R.id.action_menu3:
                        Toast.makeText(getApplicationContext(),"현재 페이지입니다!", Toast.LENGTH_SHORT).show(); //동일 페이지에 출력
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void showNoti1() {


        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.star_on));
        builder.setSmallIcon(android.R.drawable.star_on);
        builder.setContentTitle("오늘의 일정");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setNumber(999);

        Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);

        for(int i = 0; i < 10 ; i++)
        {
            inboxStyle.addLine("DB data" +i); // DB에서 일정 갯수 확인 후 for문 돌려야 함
        }

        inboxStyle.setSummaryText("더 보기");
        builder.setStyle(inboxStyle);

        notificationManager.notify(1, builder.build());


    }
/*



    public void showNoti2() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID2) == null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID2, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));


                builder = new NotificationCompat.Builder(this, CHANNEL_ID2);
            }
        } else {
            builder = new NotificationCompat.Builder(this);
        }
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("오늘의 챌린지는 다 완수하셨나요?");
        //builder.setContentText("알림 메시지2");
        builder.setSmallIcon(android.R.drawable.ic_menu_view);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);

        manager.notify(2, builder.build());

*/



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }

    public void loadDB(){
        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(SettingsActivity.this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String achive = "";
        String username= "";
        Cursor usercursor = db.query(
                swp_database.UserDB.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        while (usercursor.moveToNext()){
            achive = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.CURRENT_ACHIVE));
            username = usercursor.getString(usercursor.getColumnIndexOrThrow(swp_database.UserDB.USER_NAME));
        }
        currentAchive = achive;
        this.username = username;
    }
}