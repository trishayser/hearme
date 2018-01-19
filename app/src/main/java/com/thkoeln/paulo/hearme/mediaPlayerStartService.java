package com.thkoeln.paulo.hearme;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.media.MediaPlayer;
import android.util.Log;
import android.media.AudioManager;


import java.io.IOException;
import java.util.Random;


/**
 * Created by pascal on 15.12.17.
 */
public class mediaPlayerStartService extends Service implements MediaPlayer.OnCompletionListener {

        private final IBinder mBinder = new LocalBinder();
        private final Random mGenerator = new Random();

        private static final String LOG_TAG = "AudioRecordTest";

        private MediaRecorder mRecorder = null;
        private MediaPlayer mPlayer = null;

    private String mFileName = null;
        private String referenceCheck = "Reference ist da";

    public String getReferenceCheck(){
        return referenceCheck;
    }

    public mediaPlayerStartService() {
        //this.mPlayer = new MediaPlayer();
    }

    public class LocalBinder extends Binder {
            mediaPlayerStartService getService(){
                return mediaPlayerStartService.this;
            }
    }

    public void onRecord(boolean start) {
            if (start) {
                startRecording();
            } else {
                stopRecording();
            }
        }

        public void onPlay(boolean start) {
            if (start) {
                startPlaying();
            } else {
                stopPlaying();
            }
        }

        private void startPlaying() {
            System.out.println("startPlaying() in Service");
            mPlayer = new MediaPlayer();
            mPlayer.isPlaying();
            try {
                mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mPlayer.setDataSource(this.mFileName);
                //mPlayer.setOnPreparedListener(ActivityPlayListAdapter.this);
                //mPlayer.prepareAsync();
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
        }

        private void stopPlaying() {
            mPlayer.release();
//            mPlayer.stop();
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




        public MediaRecorder getmRecorder() {
            return mRecorder;
        }

        public MediaPlayer getmPlayer() {
            return mPlayer;
        }
        public void setmPlayer(MediaPlayer mPlayer) {
            this.mPlayer = mPlayer;
        }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
        System.out.println(this.mFileName);
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public int getRandomNUmber () {
        return mGenerator.nextInt(100);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
    }
}
