package fr.stl.mytimeline.mytimeline.meteo;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_KEY = "&APPID=ecdbb0174f9e1c84646a8c5e83216bc6";

    public String getWeatherData(String location) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(BASE_URL + location + API_KEY)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ( (line = br.readLine()) != null){
                buffer.append(line+"rn");
            }
            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch (Throwable t){
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t){}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;
    }
}
