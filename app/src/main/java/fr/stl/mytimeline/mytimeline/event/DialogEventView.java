package fr.stl.mytimeline.mytimeline.event;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;

import fr.stl.mytimeline.mytimeline.R;
import fr.stl.mytimeline.mytimeline.ScrollingActivity;

public class DialogEventView extends DialogFragment {
    private EditText name;
    private EditText place;
    private EditText desc;
    private ImageView img;
    private RadioGroup rgfeel;
    private EventListHandler adapter;
    private ImageView imgv;
    private Event event;
    private Calendar cal;
    private TextView tvdate;
    private TextView time;
    private Uri imgvu;
    private ScrollingActivity mainActivity;




    public DialogEventView init(EventListHandler adapter, int position, ScrollingActivity mainActivity){
        this.adapter = adapter;
        event = adapter.getItem(position);
        this.mainActivity = mainActivity;
        return this;
    }


    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("View event");
        alertDialogBuilder.setView(getActivity().getLayoutInflater().inflate(R.layout.event_view, null));

        cal = Calendar.getInstance();


        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();


        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

        tvdate = alert.findViewById(R.id.date);
        time = alert.findViewById(R.id.time);
        rgfeel = alert.findViewById(R.id.radiogroup);
        name = alert.findViewById(R.id.event_name_edit);
        desc = alert.findViewById(R.id.desc);
        place = alert.findViewById(R.id.place);

        name.setEnabled(false);
        desc.setEnabled(false);
        place.setEnabled(false);

        ImageView positionview = alert.findViewById(R.id.positionview);
        positionview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = String.format(Locale.getDefault(), "geo:0,0?q="+event.getPlace());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                mainActivity.startActivity(intent);

            }
        });

        cal.setTime(event.getDate());

        tvdate.setText(cal.get(Calendar.DATE)+"/"+DateUtils.convertMonth(cal.get(Calendar.MONTH))+"/"+cal.get(Calendar.YEAR));
        time.setText(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE));

        imgv = alert.findViewById(R.id.imgv);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(event.getImg(), "image/*");
                startActivity(intent);

            }
        });


        name.setText(event.getName());
        place.setText(event.getPlace());
        if(event.getImg() != null) imgv.setImageURI(event.getImg());
        desc.setText(event.getText_content());
        Feeling f = event.getFeel();
        ((RadioButton)alert.findViewById(R.id.rb_rhappy)).setEnabled(false);
        ((RadioButton)alert.findViewById(R.id.rb_happy)).setEnabled(false);
        ((RadioButton)alert.findViewById(R.id.rb_meh)).setEnabled(false);
        ((RadioButton)alert.findViewById(R.id.rb_sad)).setEnabled(false);
        ((RadioButton)alert.findViewById(R.id.rb_rsad)).setEnabled(false);
        switch(f){
            case RHAPPY:
                ((RadioButton)alert.findViewById(R.id.rb_rhappy)).setChecked(true);
                ((RadioButton)alert.findViewById(R.id.rb_rhappy)).setEnabled(true);
                break;
            case HAPPY:
                ((RadioButton)alert.findViewById(R.id.rb_happy)).setChecked(true);
                ((RadioButton)alert.findViewById(R.id.rb_happy)).setEnabled(true);
                break;
            case MEH:
                ((RadioButton)alert.findViewById(R.id.rb_meh)).setChecked(true);
                ((RadioButton)alert.findViewById(R.id.rb_meh)).setEnabled(true);
                break;
            case SAD:
                ((RadioButton)alert.findViewById(R.id.rb_sad)).setChecked(true);
                ((RadioButton)alert.findViewById(R.id.rb_sad)).setEnabled(true);
                break;
            case RSAD:
                ((RadioButton)alert.findViewById(R.id.rb_rsad)).setChecked(true);
                ((RadioButton)alert.findViewById(R.id.rb_rsad)).setEnabled(true);
                break;
        }

        return alert;
    }

    public static final int PICK_IMAGE = 1;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            if(data != null){
                imgv.setImageURI(data.getData());
                imgvu = data.getData();
                Toast.makeText(getActivity(), data.getData().toString()+" added to event", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getActivity(), "No image selected", Toast.LENGTH_LONG).show();
            }
        }
    }
}
