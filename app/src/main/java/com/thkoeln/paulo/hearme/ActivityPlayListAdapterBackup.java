//package com.thkoeln.paulo.hearme;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.CountDownTimer;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import java.util.List;
//
///**
// * Created by pascal on 10.12.17.
// */
//
//public class ActivityPlayListAdapterBackup extends ArrayAdapter<PlayerItem> {
//
//    private List<PlayerItem> items;
//    private int layoutResourceId;
//    private int layoutResourceId2;
//    private int layoutCount;
//    private Context context;
//    boolean mStartPlaying; // Für Play
//    int progress;
//    PlayItemHolder currentItem;
//    PlayItemHolder timerHolder;
//    PlayActivity playActivity;
//
//    public static String mFileName;
//
//    String audiopath;
//
//    private boolean start;
//
//    CountDownTimer timer;
//
//    public ActivityPlayListAdapterBackup(Context context, int layoutResourceId, int layoutResourceId2, List<PlayerItem> items, String mFileName, PlayActivity playActivityR, String audiopath) {
//        super(context, layoutResourceId, itemsfinal PlayItemHolder holder);
//        this.layoutResourceId = layoutResourceId;
//        this.layoutResourceId2 = layoutResourceId2;
//        this.layoutCount = 0;
//        this.context = context;
//        this.items = items;
//        this.mFileName = mFileName;
//        this.mStartPlaying = true;
//        this.start = true;
//        this.playActivity = playActivityR;
//        this.audiopath = audiopath;
//
//
////----------- Timer--------------------------------------------------------------------
//        this.timer = new CountDownTimer(300000,50) {
//            public void onTick(long millisUntilFinished) {
//                timerHolder = currentItem;
//
//                try {
//                    progress = ((playActivity.mService.getmPlayer().getCurrentPosition() * 100) / playActivity.mService.getmPlayer().getDuration());
//                    System.out.println(progress);
//                    System.out.println("progress setzen");
//                    currentItem.playProgressbar.setProgress(progress);
//                    System.out.println(playActivity.mService.getmPlayer().getCurrentPosition());
//                    System.out.println(playActivity.mService.getmPlayer().getDuration());
//
//                    long secs = playActivity.mService.getmPlayer().getCurrentPosition() / 1000;
//                    long mins = secs / 60;
//                    long restsecs = secs % 60;
//                    String time = String.format("%02d:%02d", mins,secs);
//                    System.out.println(mins +":"+restsecs);
//                    currentItem.timeView.setText(time);
//
//                    if (!playActivity.mService.getmPlayer().isPlaying()){
//                        mStartPlaying = true;
//                        this.onFinish();
//                    }
//                } catch (Exception e) {
//                    System.out.println("Nachricht konnte abgespielt werden");
//                }
//
//            }
//
//            @Override
//            public void onFinish() {
//                currentItem.playProgressbar.setProgress(0);
//                currentItem.timeView.setText(00 +":"+00);
//                String time = String.format("%02d:%02d", 0, 0);
//               currentItem.timeView.setText(time);
//                currentItem.playButton.setImageResource(R.drawable.play);
//                currentItem.playerItem.setItemIsPlaying(false);
//                playActivity.mService.onPlay(false);
//                mStartPlaying = true;
//                this.cancel();
//            }
//        };
////----------- Ende - Timer--------------------------------------------------------------------
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
////        PlayItemHolder holder = null;
//
//        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        //-------------------------------------
//        if (position == 1) {
//            AnswerButtonItemHolder holder = new AnswerButtonItemHolder();
//                row = inflater.inflate(layoutResourceId, parent, false);
//                holder.answerButton = (Button)row.findViewById(R.id.antworten);
//                System.out.println("AnswerButtonItem (Item with position == 1)");
//
//            holder.answerButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    System.out.println("Antworten Button");
//                }
//            });
//
//            }
//            //set the data in subview with holder fields
//         else {
//            PlayItemHolder holder = new PlayItemHolder();
//
//                row = inflater.inflate(layoutResourceId2, parent, false);
//                System.out.println("NormalItem");
//
//            holder.playerItem = items.get(position);
//            holder.playButton = (ImageButton)row.findViewById(R.id.play);
//            holder.playButton.setTag(holder.playerItem);
//            holder.playProgressbar = (ProgressBar)row.findViewById(R.id.playProgressbar);
//
//            holder.positive = (ImageButton)row.findViewById(R.id.posButton);
//            holder.positive.setTag(holder.playerItem);
//
//            holder.negative = (ImageButton)row.findViewById(R.id.negButton);
//            holder.negative.setTag(holder.playerItem);
//
//
//            holder.playTitel = (TextView)row.findViewById(R.id.play_title);
//            holder.textViewLike = (TextView)row.findViewById(R.id.like_percent);
//            holder.textViewDislike = (TextView)row.findViewById(R.id.dislike_percent);
//            holder.timeView = (TextView)row.findViewById(R.id.time_view);
//
//            row.setTag(holder);
//            setupItem(holder);
//            setListener(holder);
//
//            }
//
//        return row;
//    }
//
//    private void setupItem(final PlayItemHolder holder) {
//        System.out.println(holder.playerItem.isItemIsPlaying());
//        holder.playTitel.setText(holder.playerItem.getName());
//        if(!mStartPlaying && holder.playerItem.isItemIsPlaying()){
//            currentItem = holder;
//            holder.playProgressbar.setProgress(progress);
//            holder.playButton.setImageResource(R.drawable.pause);
//            System.out.println("Timmer start?");
//            timer.start();
//
//        }
//        else {
//            holder.playProgressbar.setProgress(0);
//            String time = String.format("%02d:%02d", 0, 0);
//            holder.timeView.setText(time);
//        }
//    }
//
//
//
//
//
//
//
//    public void setListener(final PlayItemHolder holder){
//
//        holder.playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("PlayButton " + holder.playerItem.getName());
//                System.out.println("StartPlaying? " + mStartPlaying);
//                //System.out.println(playActivity.mService.getReferenceCheck());
//                //playActivity.mService.setmFileName(mFileName);
//                playAudioMessage(holder);
//
//                //playActivity.mService.onPlay(true);
//            }
//
//        });
//
//
//
//
//        holder.positive.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("positive");
//                bewertung(true, holder);
//            }
//        });
//
//        holder.negative.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("negative");
//                bewertung(false, holder);
//            }
//        });
//    }
//
//    public static class AnswerButtonItemHolder {
//        Button answerButton;
//    }
//
//    public void playAudioMessage(PlayItemHolder holder) {
//        if (currentItem == null) {
//            currentItem = holder;
//        }
//
//        if (mStartPlaying) {
//
//
//            System.out.println(playActivity.mService.getReferenceCheck());
//            playActivity.mService.setmFileName(audiopath);
//            System.out.println("Player starten? Bitte ja! ");
//            currentItem = holder;
//            holder.playButton.setImageResource(R.drawable.pause);
//            playActivity.mService.onPlay(true);
//            mStartPlaying = false;
//            holder.holderPlaying = true;
//            currentItem = holder;
//            currentItem.playerItem.setItemIsPlaying(true);
//            timer.start();
//        }
//
//        else if (currentItem != holder) {
//                timer.onFinish();
//                System.out.println("Anderen Player Stoppen");
//                currentItem.playProgressbar.setProgress(0);
//                String time2 = String.format("%02d:%02d", 0, 0);
//                currentItem.timeView.setText(time2);
//
////                playActivity.mService.onPlay(false);
//                currentItem.playButton.setImageResource(R.drawable.play);
//                System.out.println("Anderen Player gestoppt");
//                mStartPlaying = true;
//
//                System.out.println(playActivity.mService.getReferenceCheck());
//                playActivity.mService.setmFileName(mFileName);
//                System.out.println("Player starten? Bitte ja! ");
//                currentItem = holder;
//                holder.playButton.setImageResource(R.drawable.pause);
//                playActivity.mService.onPlay(true);
//                mStartPlaying = false;
//                holder.holderPlaying = false;
//                currentItem.playerItem.setItemIsPlaying(false);
//                holder.playerItem.setItemIsPlaying(false);
//                currentItem = holder;
//                currentItem.playerItem.setItemIsPlaying(true);
//                timer.start();
//
//            }
//            else {
//                timer.onFinish();
//                System.out.println("Aktuellen Player stoppen");
//                holder.playProgressbar.setProgress(0);
//                String time = String.format("%02d:%02d", 0, 0);
//                holder.timeView.setText(time);
//                holder.playButton.setImageResource(R.drawable.play);
//                System.out.println("aktueller Player gestoppt");
//                holder.holderPlaying = false;
//                currentItem.playerItem.setItemIsPlaying(false);
//                mStartPlaying = true;
//                currentItem = null;
//            }
//
//
//    }
//
//    public void bewertung (boolean positive, PlayItemHolder holder){
//        holder.countBewertungen++;
//
//        if (positive){
//            holder.countpos++;
//        }
//        else{
//            holder.countneg++;
//        }
//        holder.pos = (holder.countpos*100)/holder.countBewertungen;
//        holder.neg = 100-holder.pos;
//
//        System.out.println("Positive "+ holder.pos + "% " + "  Negative " + holder.neg +"% ");
//
//        holder.textViewLike.setText(holder.pos + "%");
//
//        holder.textViewDislike.setText(holder.neg + "%");
//    }
//
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (position == 0) {
//            return 0;
//        } else {
//            return 1;
//        }
//    }
//
//    public static class PlayItemHolder {
//        PlayerItem playerItem;
//        TextView playTitel;
//        ImageButton playButton;
//        ProgressBar playProgressbar;
//        ImageButton negative;
//        ImageButton positive;
//        TextView textViewLike;
//        TextView textViewDislike;
//        TextView timeView;
//        int pos;
//        int neg;
//        int countBewertungen;
//        int countpos;
//        int countneg;
//        boolean holderPlaying = false;
//    }
//}
