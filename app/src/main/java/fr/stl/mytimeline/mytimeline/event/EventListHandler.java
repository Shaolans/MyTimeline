package fr.stl.mytimeline.mytimeline.event;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import fr.stl.mytimeline.mytimeline.R;

public class EventListHandler extends ArrayAdapter<Event> {
    private AppCompatActivity activity;

    public EventListHandler(Context context, int resource) {
        super(context, resource);
    }

    public EventListHandler(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public EventListHandler(Context context, int resource, Event[] objects) {
        super(context, resource, objects);
    }

    public EventListHandler(Context context, int resource, int textViewResourceId, Event[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public EventListHandler(Context context, int resource, List<Event> objects) {
        super(context, resource, objects);
    }

    public EventListHandler(Context context, int resource, int textViewResourceId, List<Event> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public AppCompatActivity getActivity(){
        return activity;
    }

    public void init(AppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    public @NonNull View getView(int position, @Nullable View convertView,
                                 @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.content_timeline, null);
        }
        final int pos = position;
        final ImageView smiley = view.findViewById(R.id.msg_smiley);
        final TextView date = view.findViewById(R.id.msg_date);
        final TextView title = view.findViewById(R.id.msg_title);
        final TextView time = view.findViewById(R.id.msg_time);

        final Event event = getItem(position);
        switch(event.getFeel()){
            case RHAPPY:
                smiley.setImageResource(R.drawable.smiley_rhappy);
                break;
            case HAPPY:
                smiley.setImageResource(R.drawable.smiley_happy);
                break;
            case MEH:
                smiley.setImageResource(R.drawable.smiley_meh);
                break;
            case SAD:
                smiley.setImageResource(R.drawable.smiley_sad);
                break;
            case RSAD:
                smiley.setImageResource(R.drawable.smiley_rsad);
                break;
        }
        date.setText(event.getDate().getDay()+"/"+event.getDate().getMonth()+"/"+event.getDate().getYear());
        title.setText(event.getName());
        time.setText(event.getDate().getHours()+":"+event.getDate().getMinutes());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogEventEdit de = new DialogEventEdit().init(EventListHandler.this, pos);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                de.show(fm, "Dialog_event_edit");*/
            }
        });
        return view;
    }



}
