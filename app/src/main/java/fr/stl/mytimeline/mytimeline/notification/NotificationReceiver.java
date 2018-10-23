package fr.stl.mytimeline.mytimeline.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import fr.stl.mytimeline.mytimeline.R;
import fr.stl.mytimeline.mytimeline.ScrollingActivity;


public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        createNotification(context, intent);
    }

    public void createNotification(Context context, Intent intent){
        PendingIntent noficitIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, ScrollingActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.btn_add)
                .setContentTitle("The event "+intent.getStringExtra("name")+" at "+intent.getStringExtra("place"))
                .setTicker("")
                .setContentText(intent.getStringExtra("text_content"));

        mBuilder.setContentIntent(noficitIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(0, mBuilder.build());

    }
}
