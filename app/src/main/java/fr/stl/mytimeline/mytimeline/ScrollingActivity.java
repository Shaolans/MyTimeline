package fr.stl.mytimeline.mytimeline;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

import fr.stl.mytimeline.mytimeline.event.DialogEvent;
import fr.stl.mytimeline.mytimeline.event.DialogEventEdit;
import fr.stl.mytimeline.mytimeline.event.Event;
import fr.stl.mytimeline.mytimeline.event.EventListHandler;
import fr.stl.mytimeline.mytimeline.meteo.JSONWeatherTask;


public class ScrollingActivity extends AppCompatActivity {
    private static int cpt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initChannels(this);

        List<Event> events = new ArrayList<>();
        final EventListHandler adapter = new EventListHandler(this, android.R.layout.simple_list_item_1, events, this);
        final ListView list = findViewById(R.id.listevents);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                PopupMenu popup = new PopupMenu(ScrollingActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_msg:
                                DialogEventEdit de = new DialogEventEdit().init(adapter, position);
                                de.show(ScrollingActivity.this.getSupportFragmentManager(), "Dialog_event_edit");
                                return true;
                            case R.id.remove_msg:
                                adapter.remove(adapter.getItem(position));
                                return true;
                            default:
                                return false;
                        }

                    }
                });
                popup.show();
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                PopupMenu popup = new PopupMenu(ScrollingActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_msg:
                                DialogEventEdit de = new DialogEventEdit().init(adapter, position);
                                de.show(ScrollingActivity.this.getSupportFragmentManager(), "Dialog_event_edit");
                                return true;
                            case R.id.remove_msg:
                                adapter.remove(adapter.getItem(position));
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEvent de = new DialogEvent().init(adapter);
                FragmentManager fm = getSupportFragmentManager();
                de.show(fm, "Dialog_event");

            }
        });

        String location = "Paris,FR";
        new JSONWeatherTask(this).execute(new String[] {location});
    }

    public void initChannels(Context context) {
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("default",
                "Channel name",
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Channel description");
        notificationManager.createNotificationChannel(channel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
