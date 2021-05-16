package com.example.swp_challenge.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.swp_challenge.R;
import com.example.swp_challenge.dataController.PreferenceManager;
import com.example.swp_challenge.dataController.swp_databaseOpenHelper;

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


        swp_databaseOpenHelper dbHelper = new swp_databaseOpenHelper(this);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.insertUsername(username.getText().toString(), birth.getText().toString());
                PreferenceManager.setBoolean(IntroActivity.this,"check",false);
                PreferenceManager.setString(IntroActivity.this,"username",username.getText().toString());
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

                finish();
            }
        });


    }
}