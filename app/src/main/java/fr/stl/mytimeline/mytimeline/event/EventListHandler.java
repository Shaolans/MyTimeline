package fr.stl.mytimeline.mytimeline.event;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.stl.mytimeline.mytimeline.R;

public class EventListHandler extends ArrayAdapter<Event> {

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

    @Override
    public @NonNull View getView(int position, @Nullable View convertView,
                                 @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.content_timeline, null);
        }
        final ImageView smiley = view.findViewById(R.id.msg_smiley);
        final TextView date = view.findViewById(R.id.msg_date);
        final TextView title = view.findViewById(R.id.msg_title);
        final Event event = getItem(position);
        smiley.setImageResource(R.drawable.smiley_rhappy);
        date.setText(event.getText_content().toString());
        title.setText(event.getName());
        return view;
    }
}
