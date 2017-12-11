package com.thkoeln.paulo.hearme;

/**
 * Created by trisbeta on 11.12.17.
 */

class Post {
    /**
     * Created by trisbeta on 11.12.17.
     */

        public Integer id;
        public String title, author, gps;

        //public Integer id, rel, category;
        //public String title, author, url, gps, status, date;


        public Post (Integer id, String title, String author, String gps) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.gps = gps;
        }

}
