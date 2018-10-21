package fr.stl.mytimeline.mytimeline.meteo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.net.URL;

public class Weather {
    private static String IMG_URL = "http://openweathermap.org/img/w/";

    private String country;
    private String city;
    private String description;
    private String main;
    private String icon;
    private double temp;
    private Bitmap img_bitmap;

    public Weather(){
        this.country = null;
        this.city = null;
        this.description = null;
        this.main = null;
        this.icon = null;
        this.temp = 0;
        this.img_bitmap = null;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
        try{
            URL img_url = new URL(IMG_URL+icon+".png");
            this.img_bitmap = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public Bitmap getImg_bitmap() {
        return img_bitmap;
    }
}
