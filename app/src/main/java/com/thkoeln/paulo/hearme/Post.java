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
        public double longitude, latitude;

        //public Integer id, rel, category;
        //public String title, author, url, gps, status, date;


        public Post() {

        }

        public Post (Integer id, String title, String author, double latitude, double longitude) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.longitude = longitude;
            this.latitude = latitude;
        }

        public String getTitle() {
            return title;
    }
        public double getlongitude() {return longitude;}
        public double getlatitude() {
        return latitude;
    }

}
