package com.example.swp_challenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

import java.text.SimpleDateFormat;//
//
public class IntroActivity extends AppCompatActivity {
    public Button next_btn;
    public EditText username;
    public EditText birth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        next_btn = findViewById(R.id.button_next21);
        username = findViewById(R.id.textview_username1);
        birth = findViewById(R.id.textView_birth);
        PreferenceManager.setString(this,"username",username.getText().toString());

        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insertUsername(username.getText().toString(), birth.getText().toString());
                PreferenceManager.setBoolean(IntroActivity.this,"check",false);
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}