package fr.stl.mytimeline.mytimeline;


import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.stl.mytimeline.mytimeline.event.DialogEvent;
import fr.stl.mytimeline.mytimeline.event.DialogEventEdit;
import fr.stl.mytimeline.mytimeline.event.DialogEventView;
import fr.stl.mytimeline.mytimeline.event.Event;
import fr.stl.mytimeline.mytimeline.event.EventListHandler;
import fr.stl.mytimeline.mytimeline.meteo.JSONWeatherTask;


public class ScrollingActivity extends AppCompatActivity {
    private static int cpt = 0;

    public Address getPosition(){
        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Location location;

        if(network_enabled){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            int res = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if(res == PackageManager.PERMISSION_GRANTED){
                location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(location!=null){
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                    try {
                        return geocoder.getFromLocation(latitude, longitude, 1).get(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
        return null;


    }

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
                DialogEventView dev = new DialogEventView().init(adapter, position, ScrollingActivity.this);
                dev.show(ScrollingActivity.this.getSupportFragmentManager(), "Dialog_event_view");
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
                            case R.id.view_msg:
                                DialogEventView dev = new DialogEventView().init(adapter, position, ScrollingActivity.this);
                                dev.show(ScrollingActivity.this.getSupportFragmentManager(), "Dialog_event_view");
                                return true;
                            case R.id.share_msg:
                                Event e = adapter.getItem(position);
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                String text = "MyTimeline event !\n";
                                text += "title: "+e.getName() +"\n";
                                text += "place: "+e.getPlace() +"\n";
                                text += "date: "+e.getDate() +"\n";
                                text += "description: "+e.getText_content() +"\n";
                                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                                if(e.getImg() != null){
                                    shareIntent.putExtra(Intent.EXTRA_STREAM, e.getImg());
                                }
                                shareIntent.setType("image/jpeg");
                                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                startActivity(Intent.createChooser(shareIntent, "share_event"));
                                return true;
                            case R.id.edit_msg:
                                DialogEventEdit de = new DialogEventEdit().init(adapter, position, ScrollingActivity.this);
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
                DialogEvent de = new DialogEvent().init(adapter, ScrollingActivity.this);
                FragmentManager fm = getSupportFragmentManager();
                de.show(fm, "Dialog_event");
                getPosition();

            }
        });

        Address address = getPosition();
        String location = "Paris,FR";
        if(address != null){
            location = address.getLocality();
        }


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
