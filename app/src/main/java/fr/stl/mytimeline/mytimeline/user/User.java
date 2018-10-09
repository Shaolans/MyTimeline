package fr.stl.mytimeline.mytimeline.user;

import android.media.Image;

import java.util.Date;

public class User {
    private String pseudo;
    private String firstname;
    private String lastname;
    private Date birth;
    private Image picture;

    public User(String pseudo, String firstname, String lastname, Date birth, Image picture) {
        this.pseudo = pseudo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birth = birth;
        this.picture = picture;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Image getPicture() {
        return picture;
    }

    public void setPicture(Image picture) {
        this.picture = picture;
    }




}
