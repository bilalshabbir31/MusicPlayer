package com.tutlane.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Secondactivity extends AppCompatActivity {
Button allsongbtn,abtbtn;
ImageView allsongicon,abouticon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        allsongbtn=findViewById(R.id.allsongbtn);
        abtbtn=findViewById(R.id.aboutbtn);
        allsongicon=findViewById(R.id.songicon);
        abouticon=findViewById(R.id.abouticon);
    }
    public void songsclick(View view)
    {
        Intent i=new Intent(Secondactivity.this,Allsongs.class);
        startActivity(i);
        finish();
    }
    public void aboutclick(View view)
    {

    }
}
