package com.thkoeln.paulo.hearme;
//spackage com.android.audiorecordtest;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

public class AudioRecordTest {

    private static final String LOG_TAG = "AudioRecordTest";

    private static String mFileName = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;


//    public AudioRecordTest (String mFileName){
//        this.mFileName = mFileName;
//    }

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
        mPlayer = new MediaPlayer();
        mPlayer.isPlaying();
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
    }
}