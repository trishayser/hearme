package com.thkoeln.paulo.hearme;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pascal on 10.12.17.
 */

public class ActivityPlayListAdapter extends ArrayAdapter<PlayerItem> {

    private List<PlayerItem> items;
    private int layoutResourceId;
    private Context context;
    boolean mStartPlaying; // Für Play
    boolean otherPlayer;
    int maxCounter;
    int currentLength;
    int progress;
    PlayItemHolder currentItem;
    private AudioRecordTest audioRecordTest;
    String mFileName;
    CountDownTimer testTimer;

    public ActivityPlayListAdapter(Context context, int layoutResourceId, List<PlayerItem> items, String mFileName) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.mFileName = mFileName;
        this.otherPlayer = false;
        this.mStartPlaying = true;
        this.audioRecordTest = new AudioRecordTest(this.mFileName);
        audioRecordTest.setmPlayer(new MediaPlayer());
        this.testTimer = createTimer();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PlayItemHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new PlayItemHolder();
        holder.playerItem = items.get(position);
        holder.playButton = (ImageButton)row.findViewById(R.id.play);
        holder.playButton.setTag(holder.playerItem);
        holder.playProgressbar = (ProgressBar)row.findViewById(R.id.playProgressbar);

        holder.positive = (ImageButton)row.findViewById(R.id.posButton);
        holder.positive.setTag(holder.playerItem);

        holder.negative = (ImageButton)row.findViewById(R.id.negButton);
        holder.negative.setTag(holder.playerItem);


        holder.playTitel = (TextView)row.findViewById(R.id.play_title);
        holder.textViewLike = (TextView)row.findViewById(R.id.like_percent);
        holder.textViewDislike = (TextView)row.findViewById(R.id.dislike_percent);
        holder.audioRecordTestHolder = this.audioRecordTest;
        row.setTag(holder);

        setupItem(holder);
        setListener(holder);
        return row;
    }

    private void setupItem(final PlayItemHolder holder) {
        holder.playTitel.setText(holder.playerItem.getName());
        holder.playProgressbar.setProgress(0);
    }







    public void setListener(final PlayItemHolder holder){

        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("PlayButton " + holder.playTitel);

                if(currentItem != holder) {
                    testTimer.cancel();
                    if (currentItem!=null){
                        currentItem.playButton.setImageResource(R.drawable.play);
                        currentItem.playProgressbar.setProgress(0);
                    }
                    currentItem = holder;
                    mStartPlaying = true;
                }

                //holder.audioRecordTestHolder.onPlay(mStartPlaying);
                if (mStartPlaying == true && otherPlayer == false) {
                    System.out.println("If 1");
                    holder.playButton.setImageResource(R.drawable.pause);
                    audioRecordTest.onPlay(true);
                    mStartPlaying = false;
                    otherPlayer = true;
                    testTimer.start();

                } else if (mStartPlaying == true && otherPlayer == true) {
                    System.out.println("If 2");
                    testTimer.onFinish();
                    progress = 0;
                    currentLength = 0;
//                    audioRecordTest.onPlay(false);
                    holder.playButton.setImageResource(R.drawable.pause);
                    otherPlayer = true;
                    mStartPlaying = false;
                    audioRecordTest.onPlay(true);
                    testTimer.start();
                }
                else {
                    System.out.println("If 3");
//                    holder.playButton.setImageResource(R.drawable.play);
//                    holder.playProgressbar.setProgress(0);
//                    audioRecordTest.onPlay(false);
                    testTimer.onFinish();
                    progress = 0;
                    currentLength = 0;
                    otherPlayer = false;
                    mStartPlaying = true;
                }

            }

        });




        holder.positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("positive");
                bewertung(true, holder);
            }
        });

        holder.negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("negative");
                bewertung(false, holder);
            }
        });




    }

    public static class PlayItemHolder {
        PlayerItem playerItem;
        TextView playTitel;
        ImageButton playButton;
        ProgressBar playProgressbar;
        ImageButton negative;
        ImageButton positive;
        TextView textViewLike;
        TextView textViewDislike;
        int pos;
        int neg;
        int countBewertungen;
        int countpos;
        int countneg;
        AudioRecordTest audioRecordTestHolder;
        CountDownTimer playItemTimer;
    }


        public void bewertung (boolean positive, PlayItemHolder holder){
        holder.countBewertungen++;

        if (positive){
            holder.countpos++;
        }
        else{
            holder.countneg++;
        }
            holder.pos = (holder.countpos*100)/holder.countBewertungen;
            holder.neg = 100-holder.pos;

        System.out.println("Positive "+ holder.pos + "% " + "  Negative " + holder.neg +"% ");

        holder.textViewLike.setText(holder.pos + "%");

        holder.textViewDislike.setText(holder.neg + "%");



    }

    public int currenLength(){
            return audioRecordTest.getmPlayer().getCurrentPosition();
    }
    public int maxLength(){
        return  audioRecordTest.getmPlayer().getDuration();
    }


    public  CountDownTimer  createTimer(){
        maxCounter = 0;
        progress = 0;
        currentLength = 0;

        CountDownTimer newTimer = new CountDownTimer(30000, 100) {


            public void onTick(long millisUntilFinished) {

                currentLength = currenLength();
                progress = ((currentLength*100)/maxLength());

                System.out.println("Länge in Timer " + maxLength());

                currentItem.playProgressbar.setProgress(progress);
                System.out.println(currentLength);
//              System.out.println(currentLength);
                System.out.println(progress);
                System.out.println("tick");
                maxCounter = maxCounter + 100;

                if (maxCounter >= maxLength()) {
                    currentLength = 0;
                    onFinish();
                }

            }

            public void onFinish() {
                currentItem.playProgressbar.setProgress(0);
                currentItem.playButton.setImageResource(R.drawable.play);
                audioRecordTest.onPlay(false);
                mStartPlaying = true;
                otherPlayer = false;
                maxCounter = 0;
                this.cancel();
                System.out.println("Nachricht fertig abgespiel!");
            }
        };
    return newTimer;
    }

}