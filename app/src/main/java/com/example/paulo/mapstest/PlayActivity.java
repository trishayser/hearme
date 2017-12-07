package com.example.paulo.mapstest;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    ImageButton play;
    ImageButton positive;
    ImageButton negative;

    int pos;
    int neg;
    int countBewertungen;
    int countpos;
    int countneg;

    Boolean isPlay = false;


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


        final Intent playbackServiceIntent = new Intent(this, AudioPlayer.class);
        play = (ImageButton) findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlay) {
                    stopService(playbackServiceIntent);
                    isPlay = false;
                    play.setImageResource(R.drawable.play);
                } else {
                    startService(playbackServiceIntent);
                    isPlay = true;
                    play.setImageResource(R.drawable.pause);
                }

            }

        });


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




}


