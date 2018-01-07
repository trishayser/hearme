package com.thkoeln.paulo.hearme;

import java.io.Serializable;

/**
 * Created by pascal on 10.12.17.
 */

public class PlayerItem  implements Serializable {

    private String name = "";
    private String mFileName = "";

    private boolean itemIsPlaying;

        public PlayerItem(String name, String mFileName) {
            this.setName(name);
            this.setmFileName(mFileName);
            this.itemIsPlaying = false;
        }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getmFileName() {
        return mFileName;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public boolean isItemIsPlaying() {
        return itemIsPlaying;
    }

    public void setItemIsPlaying(boolean itemIsPlaying) {
        this.itemIsPlaying = itemIsPlaying;
    }

}

