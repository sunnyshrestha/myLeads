package dev.suncha.myleads;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

public class AddEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    EditText eventTitle, eventDate, reminderDate, reminderTime, eventTime;
    Button chooseTime, chooseReminderDate, chooseReminderTime, addEvent, cancel;
    Switch aSwitch;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setFinishOnTouchOutside(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_to_calendar);

        eventTitle = (EditText) findViewById(R.id.event_name_value);
        eventDate = (EditText) findViewById(R.id.meeting_date_value);
        reminderDate = (EditText) findViewById(R.id.reminder_date);
        reminderTime = (EditText) findViewById(R.id.reminder_time);
        chooseTime = (Button) findViewById(R.id.button_chooseTime);
        chooseReminderDate = (Button) findViewById(R.id.chooseReminderDate);
        chooseReminderTime = (Button) findViewById(R.id.chooseReminderTime);
        eventTime = (EditText) findViewById(R.id.meeting_time_value);
        addEvent = (Button) findViewById(R.id.button_add);
        cancel = (Button) findViewById(R.id.button_cancel);

        aSwitch = (Switch) findViewById(R.id.reminder_switch);

        loadExtras();
        switchHandler();

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(fragmentManager, "TIME PICKER");
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add event to calendar
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadExtras() {
        Intent intent = getIntent();
        if (intent.getStringExtra("person name").length() != 0 || intent.getStringExtra("company name").length() != 0)
            eventTitle.setText("Follow-up with " + intent.getStringExtra("person name") + " of " + intent.getStringExtra("company name"));
        eventDate.setText(intent.getStringExtra("follow up date"));
    }

    public void switchHandler() {
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveDialogFragment savedialogfragment = new saveDialogFragment();
        savedialogfragment.show(fragmentManager, "SAVE DIALOG");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        eventTime.setText(hourOfDay + ":" + minute);
    }
}
