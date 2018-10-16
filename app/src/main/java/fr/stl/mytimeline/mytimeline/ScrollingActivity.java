package fr.stl.mytimeline.mytimeline;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.stl.mytimeline.mytimeline.event.Event;
import fr.stl.mytimeline.mytimeline.event.EventListHandler;
import fr.stl.mytimeline.mytimeline.event.Feeling;

public class ScrollingActivity extends AppCompatActivity {
    private static int cpt = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        List<Event> events = new ArrayList<>();
        final EventListHandler adapter = new EventListHandler(this, android.R.layout.simple_list_item_1, events);
        final ListView list = findViewById(R.id.listevents);
        list.setAdapter(adapter);
        adapter.add(new Event(cpt++, "Event4", new Date(), Feeling.HAPPY, null, "text", "Paris"));
        adapter.add(new Event(cpt++, "Event2", new Date(), Feeling.HAPPY, null, "text", "Paris"));
        adapter.add(new Event(cpt++, "Event3", new Date(), Feeling.HAPPY, null, "text", "Paris"));

        FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*final Dialog dialog = new Dialog(ScrollingActivity.this);
                dialog.setContentView(R.layout.add_event_dialog);
                dialog.setTitle("Add new event");
                dialog.show();*/

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ScrollingActivity.this);
                alertDialogBuilder.setTitle("Your Title");
                alertDialogBuilder.setView(ScrollingActivity.this.getLayoutInflater().inflate(R.layout.add_event_dialog, null));
                alertDialogBuilder.create().show();


            }
        });



    }

    public void addEvent(EventListHandler adapter){
        final FloatingActionButton fab = findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
