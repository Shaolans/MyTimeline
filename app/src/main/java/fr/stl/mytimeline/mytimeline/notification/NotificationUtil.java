package fr.stl.mytimeline.mytimeline.notification;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import fr.stl.mytimeline.mytimeline.event.Event;

public class NotificationUtil {
    public static void cancelNotification(Event e, Context activity){
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(activity.ALARM_SERVICE);
        Intent intent =  new Intent(activity, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(activity, e.getId(), intent,PendingIntent.FLAG_CANCEL_CURRENT);


        alarmManager = (AlarmManager) activity.getSystemService(activity.ALARM_SERVICE);
        alarmManager.cancel(pi);
    }

    public static AlarmManager setNotification(Event e, Context activity){
        Intent alertIntent = new Intent(activity, NotificationReceiver.class);

        alertIntent
                .putExtra("name", e.getName())
                .putExtra("text_content", e.getText_content())
                .putExtra("place", e.getPlace());

        AlarmManager alarmManager = (AlarmManager)
                activity.getSystemService(activity.ALARM_SERVICE);

        PendingIntent pi = PendingIntent.getBroadcast(activity, e.getId(), alertIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, e.getDate().getTime(), pi);


        return alarmManager;
    }
}
