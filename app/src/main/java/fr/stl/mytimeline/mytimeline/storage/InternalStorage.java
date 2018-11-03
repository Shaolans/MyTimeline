package fr.stl.mytimeline.mytimeline.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class InternalStorage {

    public static void writeObject(Context context, String filename, Object object) throws IOException {
        FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(object);
        oos.close();
        fos.close();
    }

    public static Object readObject(Context context, String filename) throws IOException, ClassNotFoundException{
        FileInputStream fis = context.openFileInput(filename);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object object = ois.readObject();
        return object;
    }

    public static boolean removeObject(Context context, String filename) {
        File f = new File(context.getFilesDir(), filename);
        return f.delete();
    }

    public static void writeArrayInSharedPreferences(Context context, String key, List<String> timelines){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        JSONArray jsonArray = new JSONArray();
        for(String s : timelines){
            jsonArray.put(s);
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, jsonArray.toString());
        editor.commit();
    }

    public static ArrayList<String> readArrayInSharedPreferences(Context context, String key){
        try{
            ArrayList<String> res = new ArrayList<String>();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            JSONArray jsonArray = new JSONArray(prefs.getString(key,"[]"));
            for (int i =0; i<jsonArray.length(); i++){
                res.add(jsonArray.get(i)+"");
            }
            return res;
        } catch (JSONException jsone){
            jsone.printStackTrace();
        }
        return null;
    }

    public static void deleteInSharedPreferences(Context context, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().remove(key).commit();
    }

    public static void writeInSharedPreferences(Context context, String key, String value){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, value).commit();
    }

    public static String readInSharedPreferences(Context context, String key){
        String res = "";
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        res = prefs.getString(key,"");
        return res;
    }
}
