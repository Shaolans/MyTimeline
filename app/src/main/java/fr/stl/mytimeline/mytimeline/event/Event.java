package fr.stl.mytimeline.mytimeline.event;

import android.media.Image;
import android.net.Uri;

import java.util.Date;

public class Event {
    private int id;
    private String name;
    private Date date;
    private Feeling feel;
    private Uri img;
    private String text_content;
    private String place;

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
