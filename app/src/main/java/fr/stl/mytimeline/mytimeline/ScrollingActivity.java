package fr.stl.mytimeline.mytimeline;


import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import fr.stl.mytimeline.mytimeline.storage.InternalStorage;
import fr.stl.mytimeline.mytimeline.event.DialogEvent;
import fr.stl.mytimeline.mytimeline.event.DialogEventEdit;
import fr.stl.mytimeline.mytimeline.event.DialogEventView;
import fr.stl.mytimeline.mytimeline.event.Event;
import fr.stl.mytimeline.mytimeline.event.EventListHandler;
import fr.stl.mytimeline.mytimeline.meteo.JSONWeatherTask;
import fr.stl.mytimeline.mytimeline.timelines.Timeline;
import fr.stl.mytimeline.mytimeline.timelines.TimelineListHandler;


public class ScrollingActivity extends AppCompatActivity {
    List<Event> events;
    List<Timeline> timelines;
    String current_timeline;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        initChannels(this);
        drawer = findViewById(R.id.drawer_layout);
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);

        try{
            this.getReadMediaPermission();

            timelines = InternalStorage.readTimelinesArrayInSharedPreferences(this,"timelines");
            current_timeline = InternalStorage.readInSharedPreferences(this,"current_timeline");
            if(!current_timeline.equals("")){
                events = (List<Event>) InternalStorage.readObject(this,current_timeline);
            }
            collapsingToolbarLayout.setTitle(current_timeline);

        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        if(current_timeline.equals("")){
            current_timeline = "MyTimeline";
        }

        if(timelines.isEmpty()){
            timelines.add(new Timeline(current_timeline));
        }

        if(events == null){
            events = new ArrayList<>();
            try{
                InternalStorage.writeObject(this,current_timeline,events);
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        final TimelineListHandler tmAdapter = new TimelineListHandler(this,android.R.layout.simple_list_item_1, timelines);
        final ListView timeline_list = findViewById(R.id.timelines_list);
        timeline_list.setAdapter(tmAdapter);
        Button button = findViewById(R.id.new_tm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity.this);
                builder.setTitle("Add new timeline");
                final EditText input = new EditText(ScrollingActivity.this);
                builder.setView(input);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String enter = input.getText().toString();
                        if(enter.equals("")){
                            Toast.makeText(ScrollingActivity.this,"Empty name is not accepted.", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                        else{
                            boolean exist = false;
                            for(int i=0; i<tmAdapter.getCount(); i++){
                                if(enter.equals(tmAdapter.getItem(i).getName())){
                                    exist = true;
                                }
                            }
                            if(exist){
                                Toast.makeText(ScrollingActivity.this,"Name already exists.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
                            }
                            else{
                                tmAdapter.add(new Timeline(enter));
                                try{
                                    InternalStorage.writeObject(ScrollingActivity.this, enter, new ArrayList<Event>());
                                } catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        // scrolling
        timeline_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            };
        });

        final EventListHandler adapter = new EventListHandler(this, android.R.layout.simple_list_item_1, events, this);
        final ListView list = findViewById(R.id.listevents);
        list.setAdapter(adapter);

        /*
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());
        adapter.add(new Event());

        adapter.sort(new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if(o1.getDate().before(o2.getDate())) return 1;
                if(o1.getDate().equals(o2.getDate())) return 0;
                return -1;
            }
        });*/


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


        timeline_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
                try{
                    if(!current_timeline.equals(tmAdapter.getItem(position).getName())){
                        List<Event> data_copy = new ArrayList<Event>();
                        for(int i=0; i<adapter.getCount(); i++){
                            data_copy.add(adapter.getItem(i));
                        }
                        InternalStorage.writeObject(ScrollingActivity.this, current_timeline, data_copy);
                        current_timeline = tmAdapter.getItem(position).getName();
                        events.clear();
                        events = (List<Event>) InternalStorage.readObject(ScrollingActivity.this,current_timeline);
                        adapter.clear();
                        adapter.addAll(events);
                        collapsingToolbarLayout.setTitle(current_timeline);
                    }
                    drawer.closeDrawers();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }

            }
        });


        timeline_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id) {
                final String name = tmAdapter.getItem(position).getName();
                PopupMenu popup = new PopupMenu(ScrollingActivity.this, v);
                popup.getMenuInflater().inflate(R.menu.popup_menu_timelines, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit_timelines:
                                AlertDialog.Builder builder = new AlertDialog.Builder(ScrollingActivity.this);
                                builder.setTitle("Rename timeline");
                                final EditText input = new EditText(ScrollingActivity.this);
                                input.setText(name);
                                builder.setView(input);

                                builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String enter = input.getText().toString();
                                        if(enter.equals("")){
                                            Toast.makeText(ScrollingActivity.this,"Empty name is not accepted.", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                        else{
                                            if(!enter.equals(name)) {
                                                boolean exist = false;
                                                for (int i = 0; i < tmAdapter.getCount(); i++) {
                                                    if (enter.equals(tmAdapter.getItem(i).getName())) {
                                                        exist = true;
                                                    }
                                                }
                                                if (exist) {
                                                    Toast.makeText(ScrollingActivity.this, "Name already exists.", Toast.LENGTH_SHORT).show();
                                                    dialog.cancel();
                                                } else {
                                                    InternalStorage.renameFile(ScrollingActivity.this, name, enter);
                                                    tmAdapter.getItem(position).setName(enter);
                                                    if(current_timeline.equals(name)){
                                                        collapsingToolbarLayout.setTitle(enter);
                                                    }
                                                    tmAdapter.notifyDataSetChanged();
                                                }
                                            }
                                            else{
                                                Toast.makeText(ScrollingActivity.this, "Nothing changed.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }
                                });

                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                builder.show();

                                return true;
                            case R.id.remove_timelines:
                                if(current_timeline.equals(tmAdapter.getItem(position).getName())){
                                    Toast.makeText(ScrollingActivity.this,"You can't remove the current timeline.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    InternalStorage.removeFile(ScrollingActivity.this, tmAdapter.getItem(position).getName());
                                    tmAdapter.remove(tmAdapter.getItem(position));
                                }
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



        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_btn_1, R.drawable.ic_arrow_drop_down_black_24dp)
                        .setLabel("Most to bottom")
                        .create()
        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_btn_2, R.drawable.ic_event_black_24dp)
                        .setLabel("Add event")
                        .create()
        );
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.fab_btn_3, R.drawable.ic_search_black_24dp)
                        .setLabel("Scroll to date")
                        .create()
        );


        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch(actionItem.getId()){
                    case R.id.fab_btn_1:
                        list.smoothScrollToPosition(adapter.getCount());
                        return true;
                    case R.id.fab_btn_2:
                        DialogEvent de = new DialogEvent().init(adapter, ScrollingActivity.this);
                        FragmentManager fm = getSupportFragmentManager();
                        de.show(fm, "Dialog_event");
                        getPosition();
                        return true;
                    case R.id.fab_btn_3:
                        Calendar c =  Calendar.getInstance();
                        DatePickerDialog datepick = new DatePickerDialog(ScrollingActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                int scrollposition = 0;
                                boolean foundyear = false;
                                boolean foundmonth = false;
                                boolean foundday = false;
                                for(int i = 0; i < adapter.getCount(); i++){
                                    Event e = adapter.getItem(i);
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(e.getDate());
                                    if(!foundyear && cal.get(Calendar.YEAR) == year){
                                        foundyear = true;
                                        scrollposition = i;
                                    }

                                    if(!foundmonth && cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month){
                                        foundmonth = true;
                                        scrollposition = i;
                                    }

                                    if(!foundday && cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month && cal.get(Calendar.DAY_OF_MONTH) == dayOfMonth){
                                        foundday = true;
                                        scrollposition = i;
                                    }

                                    list.smoothScrollToPositionFromTop(scrollposition, 0);

                                }
                            }
                        }, c.get(Calendar.YEAR),  c.get(Calendar.MONTH),  c.get(Calendar.DATE));
                        datepick.show();

                }
                return false;
            }
        });

        Address address = getPosition();
        String location = "Paris,FR";
        if(address != null){
            location = address.getLocality();
        }


        new JSONWeatherTask(this).execute(new String[] {location});

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);







        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if("text/plain".equals(type)){
                DialogEvent de = new DialogEvent().init(adapter, ScrollingActivity.this, intent.getStringExtra(Intent.EXTRA_TEXT));
                FragmentManager fm = getSupportFragmentManager();
                de.show(fm, "Dialog_event");
            } else if (type.startsWith("image/")) {
                Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
                DialogEvent de = new DialogEvent().init(adapter, ScrollingActivity.this, imageUri);
                FragmentManager fm = getSupportFragmentManager();
                de.show(fm, "Dialog_event");
            }
        }

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        
        if (id == android.R.id.home){
            drawer.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        storeAll();
        super.onDestroy();
    }

    public void storeAll(){
        try{
            InternalStorage.writeTimelinesArrayInSharedPreferences(this,"timelines", timelines);
            InternalStorage.writeInSharedPreferences(this,"current_timeline", current_timeline);
            InternalStorage.writeObject(this, current_timeline, events);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

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

    public boolean getReadMediaPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        int res = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if(res == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        else {
            return false;
        }
    }
}
