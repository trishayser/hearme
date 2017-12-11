package com.thkoeln.paulo.hearme;

import java.io.Serializable;

/**
 * Created by pascal on 10.12.17.
 */

public class PlayerItem  implements Serializable {


    private String name = "";
    private String mFileName = "";
    //private AudioRecordTest audioRecordTest;

        public PlayerItem(String name, String mFileName) {
            this.setName(name);
            this.setmFileName(mFileName);
            //this.audioRecordTest = new AudioRecordTest(this.mFileName);

        }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }
//    public AudioRecordTest getAudioRecordTest() {
//        return audioRecordTest;
//    }
//
//    public void setAudioRecordTest(AudioRecordTest audioRecordTest) {
//        this.audioRecordTest = audioRecordTest;
//    }


//    public void bewertung (boolean positive){
//        countBewertungen++;
//
//        if (positive){
//            countpos++;
//        }
//        else{
//            countneg++;
//        }
//        pos = (countpos*100)/countBewertungen;
//        neg = 100-pos;
//
//        System.out.println("Positive "+ pos + "% " + "  Negative " + neg +"% ");
//
//        final TextView textViewLike = (TextView)findViewById(R.id.like_percent);
//        textViewLike.setText(pos + "%");
//
//        final TextView textViewDislike = (TextView)findViewById(R.id.dislike_percent);
//        textViewDislike.setText(neg + "%");
//
//
//
//    }



//        //--------------------AudioRecordTest-Klasse----------------------------------------
//        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
//        mFileName += "/audiorecordtest.3gp";
//        audioRecordTest = new AudioRecordTest(mFileName);
//        //------------------------------------------------------------
//
//        positive = (ImageButton) findViewById(R.id.posButton);
//        negative = (ImageButton) findViewById(R.id.negButton);
//
//        positive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("positive");
//                bewertung(true);
//            }
//        });
//
//        negative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("negative");
//                bewertung(false);
//            }
//        });
//
//
//        final Intent recordPlaybackServiceIntent = new Intent(this, AudioPlayer.class);
//        play = (ImageButton) findViewById(R.id.play);
//
//        final ProgressBar playProgressbar = (ProgressBar) findViewById(R.id.playProgressbar);
//        playProgressbar.setProgress(0);
//
//        testTimer = new CountDownTimer(30000, 100) {
//
//
//            public void onTick(long millisUntilFinished) {
//
//                currentLength = audioRecordTest.getmPlayer().getCurrentPosition();
//                int maxLength = audioRecordTest.getmPlayer().getDuration();
//                progress = ((currentLength*100)/maxLength);
//
//                System.out.println("Länge in Timer " + maxLength);
//
//                playProgressbar.setProgress(progress);
//                System.out.println(currentLength);
////              System.out.println(currentLength);
//                System.out.println(progress);
//                System.out.println("tick");
//
//                test3 = test3+100;
//
//                if(test3 >= maxLength){
//                    currentLength = 0;
//                    test3 = 0;
//                    onFinish();
//                }
//
//            }
//
//            public void onFinish() {
//                playProgressbar.setProgress(0);
//                play.setImageResource(R.drawable.play);
//                audioRecordTest.onPlay(mStartPlaying);
//                mStartPlaying = true;
//                this.cancel();
//                System.out.println("Nachricht fertig abgespiel!");
//            }
//        };
//
//
//
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                audioRecordTest.onPlay(mStartPlaying);
//                if (mStartPlaying) {
//
//                    play.setImageResource(R.drawable.pause);
//                    testTimer.start();
//
//                } else {
//                    play.setImageResource(R.drawable.play);
//                    playProgressbar.setProgress(0);
//                    testTimer.cancel();
//                    progress = 0;
//                    currentLength = 0;
//                }
//                mStartPlaying = !mStartPlaying;
//
//
//
//            }
//
//        });



}

