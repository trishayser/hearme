package com.thkoeln.paulo.hearme;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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

        playerService = new Intent(this, mediaPlayerStartService.class);
        //startService(playerService);

/*
        //--------------------AudioRecordTest-Klasse----------------------------------------
        mFileName = getExternalCacheDir().getAbsolutePath(); //Original
        mFileName += "/audiorecordtest.3gp";
        //audioRecordTest = new AudioRecordTest(mFileName);
        //------------------------------------------------------------
*/
        //FILE DOWNLOAD
        StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReference();


        testPlayList = new ArrayList<PlayerItem>();

        PlayerItem testPlayItem = new PlayerItem("Hauptnachricht", mFileName);
        PlayerItem testPlayItem2 = new PlayerItem("Antwort 1", mFileName);
        PlayerItem testPlayItem3 = new PlayerItem("Antwort 2", mFileName);
        testPlayList.add(testPlayItem);




        ActivityPlayListAdapter adapter = new ActivityPlayListAdapter(PlayActivity.this, R.layout.list_item_main_message, R.layout.list_item_messages, testPlayList, mFileName, this);
        ListView atomPaysListView = (ListView)findViewById(R.id.Answer_list);
        atomPaysListView.setAdapter(adapter);

        PlayerItem testPlayItem4 = new PlayerItem("Antwort 3", mFileName);
        PlayerItem testPlayItem5 = new PlayerItem("Antwort 4", mFileName);

        adapter.add(testPlayItem2);
        adapter.add(testPlayItem2);
        adapter.add(testPlayItem3);
        adapter.add(testPlayItem4);
        adapter.add(testPlayItem5);



        // Ende List Test



    }
    @Override
    protected void onStart(){
        super.onStart();
        // Bind mediaPlayerStartService
        bindService(playerService, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop(){
        super.onStop();
        // unbind mediaplayerStartService
        unbindService(mConnection);
    }


    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mediaPlayerStartService.LocalBinder binder = (mediaPlayerStartService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

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
}


