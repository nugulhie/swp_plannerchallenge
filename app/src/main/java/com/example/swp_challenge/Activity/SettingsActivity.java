package com.example.swp_challenge.Activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.preference.PreferenceFragmentCompat;

import com.example.swp_challenge.R;

//////
public class SettingsActivity extends AppCompatActivity {
    ImageButton img_cal;
    ImageButton btn_menu;


    private static String CHANNEL_ID = "channel1";
    private static String CHANNEL_NAME = "Channel1";
    private static String CHANNEL_ID2 = "channel2";
    private static String CHANNEL_NAME2 = "Channel2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);
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



        //금일 일정 알려주는 알람.
        private void aram_plan() {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(manager.getNotificationChannel(CHANNEL_ID) == null) {
                    manager.createNotificationChannel(new NotificationChannel(
                            CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                    ));
                }
                builder = new Notification.Builder(this, CHANNEL_ID);

            }else{
                //builder = new Notification.Builder(this);
            }

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setSmallIcon(android.R.drawable.ic_menu_view);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);
            builder.setContentTitle("금일 일정");

            Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);
            for (int i = 0; i < 10 ; i++)
            {
                inboxStyle.addLine("DB data" +i);
            }
            inboxStyle.setSummaryText("더보기");
            builder.setStyle(inboxStyle);

            Notification noti = builder.build();
            manager.notify(1,noti);
        }
        //캘린더 다했는지 물어보는 알람.
        private void aram_cal() {

            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(manager.getNotificationChannel(CHANNEL_ID2) == null) {
                    manager.createNotificationChannel(new NotificationChannel(
                            CHANNEL_ID2, CHANNEL_NAME2, NotificationManager.IMPORTANCE_DEFAULT
                    ));
                }
                builder = new NotificationCompat.Builder(this, CHANNEL_ID2);

            }else{
                //builder = new NotificationCompat.Builder(this);
            }

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setDefaults(Notification.DEFAULT_SOUND);
            builder.setContentTitle("금일 챌린지는 다 하셨나요?");
            builder.setSmallIcon(android.R.drawable.ic_menu_view);
            builder.setAutoCancel(true);
            builder.setContentIntent(pendingIntent);

            Notification noti = builder.build();
            manager.notify(2,noti);

        }


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

//    public void showNoti1() {
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder builder = new Notification.Builder(this);
//
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.star_on));
//        builder.setSmallIcon(android.R.drawable.star_on);
//        builder.setContentTitle("오늘의 일정");
//        builder.setWhen(System.currentTimeMillis());
//        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
//        builder.setContentIntent(pendingIntent);
//        builder.setAutoCancel(true);
//        builder.setNumber(999);
//
//        Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);
//
//        for(int i = 0; i < 10 ; i++)
//        {
//            inboxStyle.addLine("DB data" +i); // DB에서 일정 갯수 확인 후 for문 돌려야 함
//        }
//
//        inboxStyle.setSummaryText("더 보기");
//        builder.setStyle(inboxStyle);
//
//        notificationManager.notify(1, builder.build());
//
//
//    }




    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}