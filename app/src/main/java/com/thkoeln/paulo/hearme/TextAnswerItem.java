package com.thkoeln.paulo.hearme;

import android.support.design.widget.TabLayout;

import java.io.Serializable;

/**
 * Created by pascal on 21.01.18.
 */

public class TextAnswerItem implements Serializable {

    private String author;
    private String kommentar;

    public TextAnswerItem(String author, String kommentar){
        this.author = author;
        this.kommentar = kommentar;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }




}
