package dev.suncha.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Created by user on 7/5/2015.
 */
public class AddEventFragment extends DialogFragment {

    EditText eventTitle, eventDate, reminderDate, reminderTime;
    Button chooseTime, chooseReminderDate, chooseReminderTime;
    Switch aSwitch;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogView = layoutInflater.inflate(R.layout.add_event_to_calendar, null);

        eventTitle = (EditText) dialogView.findViewById(R.id.event_name_value);
        eventDate = (EditText) dialogView.findViewById(R.id.meeting_date_value);
        reminderDate = (EditText) dialogView.findViewById(R.id.reminder_date);
        reminderTime = (EditText) dialogView.findViewById(R.id.reminder_time);
        chooseTime = (Button) dialogView.findViewById(R.id.button_chooseTime);
        chooseReminderDate = (Button) dialogView.findViewById(R.id.chooseReminderDate);
        chooseReminderTime = (Button) dialogView.findViewById(R.id.chooseReminderTime);

        aSwitch = (Switch) dialogView.findViewById(R.id.reminder_switch);

        String received_followup_date = getArguments().getString("follow up date");
        eventDate.setText(received_followup_date);

        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //enable the underlying widgets
                    reminderDate.setVisibility(View.VISIBLE);
                    reminderTime.setVisibility(View.VISIBLE);
                    chooseReminderDate.setVisibility(View.VISIBLE);
                    chooseReminderTime.setVisibility(View.VISIBLE);

                } else if (!isChecked) {
                    //disable the underlying widgets
                    reminderDate.setVisibility(View.GONE);
                    reminderTime.setVisibility(View.GONE);
                    chooseReminderDate.setVisibility(View.GONE);
                    chooseReminderTime.setVisibility(View.GONE);
                }
            }
        });

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
