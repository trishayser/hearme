package com.example.paulo.mapstest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;


public class NewRecordActivityActivity extends AppCompatActivity  {

    ImageButton recordPlay;
    Boolean isPlay = false;
    CountDownTimer testTimer;
    int maxLength;
    int progress = 0;

    public int getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(int currentLength) {
        this.currentLength = currentLength;
    }

    int currentLength = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_record_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    // Progress Bar


        // initiate progress bar and start button
        final ProgressBar simpleProgressBar = (ProgressBar) findViewById(R.id.simpleProgressBar);
        final LinearLayout record_player = (LinearLayout) findViewById(R.id.record_player);
        ImageButton recordButton = (ImageButton) findViewById(R.id.record);
        // perform click event on button
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    simpleProgressBar.setVisibility(View.VISIBLE);
                    return true;
                }
                    else if(event.getAction() == MotionEvent.ACTION_UP){

                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        record_player.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });


        Button abschicken = (Button) findViewById(R.id.abschicken);
        final Intent abschickenIntent = new Intent(this, MapsActivity.class);


        abschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(abschickenIntent);
            }
        });

        //Progress bar animtion
       // AudioPlayer test = new AudioPlayer();
        //int maxLength = test.getMediaPlayer().getDuration();

        maxLength = 27000; // leider manuell nachgesehen // in seconds

        final ProgressBar recordProgressbar = (ProgressBar) findViewById(R.id.progressBar_2_record);
        recordProgressbar.setProgress(0);

        // Ende


        final Intent recordPlaybackServiceIntent = new Intent(this, AudioPlayer.class);
        recordPlay = (ImageButton) findViewById(R.id.play_record);

        testTimer = new CountDownTimer(30000, 100) {


            public void onTick(long millisUntilFinished) {
                currentLength = currentLength + 100;
                progress = (currentLength/270);
                recordProgressbar.setProgress(progress);
                System.out.println(currentLength);
                System.out.println(progress);
                System.out.println("tick");
            }

            public void onFinish() {
                recordProgressbar.setProgress(0);
                this.cancel();
                System.out.println("Nachricht fertig abgespiel!");
            }
        };

        recordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlay){
                    stopService(recordPlaybackServiceIntent);
                    isPlay = false;
                    recordPlay.setImageResource(R.drawable.play);
                    recordProgressbar.setProgress(10);
                    testTimer.cancel();
                }
                else {
                    startService(recordPlaybackServiceIntent);
                    isPlay = true;
                    recordPlay.setImageResource(R.drawable.pause);
                    testTimer.start();




                }

            }

        });



    }

    }

