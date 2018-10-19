package fr.stl.mytimeline.mytimeline.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Comparator;

import fr.stl.mytimeline.mytimeline.R;

public class DialogEvent extends DialogFragment {

    private EditText name;
    private EditText place;
    private EditText desc;
    private ImageView img;
    private RadioGroup rgfeel;
    private EventListHandler adapter;
    private Activity activity;



    public Activity getActivityD(){
        return activity;
    }

    public DialogEvent init(EventListHandler adapter, Activity activity){
        this.adapter = adapter;
        this.activity = activity;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivityD());
        alertDialogBuilder.setTitle("Add an event");
        alertDialogBuilder.setView(getActivityD().getLayoutInflater().inflate(R.layout.add_event_dialog, null));

        final Calendar cal = Calendar.getInstance();



        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();


        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = rgfeel.getCheckedRadioButtonId();
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


                adapter.add(new Event(0, name.getText().toString(), cal.getTime(), value, null,
                        desc.getText().toString(),place.getText().toString()));
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
        rgfeel = alert.findViewById(R.id.radiogroup);
        name = alert.findViewById(R.id.event_name_edit);
        desc = alert.findViewById(R.id.desc);
        place = alert.findViewById(R.id.place);

        tvdate.setText(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(getActivityD(), android.R.style.Theme_Holo_Light);
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
                TimePickerDialog tpd = new TimePickerDialog(getActivityD(), android.R.style.Theme_Holo_Light,
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

        ImageButton imgbtn = alert.findViewById(R.id.imgbtn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                //setTargetFragment(DialogEvent.this, PICK_IMAGE);
                //getTargetFragment().onActivityResult(getTargetRequestCode(), 0, chooserIntent);
                //getTargetFragment().startActivityForResult(chooserIntent, PICK_IMAGE);
                startActivityForResult(chooserIntent, PICK_IMAGE);

            }
        });


        return alert;
    }

    public static final int PICK_IMAGE = 1;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Toast.makeText(getActivityD(), "LALALALA", Toast.LENGTH_LONG).show();
        }
    }
}
