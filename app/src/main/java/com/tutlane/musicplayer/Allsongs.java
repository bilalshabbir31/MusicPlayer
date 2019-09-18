package com.tutlane.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class Allsongs extends AppCompatActivity {
    ListView allsongs;
    String []item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsongs);
        allsongs=findViewById(R.id.listview);
        permission();
    }
    public void permission()
    {
        Dexter.withActivity(Allsongs.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                display();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }
    public ArrayList<File> findsong(File file)
    {
        ArrayList<File>arrayList=new ArrayList<>();
        File[] files=file.listFiles();
        for(File singlefiles:files)
        {
            if(singlefiles.isDirectory()&&!singlefiles.isHidden())
            {
                arrayList.addAll(findsong(singlefiles));
            }
            else
            {
                if(singlefiles.getName().endsWith(".mp3")||singlefiles.getName().endsWith(".wav")||singlefiles.getName().endsWith("m4a")||singlefiles.getName().endsWith(".SE")||singlefiles.getName().endsWith(".Com")||singlefiles.getName().endsWith(".com")||singlefiles.getName().endsWith(".PK")||singlefiles.getName().endsWith("org")||singlefiles.getName().endsWith(".io")||singlefiles.getName().endsWith("INFO")||singlefiles.getName().endsWith("Info")||singlefiles.getName().endsWith(".Org")||singlefiles.getName().endsWith(".live")||singlefiles.getName().endsWith(".Cool")||singlefiles.getName().endsWith(".Co")||singlefiles.getName().endsWith(".Pk")||singlefiles.getName().endsWith(".info")||singlefiles.getName().endsWith(".Se")||singlefiles.getName().endsWith("CoM"))
                {
                    arrayList.add(singlefiles);
                }
            }
        }
        return arrayList;
    }
    void display()
    {
        final ArrayList<File>mysong=findsong(Environment.getExternalStorageDirectory());
        item=new String[mysong.size()];
        for(int i=0;i<mysong.size();i++)
        {
            item[i]=mysong.get(i).getName().toString().replace(".mp3","").replace(".wav","").replace(".m4a","").replace(".SE","").replace(".Com","").replace(".com","").replace(".PK","").replace(".org","").replace(".io","").replace(".INFO","").replace(".Info","").replace(".Org","").replace(".live","").replace(".Cool","").replace(".Co","").replace(".Pk","").replace(".info","").replace(".Se","").replace("CoM","");
            ArrayAdapter<String> myadapter=new ArrayAdapter<String>(Allsongs.this,android.R.layout.simple_list_item_1,item);
            allsongs.setAdapter(myadapter);
            allsongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String songName=allsongs.getItemAtPosition(i).toString();
                    startActivity(new Intent(getApplicationContext(),Player.class).putExtra("songss",mysong).putExtra("songnames",songName).putExtra("pos",i));
                }
            });
        }
    }
}
