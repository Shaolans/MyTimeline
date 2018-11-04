package fr.stl.mytimeline.mytimeline.timelines;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fr.stl.mytimeline.mytimeline.R;

public class TimelineListHandler extends ArrayAdapter<Timeline> {

    public TimelineListHandler(@NonNull Context context, int resource, @NonNull List<Timeline> objects){
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent){
        View view = convertView;
        if( convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.content_list_timelines, null);
        }
        final Timeline text = getItem(position);
        final TextView tv = view.findViewById(R.id.timeline_name);
        tv.setText(text.getName());
        return view;
    }
}
