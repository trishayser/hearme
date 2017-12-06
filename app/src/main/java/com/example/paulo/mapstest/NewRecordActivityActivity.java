package com.example.paulo.mapstest;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.IOException;


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

    boolean mStartRecording = true; // Für Record
    boolean mStartPlaying = true; // Für Play






    /// Recordtest
    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    private AudioRecordTest.RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private AudioRecordTest.PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted = false;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }



//    class PlayButton extends Button {
//        boolean mStartPlaying = true;
//
//        OnClickListener clicker = new OnClickListener() {
//            public void onClick(View v) {
//                onPlay(mStartPlaying);
//                if (mStartPlaying) {
//                    setText("Stop playing");
//                } else {
//                    setText("Start playing");
//                }
//                mStartPlaying = !mStartPlaying;
//            }
//        };
//
//        public PlayButton(Context ctx) {
//            super(ctx);
//            setText("Start playing");
//            setOnClickListener(clicker);
//        }
//    }

    //// Record Test Ende







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

        final Intent test = new Intent(this, AudioRecordTest.class);

        // perform click event on button
        recordButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    simpleProgressBar.setVisibility(View.VISIBLE);



                    onRecord(mStartRecording);
                mStartRecording = false;



                    return true;
                }
                    else if(event.getAction() == MotionEvent.ACTION_UP){

                        simpleProgressBar.setVisibility(View.INVISIBLE);
                        record_player.setVisibility(View.VISIBLE);
                        onRecord(mStartRecording);
                        mStartRecording = true;

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

                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    recordPlay.setImageResource(R.drawable.pause);
                    testTimer.start();
                } else {
                    recordPlay.setImageResource(R.drawable.play);
                    recordProgressbar.setProgress(0);
                    testTimer.cancel();
                    progress = 0;
                    currentLength = 0;
                }
                mStartPlaying = !mStartPlaying;



             // Original

//                if(isPlay){
//                    stopService(recordPlaybackServiceIntent);
//                    isPlay = false;
//                    recordPlay.setImageResource(R.drawable.play);
//                    recordProgressbar.setProgress(0);
//                    testTimer.cancel();
//                    progress = 0;
//                    currentLength = 0;
//                }
//                else {
//                    startService(recordPlaybackServiceIntent);
//                    isPlay = true;
//                    recordPlay.setImageResource(R.drawable.pause);
//                    testTimer.start();
//
//                }

            }

        });

    // Record Test
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);



    // Record Test Ende
    }
    // Test Record
    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }



    // Test Record Ende



    }

