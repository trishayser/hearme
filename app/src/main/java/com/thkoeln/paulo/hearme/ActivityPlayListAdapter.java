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
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ProgressBar;
        import android.widget.TextView;

        import com.google.firebase.storage.StorageReference;

        import java.io.IOException;
        import java.text.SimpleDateFormat;
        import java.util.List;

/**
 * Created by pascal on 10.12.17.
 */

public class ActivityPlayListAdapter extends ArrayAdapter<TextAnswerItem> {

    private List<TextAnswerItem> items;
    private int layoutResourceId;
    private int layoutCount;
    private Context context;


    public ActivityPlayListAdapter(Context context, int layoutResourceId, List<TextAnswerItem> textAnswers) {
        super(context, layoutResourceId, textAnswers);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.items = textAnswers;




    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        row = inflater.inflate(layoutResourceId, parent, false);
        TextAnswerHolder answer = new TextAnswerHolder();
        answer.item = items.get(position);
        answer.authorView = (TextView)row.findViewById(R.id.author_kom);
        answer.kommentarView = (TextView)row.findViewById(R.id.kommentar);

        setupItem(answer);


        return row;
    }

    private void setupItem(TextAnswerHolder answer) {
        answer.authorView.setText(answer.item.getAuthor());
        answer.kommentarView.setText(answer.item.getKommentar());

    }


  public class TextAnswerHolder{
        TextAnswerItem item;
//        String author;
//        String kommentar;
        TextView authorView;
        TextView kommentarView;

  }
}