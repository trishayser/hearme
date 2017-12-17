package com.thkoeln.paulo.hearme;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AudioPlayer extends Service implements MediaPlayer.OnCompletionListener{
    public AudioPlayer() {
    }

    MediaPlayer mediaPlayer;
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(this, R.raw.s);// raw/s.mp3
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        return START_STICKY;
    }

    public void onDestroy() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void onCompletion(MediaPlayer _mediaPlayer) {
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
