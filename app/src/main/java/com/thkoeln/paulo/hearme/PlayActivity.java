package com.thkoeln.paulo.hearme;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private String mFileName = "Noch kein Pfad vorhanden";
    Intent playerService;
    mediaPlayerStartService mService;
    boolean mBound = false;

    // Requesting permission to RECORD_AUDIO
    private boolean permissionToRecordAccepted;
    private String [] permissions;
    List <PlayerItem> testPlayList;

    private Button send;

    ImageButton recordPlay;
    AudioRecordTest audioRecordTest;
    CountDownTimer testTimer;
    ProgressBar recordProgressbar;

    int test3, progress, currentLength = 0;

    boolean mStartRecording = true; // Für Record
    boolean mStartPlaying = true; // Für Play

    String audiopath;

    TextView timeView, title;
    String titleText;
    String id, title_marker;
    int countBewertungen;
    int countpos;
    int countneg;
    int pos;
    int neg;
    public TextView textViewLike;
    public TextView textViewDislike;

    ActivityPlayListAdapter adapter;



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

        timeView = (TextView)findViewById(R.id.time_view);
        title = (TextView)findViewById(R.id.play_title);

        countBewertungen = 0;
        countpos = 0;
        countneg = 0;
        pos = 0;
        neg = 0;

        //Button send = (Button) findViewById(R.id.abschicken_comment);



        playerService = new Intent(this, mediaPlayerStartService.class);

        startService(playerService);
/*
        //--------------------AudioRecordTest-Klasse----------------------------------------
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";
        //audioRecordTest = new AudioRecordTest(mFileName);
        //------------------------------------------------------------
*/
        Intent intent = getIntent();
        String message = intent.getStringExtra(MapsActivity.EXTRA_ID);

        String [] messages = message.split("/");
        id = messages[0];
        title_marker = messages[1];
        System.out.println("message" + message);
        System.out.println("id " + messages[0]);
        System.out.println("titel marker " + title_marker);



       // testPlayList = new ArrayList<PlayerItem>();
        //FILE DOWNLOAD
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference playRef = mStorageRef.child(id + ".3gp");
        audiopath = null;



       //Title
        titleText = "Titel";

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("posts");


        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {
                                                    Post post = dataSnapshot.child(noteDataSnapshot.getKey()).getValue(Post.class);
                                                                                                                                                                                                                
                                                    if(post.title.toString().contentEquals(title_marker)){
                                                        titleText = post.title.toString();
                                                        title.setText(post.title);
                                                    }


                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                System.out.println("Hallo");
                                            }
                                        });



        // Tile ende

        try {
            File localFile = null;
            localFile = File.createTempFile("audio", ".3gp");
            playRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    System.out.print("Lokales Tempfile generiert2");
                }
            });
            audiopath = localFile.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        Intent intent = getIntent();
        String id = intent.getStringExtra(MapsActivity.EXTRA_MESSAGE);

        StorageReference playfile =
        File localFile = null;
        try {
            localFile = File.createTempFile("play", "3gp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mStorageRef.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                // ...
            }
        });

        PlayerItem testPlayItem = new PlayerItem("Hauptnachricht", localFile.getAbsolutePath());
        testPlayList.add(testPlayItem);
        */
/*
        try {

            File localFile = File.createTempFile("audio", ".3gp");
            System.out.println(localFile.getAbsolutePath());
            //localFile.createNewFile();
            playRef.getFile(localFile);
            if (localFile == null) {
                System.out.printf("Nope");
            }
            PlayerItem testPlayItem = new PlayerItem("Hauptnachricht", localFile.getAbsolutePath());
            testPlayList.add(testPlayItem);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("hat nicht funktioniert","schade");
        }
*/
        //testPlayList = new ArrayList<PlayerItem>();

//pascal

//        PlayerItem testPlayItem = new PlayerItem("Hauptnachricht", mFileName);
//        PlayerItem testPlayItem2 = new PlayerItem("Antwort 1", mFileName);
//        PlayerItem testPlayItem3 = new PlayerItem("Antwort 2", mFileName);
//        testPlayList.add(testPlayItem);
//
//
//
//        if (audiopath == null) {
//            System.out.println("Dateiverzeichniss ist leer!!!!!!!!!");
//        }
//        ActivityPlayListAdapter adapter = new ActivityPlayListAdapter(PlayActivity.this, R.layout.list_item_main_message, R.layout.list_item_messages, testPlayList, mFileName, this, audiopath);
//        ListView atomPaysListView = (ListView)findViewById(R.id.Answer_list);
//        atomPaysListView.setAdapter(adapter);
//
//
//
//
//
//
//
//
//
//
//        PlayerItem testPlayItem4 = new PlayerItem("Antwort 3", mFileName);
//        PlayerItem testPlayItem5 = new PlayerItem("Antwort 4", mFileName);
//
//        adapter.add(testPlayItem2);
//        adapter.add(testPlayItem2);
//        adapter.add(testPlayItem3);
//        adapter.add(testPlayItem4);
//        adapter.add(testPlayItem5);
        //pascal

        //pascal 21.01-2018

        //--------------------AudioRecordTest-Klasse----------------------------------------
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";
        audioRecordTest = new AudioRecordTest();
        //------------------------------------------------------------

        TextAnswerItem item1 = new TextAnswerItem("pascal", "Das finde ich gut");
        TextAnswerItem item2 = new TextAnswerItem("paulo", "Also ich finde das nicht so gut");
        TextAnswerItem item3 = new TextAnswerItem("tristan", "Ich schließe mich Paulos Meinung an. Ich finde das auch nicht so gut. Aber aus welchem Grund, kann ich nicht so genau sagen");

        ArrayList<TextAnswerItem> answerList = new ArrayList<TextAnswerItem>();
        answerList.add(item1);
        answerList.add(item2);
        answerList.add(item3);

        adapter = new ActivityPlayListAdapter(PlayActivity.this, R.layout.list_item_text_answer_item, answerList);

        ListView atomPaysListView = (ListView)findViewById(R.id.Answer_list);
        atomPaysListView.setAdapter(adapter);

        recordProgressbar = (ProgressBar) findViewById(R.id.playProgressbar);
        recordProgressbar.setProgress(0);

        recordPlay = (ImageButton) findViewById(R.id.play);


        testTimer = new CountDownTimer(30000, 100) {


            public void onTick(long millisUntilFinished) {
                System.out.println("Hallo");

                try {
                    progress = ((audioRecordTest.getmPlayer().getCurrentPosition() * 100) / audioRecordTest.getmPlayer().getDuration());
                    recordProgressbar.setProgress(progress);

                    long secs = audioRecordTest.getmPlayer().getCurrentPosition() / 1000;
                    long mins = secs / 60;
                    long restsecs = secs % 60;
                    String time = String.format("%02d:%02d", mins,secs);
                    System.out.println(mins +":"+restsecs);
                    timeView.setText(time);

                    if ((audioRecordTest.getmPlayer().getDuration() - audioRecordTest.getmPlayer().getCurrentPosition()) < 100){
                       // mStartPlaying = true;
                        System.out.println("Ende");
                        this.onFinish();
                    }
                } catch (Exception e) {
                    System.out.println("Nachricht konnte nicht abgespielt werden");
                }

//                currentLength = audioRecordTest.getmPlayer().getCurrentPosition();
//                int maxLength = audioRecordTest.getmPlayer().getDuration();
//                progress = ((currentLength * 100) / maxLength);
//
//                System.out.println("Länge in Timer " + maxLength);
//
//                recordProgressbar.setProgress(progress);
//                System.out.println(currentLength);
//                System.out.println(progress);
//                System.out.println("tick");
//
//                test3 = test3 + 100;
//
//                if (test3 >= maxLength) {
//                    currentLength = 0;
//                    test3 = 0;
//                    onFinish()
//                    ;
            }

            public void onFinish() {

                recordProgressbar.setProgress(0);
                String time = String.format("%02d:%02d", 0, 0);
                timeView.setText(time);


                recordPlay.setImageResource(R.drawable.play);
                audioRecordTest.onPlay(mStartPlaying);
                mStartPlaying = true;
                this.cancel();
                System.out.println("Nachricht fertig abgespiel!");
            }
        };

        recordPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioRecordTest.setmFileName(audiopath);
                audioRecordTest.onPlay(mStartPlaying);
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

            }

        });
        ImageButton positive = (ImageButton)findViewById(R.id.posButton2);
        ImageButton negative = (ImageButton)findViewById(R.id.negButton2);
        textViewLike = (TextView) findViewById(R.id.like_percent2);
        textViewDislike = (TextView) findViewById(R.id.dislike_percent2);

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

        final EditText kommentar = (EditText)findViewById(R.id.comment_edit);
        Button kommentarAbschicken = (Button)findViewById(R.id.abschicken_comment);

        kommentarAbschicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kommentarText = kommentar.getText().toString();
                kommentarAdd(kommentarText);
            }
        });


    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        // Bind mediaPlayekrStartService
//        bindService(playerService, mConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    protected void onStop(){
//        super.onStop();
//        // unbind mediaplayerStartService
//        unbindService(mConnection);
//    }
//    /** Defines callbacks for service binding, passed to bindService() */
//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                                       IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            mediaPlayerStartService.LocalBinder binder = (mediaPlayerStartService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mBound = false;
//        }
//    };

    public String getmFileName() {
        if(mFileName == null){
            //--------------------AudioRecordTest-Klasse----------------------------------------
            mFileName = getExternalCacheDir().getAbsolutePath(); //Original
            mFileName += "/audiorecordtest.3gp";
            //audioRecordTest = new AudioRecordTest(mFileName);
            //------------------------------------------------------------
        }
        return mFileName;
    }

    public  void setTitle(String title){
                  this.titleText = title;
    }

    //Bewertung

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

        textViewLike.setText(pos + "%");
        textViewDislike.setText(neg + "%");
    }
    //Bewertung Ende

    public void kommentarAdd(String kommentarText){
    TextAnswerItem answerItem = new TextAnswerItem("Jona", kommentarText);
    adapter.add(answerItem);
    }
}
