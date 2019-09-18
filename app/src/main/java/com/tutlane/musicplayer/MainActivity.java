package com.tutlane.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 2000;
ImageView musicicon,icon;
TextView musictxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        musicicon=findViewById(R.id.homeicon);
        icon=findViewById(R.id.iconss);
        musictxt=findViewById(R.id.musictext);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this,Secondactivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}