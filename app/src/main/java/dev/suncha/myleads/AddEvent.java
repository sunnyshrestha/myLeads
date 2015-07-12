package dev.suncha.myleads;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AddEvent extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    EditText eventTitle, eventDate, reminderDate, reminderTime, eventTime;
    Button chooseTime, chooseReminderDate, chooseReminderTime, addEvent, cancel;
    Switch aSwitch;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    int timepickertracker = -1;
    int hourOfEvent, minuteOfEvent, yearOfEvent, monthOfEvent, dateOfEvent;


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
                timepickertracker = 0;
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(fragmentManager, "TIME PICKER");

            }
        });

        chooseReminderDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDateFragment selectDateFragment = new SelectDateFragment();
                selectDateFragment.show(fragmentManager, "DATE PICKER");
            }
        });

        chooseReminderTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepickertracker = 1;
                TimePickerFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(fragmentManager, "REMINDER TIME PICKER");
            }
        });

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add event to calendar
                checkDateOrder(eventDate.getText().toString(), reminderDate.getText().toString());
                addEventToCalendar();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addEventToCalendar() {
        String meetingDate = eventDate.getText().toString();

        Cursor cursor = null;
        //int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars._ID)));
        //Constructing event details
        long startMillis = 0;
        String parts[] = meetingDate.split("-");
        String dateOfMeeting = parts[0];
        String monthOfMeeting = parts[1];
        String yearOfMeeting = parts[2];
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(Integer.valueOf(yearOfMeeting), Integer.valueOf(monthOfMeeting), Integer.valueOf(dateOfMeeting), hourOfEvent, minuteOfEvent);

        //Inserting event
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        TimeZone timeZone = TimeZone.getDefault();
        contentValues.put(CalendarContract.Events.DTSTART, startMillis);
        contentValues.put(CalendarContract.Events.DURATION, 3000); //MANDATORY FIELD RAICHA
        contentValues.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        contentValues.put(CalendarContract.Events.TITLE, eventTitle.getText().toString());
        contentValues.put(CalendarContract.Events.CALENDAR_ID, 3);
        Uri uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, contentValues);

        //Retrieve eventId
        String eventID = uri.getLastPathSegment();

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
        String ampm;
        hourOfEvent = hourOfDay;
        minuteOfEvent = minute;
        int hour = hourOfDay;
        if (hourOfDay >= 12) {
            ampm = "PM";
        } else
            ampm = "AM";

        if (hourOfDay > 12) {
            hour = hourOfDay - 12;
        }

        if (hour == 0)
            hour = 12;
        if (timepickertracker == 0) {
            if (minute < 10)
                eventTime.setText(hour + ":0" + minute + " " + ampm);
            else
                eventTime.setText(hour + ":" + minute + " " + ampm);
        } else if (timepickertracker == 1) {
            if (minute < 10)
                reminderTime.setText(hour + ":0" + minute + " " + ampm);
            else
                reminderTime.setText(hour + ":" + minute + " " + ampm);
        }

    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        yearOfEvent = year;
        monthOfEvent = monthOfYear;
        dateOfEvent = dayOfMonth;

        reminderDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        if (eventDate.getText().toString().trim().length() > 0 && reminderDate.getText().toString().trim().length() > 0)
            checkDateOrder(eventDate.getText().toString(), reminderDate.getText().toString());

    }

    public void checkDateOrder(String meetingDate, String reminderdate) {
        //checks if the followup date is after the meetingDate or not
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = simpleDateFormat.parse(meetingDate);
            Date date2 = simpleDateFormat.parse(reminderdate);

            if (date2.after(date1)) {
                reminderDate.setText(null);
                reminderDateCheckDialog reminderdatecheckdialog = new reminderDateCheckDialog();
                reminderdatecheckdialog.show(fragmentManager, "Recheck date");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
