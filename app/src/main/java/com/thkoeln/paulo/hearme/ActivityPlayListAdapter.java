package com.thkoeln.paulo.hearme;

        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
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
    int progress;
    PlayItemHolder currentItem;
    PlayActivity playActivity;

    public static String mFileName;
    private boolean start;

    CountDownTimer timer;

    public ActivityPlayListAdapter(Context context, int layoutResourceId, List<PlayerItem> items, String mFileName, PlayActivity playActivityR) {
        super(context, layoutResourceId, items);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = items;
        this.mFileName = mFileName;
        this.mStartPlaying = true;
        this.start = true;
        this.playActivity = playActivityR;

        this.timer = new CountDownTimer(300000,50) {
            public void onTick(long millisUntilFinished) {

                progress = ((playActivity.mService.getmPlayer().getCurrentPosition() * 100) / playActivity.mService.getmPlayer().getDuration());
                System.out.println(progress);
                currentItem.playProgressbar.setProgress(progress);
                System.out.println(playActivity.mService.getmPlayer().getCurrentPosition());
                System.out.println(playActivity.mService.getmPlayer().getDuration());
                if (!playActivity.mService.getmPlayer().isPlaying()){
                    this.onFinish();
                }
            }

            @Override
            public void onFinish() {
                this.cancel();
                currentItem.playProgressbar.setProgress(0);
                currentItem.playButton.setImageResource(R.drawable.play);
                playActivity.mService.onPlay(false);
                mStartPlaying = true;
            }
        };
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
                System.out.println("PlayButton " + holder.playerItem.getName());
                System.out.println("StartPlaying? " + mStartPlaying);
                //System.out.println(playActivity.mService.getReferenceCheck());
                //playActivity.mService.setmFileName(mFileName);
                playAudioMessage(holder);

                //playActivity.mService.onPlay(true);
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
    }

    public void playAudioMessage(PlayItemHolder holder) {
        if (currentItem == null){
            currentItem = holder;
        }

        if(currentItem != holder) {
            timer.cancel();
                System.out.println("Anderen Player Stoppen");
                currentItem.playProgressbar.setProgress(0);
                playActivity.mService.onPlay(false);
                currentItem.playButton.setImageResource(R.drawable.play);
                System.out.println("Anderen Player gestoppt");
                mStartPlaying = true;
        }
        if (!mStartPlaying) {
            timer.onFinish();
            System.out.println("Aktuellen Player stoppen");
            holder.playProgressbar.setProgress(0);
//            playActivity.mService.onPlay(false);
            holder.playButton.setImageResource(R.drawable.play);
            System.out.println("aktueller Player gestoppt");
            mStartPlaying = true;
        }
        else {
            System.out.println(playActivity.mService.getReferenceCheck());
            playActivity.mService.setmFileName(mFileName);
            System.out.println("Player starten? Bitte ja! ");
            currentItem = holder;
            holder.playButton.setImageResource(R.drawable.pause);
            playActivity.mService.onPlay(true);
            mStartPlaying = false;
            currentItem = holder;
            timer.start();
        }

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
}