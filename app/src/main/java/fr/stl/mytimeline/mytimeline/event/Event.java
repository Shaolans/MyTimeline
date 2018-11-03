package fr.stl.mytimeline.mytimeline.event;

import android.net.Uri;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class Event implements Serializable {
    private int id;
    private String name;
    private Date date;
    private Feeling feel;
    private Uri img;
    private String text_content;
    private String place;
    private static int cpt = 0;

    private Date generateRandomDate(){
        long beginTime = Timestamp.valueOf("2016-01-01 00:00:00").getTime();
        long endTime = Timestamp.valueOf("2018-01-01 00:00:00").getTime();
        long diff = endTime - beginTime + 1;
        Date random = new Date( beginTime + (long) (Math.random() * diff));
        return random;
    }

    public Event(){
        this.id = cpt;
        this.name = "Event "+cpt;
        this.date = generateRandomDate();
        this.feel = Feeling.HAPPY;
        this.img = null;
        this.text_content = "Event content "+cpt;
        this.place = "Event place "+cpt;
        cpt++;
    }

    public Event(int id, String name, Date date, Feeling feel, Uri img, String text_content, String place) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.feel = feel;
        this.img = img;
        this.text_content = text_content;
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Feeling getFeel() {
        return feel;
    }

    public void setFeel(Feeling feel) {
        this.feel = feel;
    }

    public Uri getImg() {
        return img;
    }

    public void setImg(Uri img) {
        this.img = img;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }


}
