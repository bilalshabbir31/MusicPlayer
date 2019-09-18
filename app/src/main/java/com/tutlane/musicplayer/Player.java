package com.tutlane.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


public class Player extends AppCompatActivity {
    Button plays, next, previous;
    SeekBar mSeekBar;
    TextView curTime;
    TextView totTime;
    TextView songTitle;
    static MediaPlayer mymediaplayer;
    int position;
    ArrayList<File> mysongs;
    Intent playerData;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mSeekBar = findViewById(R.id.seekbar);
        songTitle = findViewById(R.id.label);
        curTime = findViewById(R.id.curTime);
        totTime = findViewById(R.id.totalTime);

        plays= findViewById(R.id.play);
        previous= findViewById(R.id.previousbtn);
        next= findViewById(R.id.nextbtn);
        if (mymediaplayer != null) {
            mymediaplayer.stop();
        }

        playerData = getIntent();
        bundle = playerData.getExtras();

        mysongs = (ArrayList) bundle.getParcelableArrayList("songss");
        position = bundle.getInt("pos", 0);
        initPlayer(position);


        plays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (position <= 0) {
                    position = mysongs.size() - 1;
                } else {
                    position--;
                }

                initPlayer(position);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < mysongs.size() - 1) {
                    position++;
                } else {
                    position = 0;

                }
                initPlayer(position);
            }
        });

    }


    private void initPlayer(final int position) {

        if (mymediaplayer != null && mymediaplayer.isPlaying()) {
            mymediaplayer.reset();
        }

        String sname = mysongs.get(position).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        songTitle.setText(sname);
        Uri songResourceUri = Uri.parse(mysongs.get(position).toString());

        mymediaplayer = MediaPlayer.create(getApplicationContext(), songResourceUri); // create and load mediaplayer with song resources
        mymediaplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime = createTimeLabel(mymediaplayer.getDuration());
                totTime.setText(totalTime);
                mSeekBar.setMax(mymediaplayer.getDuration());
                mymediaplayer.start();
                plays.setBackgroundResource(R.drawable.pause);

            }
        });

        mymediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int curSongPoition = position;
                // code to repeat songs until the
                if (curSongPoition < mysongs.size() - 1) {
                    curSongPoition++;
                    initPlayer(curSongPoition);
                } else {
                    curSongPoition = 0;
                    initPlayer(curSongPoition);
                }

                //playIcon.setImageResource(R.drawable.ic_play_arrow_black_24dp);

            }
        });


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser) {
                    mymediaplayer.seekTo(progress);
                    mSeekBar.setProgress(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mymediaplayer != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mymediaplayer.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mymediaplayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            mSeekBar.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            curTime.setText(cTime);
        }
    };


    private void play() {

        if (mymediaplayer != null && !mymediaplayer.isPlaying()) {
            mymediaplayer.start();
            plays.setBackgroundResource(R.drawable.pause);
        } else {
            pause();
        }

    }

    private void pause() {
        if (mymediaplayer.isPlaying()) {
            mymediaplayer.pause();
            plays.setBackgroundResource(R.drawable.playarrow);

        }

    }




    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;


    }
}
