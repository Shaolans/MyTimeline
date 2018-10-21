package fr.stl.mytimeline.mytimeline.meteo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static Weather getWeather(String data) throws JSONException {
        JSONObject obj = new JSONObject(data);

        Weather weather = new Weather();

        JSONObject sysObj = getObject("sys", obj);
        weather.setCountry(getString("country",sysObj));
        weather.setCity(getString("name",obj));

        JSONArray arr = obj.getJSONArray("weather");
        JSONObject jsonWeather = arr.getJSONObject(0);
        weather.setDescription(getString("description", jsonWeather));
        weather.setMain(getString("main", jsonWeather));
        weather.setIcon(getString("icon", jsonWeather));

        JSONObject main = getObject("main", obj);
        weather.setTemp(getDouble("temp", main));

        return weather;
    }

    private static JSONObject getObject(String tagName, JSONObject obj) throws JSONException {
        return obj.getJSONObject(tagName);
    }

    private static String getString(String tagName, JSONObject obj) throws JSONException {
        return obj.getString(tagName);
    }

    private static double getDouble(String tagName, JSONObject obj) throws JSONException {
        return obj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject obj) throws JSONException {
        return obj.getInt(tagName);
    }
}
