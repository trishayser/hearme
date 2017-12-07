package com.example.paulo.mapstest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class PlayActivity extends AppCompatActivity {

    ImageButton positive;
    ImageButton negative;

    int pos;
    int neg;
    int countBewertungen;
    int countpos;
    int countneg;

    Boolean isPlay = false;

    ImageButton play;
    CountDownTimer testTimer;

    int maxLength;
    long maxLengthLong;
    long test2 = 2000;
    int test3 = 0;
    String duration;
    int progress = 0;
    int number;
    int currentLength = 0;

    boolean mStartPlaying = true; // Für Play

    private static String mFileName = null;
    private AudioRecordTest.PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;


    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        positive = (ImageButton) findViewById(R.id.posButton);
        negative = (ImageButton) findViewById(R.id.negButton);

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("positive");
                bewertung(true);
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("negative");
                bewertung(false);
            }
        });


        final Intent recordPlaybackServiceIntent = new Intent(this, AudioPlayer.class);
        play = (ImageButton) findViewById(R.id.play);

        final ProgressBar playProgressbar = (ProgressBar) findViewById(R.id.playProgressbar);
        playProgressbar.setProgress(0);

        testTimer = new CountDownTimer(30000, 100) {


            public void onTick(long millisUntilFinished) {
                System.out.println("Länge in Timer " + maxLength);
                System.out.println("Test2 " + test2);
                currentLength = currentLength + 100;
                progress = ((currentLength*100)/maxLength);
                playProgressbar.setProgress(progress);
                System.out.println(currentLength);
                System.out.println(progress);
                System.out.println("tick");

                test3 = test3+100;

                if(test3 >= maxLength){
                    currentLength = 0;
                    test3 = 0;
                    onFinish();
                }

            }

            public void onFinish() {
                playProgressbar.setProgress(0);
                play.setImageResource(R.drawable.play);
                onPlay(mStartPlaying);
                mStartPlaying = true;
                this.cancel();
                System.out.println("Nachricht fertig abgespiel!");
            }
        };

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onPlay(mStartPlaying);
                if (mStartPlaying) {

                    MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                    mmr.setDataSource(mFileName);
                    duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    mmr.release();

                    maxLength = Integer.parseInt(duration);
                    maxLengthLong = Long.valueOf(duration);


                    play.setImageResource(R.drawable.pause);
                    System.out.println("Länge bei Playbutton " + maxLength);


                    testTimer.start();

                } else {
                    play.setImageResource(R.drawable.play);
                    playProgressbar.setProgress(0);
                    testTimer.cancel();
                    progress = 0;
                    currentLength = 0;
                }
                mStartPlaying = !mStartPlaying;



            }

        });

        // Record Test
        // Record to the external cache directory for visibility
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);



        // Record Test Ende



        Button antworten = (Button) findViewById(R.id.antworten);
        final Intent antwortenIntent = new Intent(this, NewRecordActivityActivity.class);


        antworten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(antwortenIntent);
            }
        });
    }

       public void bewertung (boolean positive){
           countBewertungen++;

           if (positive){
               countpos++;
           }
           else{
               countneg++;
           }
           pos = (countpos*100)/countBewertungen;
           neg = 100-pos;

        System.out.println("Positive "+ pos + "% " + "  Negative " + neg +"% ");

        final TextView textViewLike = (TextView) findViewById(R.id.like_percent);
        textViewLike.setText(pos + "%");

        final TextView textViewDislike = (TextView) findViewById(R.id.dislike_percent);
        textViewDislike.setText(neg + "%");



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

    // Test Record
    @Override
    public void onStop() {
        super.onStop();

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }


}


