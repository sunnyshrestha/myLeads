package dev.suncha.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 7/5/2015.
 */
public class AddEventFragment extends DialogFragment {

    View dialogView;
    EditText eventTitle;
    Button chooseTime;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        eventTitle = (EditText) dialogView.findViewById(R.id.event_name_value);
        chooseTime = (Button) dialogView.findViewById(R.id.button_chooseTime);
        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "Fix Time", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        dialogView = layoutInflater.inflate(R.layout.add_event_to_calendar, null);
        builder.setView(dialogView)
                .setPositiveButton("Add to Calendar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //save calendar event
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do nothing
                    }
                });

        return builder.create();
    }

}
