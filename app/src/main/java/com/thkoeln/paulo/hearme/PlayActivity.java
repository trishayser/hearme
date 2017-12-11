package com.thkoeln.paulo.hearme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

//    ImageButton positive;
//    ImageButton negative;
//    ImageButton playButtonMethode;
//
//    int pos;
//    int neg;
//    int countBewertungen;
//    int countpos;
//    int countneg;
//
//    ImageButton play;
//    AudioRecordTest audioRecordTest;
//    CountDownTimer testTimer;
//    int test3, progress, currentLength = 0;
//
//
//
//    boolean mStartPlaying = true; // Für Play
//
//    private static String mFileName = null;


    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted;
    private String [] permissions;
    List <PlayerItem> testPlayList;

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

       permissionToRecordAccepted = false;
      String [] permissions = {Manifest.permission.RECORD_AUDIO};


        //--------------------AudioRecordTest-Klasse----------------------------------------
        String mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";
        //audioRecordTest = new AudioRecordTest(mFileName);
        //------------------------------------------------------------

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

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);//Erlaubnis für Record

//        Button antworten = (Button) findViewById(R.id.antworten);
//        final Intent antwortenIntent = new Intent(this, NewRecordActivityActivity.class);
//
//
//        antworten.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(antwortenIntent);
//            }
//        });

        //List Test

        testPlayList = new ArrayList<PlayerItem>();

        PlayerItem testPlayItem = new PlayerItem("Test", mFileName);
        PlayerItem testPlayItem2 = new PlayerItem("Test2", mFileName);
        PlayerItem testPlayItem3 = new PlayerItem("Test3", mFileName);
        testPlayList.add(testPlayItem);
        testPlayList.add(testPlayItem2);
        testPlayList.add(testPlayItem3);




        ActivityPlayListAdapter adapter = new ActivityPlayListAdapter(PlayActivity.this, R.layout.list_item_messages, testPlayList, mFileName);
        ListView atomPaysListView = (ListView)findViewById(R.id.Answer_list);
        atomPaysListView.setAdapter(adapter);

        PlayerItem testPlayItem4 = new PlayerItem("Test4", mFileName);
        PlayerItem testPlayItem5 = new PlayerItem("Test5", mFileName);

        adapter.add(testPlayItem4);
        adapter.add(testPlayItem5);



        // Ende List Test




    }

//       public void bewertung (boolean positive){
//           countBewertungen++;
//
//           if (positive){
//               countpos++;
//           }
//           else{
//               countneg++;
//           }
//           pos = (countpos*100)/countBewertungen;
//           neg = 100-pos;
//
//        System.out.println("Positive "+ pos + "% " + "  Negative " + neg +"% ");
//
//        final TextView textViewLike = (TextView) findViewById(R.id.like_percent);
//        textViewLike.setText(pos + "%");
//
//        final TextView textViewDislike = (TextView) findViewById(R.id.dislike_percent);
//        textViewDislike.setText(neg + "%");
//
//
//
//    }


}


