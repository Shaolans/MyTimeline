package fr.stl.mytimeline.mytimeline.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.stl.mytimeline.mytimeline.event.Event;
import fr.stl.mytimeline.mytimeline.storage.InternalStorage;
import fr.stl.mytimeline.mytimeline.timelines.Timeline;

public class RebootNotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            List<Timeline> timelines = InternalStorage.readTimelinesArrayInSharedPreferences(context,"timelines");
            List<Event> events = new ArrayList<>();
            try{
                for(Timeline t: timelines){
                    events.addAll((List<Event>) InternalStorage.readObject(context, t.getName()));

                }
            }catch (Exception e){

            }
            Date d = new Date();

            for(Event e: events){
                if(e.isNotify() && d.before(e.getDate())){
                    NotificationUtil.setNotification(e, context);
                }
            }

        }
    }
}
