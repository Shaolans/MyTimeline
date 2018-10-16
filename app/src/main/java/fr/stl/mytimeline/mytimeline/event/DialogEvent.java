package fr.stl.mytimeline.mytimeline.event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Comparator;


import fr.stl.mytimeline.mytimeline.R;
import fr.stl.mytimeline.mytimeline.ScrollingActivity;

public class DialogEvent {

    public static class ViewHolder {
        EditText name;
        EditText place;
        EditText desc;
        RadioGroup rgfeel;
    }

    public static android.support.v7.app.AlertDialog createAddEvent(final EventListHandler adapter, final ScrollingActivity context){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Add an event");
        alertDialogBuilder.setView(context.getLayoutInflater().inflate(R.layout.add_event_dialog, null));

        final Calendar cal = Calendar.getInstance();



        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();

        final ViewHolder holder = new ViewHolder();

        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = holder.rgfeel.getCheckedRadioButtonId();
                Feeling value  = Feeling.RHAPPY;
                switch(selectedId){
                    case R.id.rb_rhappy:
                        value = Feeling.RHAPPY;
                        break;
                    case R.id.rb_happy:
                        value = Feeling.HAPPY;
                        break;
                    case R.id.rb_meh:
                        value = Feeling.MEH;
                        break;
                    case R.id.rb_sad:
                        value = Feeling.SAD;
                        break;
                    case R.id.rb_rsad:
                        value = Feeling.RSAD;
                        break;
                }


                adapter.add(new Event(0, holder.name.getText().toString(), cal.getTime(), value, null,
                        holder.desc.getText().toString(),holder.place.getText().toString()));
                adapter.sort(new Comparator<Event>() {
                    @Override
                    public int compare(Event o1, Event o2) {
                        if(o1.getDate().before(o2.getDate())) return -1;
                        if(o1.getDate().after(o2.getDate())) return 1;
                        return 0;
                    }
                });
                dialog.dismiss();
            }
        });
        alert.show();

        final TextView tvdate = alert.findViewById(R.id.date);
        final TextView time = alert.findViewById(R.id.time);
        holder.rgfeel = alert.findViewById(R.id.radiogroup);
        holder.name = alert.findViewById(R.id.event_name_edit);
        holder.desc = alert.findViewById(R.id.desc);
        holder.place = alert.findViewById(R.id.place);

        tvdate.setText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, android.R.style.Theme_Holo_Light);
                dpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dpd.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        cal.set(year, month, dayOfMonth);
                        tvdate.setText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
                    }
                });
                dpd.show();
            }
        });
        time.setText(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog tpd = new TimePickerDialog(context, android.R.style.Theme_Holo_Light,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                cal.set(Calendar.MINUTE, minute);
                                time.setText(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));
                            }
                        }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);
                tpd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                tpd.show();
            }
        });

        return alert;
    }
}
