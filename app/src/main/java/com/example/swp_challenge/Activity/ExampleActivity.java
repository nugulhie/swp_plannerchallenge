package com.example.swp_challenge.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.swp_challenge.R;
import com.example.swp_challenge.dataController.PreferenceManager;

public class ExampleActivity extends AppCompatActivity {
    Button exitTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_example);
        exitTutorial = findViewById(R.id.button_gotomain);
        exitTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExampleActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
