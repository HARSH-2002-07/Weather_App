package com.example.weather_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent ihome = new Intent(this, MainActivity2.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            startActivity(ihome);
            }
        }, 4000);
    }
}