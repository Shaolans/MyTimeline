package fr.stl.mytimeline.mytimeline.meteo;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;

import java.net.URL;
import java.util.Locale;

import fr.stl.mytimeline.mytimeline.R;
import fr.stl.mytimeline.mytimeline.ScrollingActivity;


public class JSONWeatherTask extends AsyncTask<String, Void, Weather> {
    private ScrollingActivity activity;

    public JSONWeatherTask(ScrollingActivity activity){
        this.activity = activity;
    }

    @Override
    protected Weather doInBackground(String... locations) {
        Weather weather = new Weather();
        String data = (new WeatherHttpClient()).getWeatherData(locations[0]);

        try {
            weather = JSONWeatherParser.getWeather(data);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }

    @Override
    protected void onPostExecute(Weather weather){

        TextView city = (TextView) activity.findViewById(R.id.city);
        city.setText(weather.getCity());
        TextView country = (TextView) activity.findViewById(R.id.country);
        country.setText(weather.getCountry());
        TextView temp = (TextView) activity.findViewById(R.id.temp);
        temp.setText((int)(weather.getTemp()-275.15)+"°C");
        TextView descr = (TextView) activity.findViewById(R.id.descr);
        descr.setText(weather.getDescription().toUpperCase(Locale.FRANCE));
        ImageView weather_img = (ImageView) activity.findViewById(R.id.weather_icon);
        weather_img.setImageBitmap(weather.getImg_bitmap());

    }
}
