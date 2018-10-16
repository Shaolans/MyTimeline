package fr.stl.mytimeline.mytimeline.event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

import fr.stl.mytimeline.mytimeline.R;
import fr.stl.mytimeline.mytimeline.ScrollingActivity;

public class DialogEvent {

    public static AlertDialog createAddEvent(final EventListHandler adapter, final ScrollingActivity context){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Your Title");
        alertDialogBuilder.setView(context.getLayoutInflater().inflate(R.layout.add_event_dialog, null));
        final Date date = new Date();



        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        android.support.v7.app.AlertDialog alert = alertDialogBuilder.create();
        TextView tvdate = alert.findViewById(R.id.date);
        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(context, android.R.style.Theme_Holo_Light);
            }
        });
        return null;
    }
}
