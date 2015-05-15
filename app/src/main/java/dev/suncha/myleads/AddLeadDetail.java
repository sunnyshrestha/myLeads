package dev.suncha.myleads;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//http://www.mkyong.com/android/android-date-picker-example/
public class AddLeadDetail extends ActionBarActivity {
    static final int DATE_DIALOG_ID = 999;
    final Calendar c = Calendar.getInstance();
    EditText organisation_name, organisation_address, organisation_phone, website, person_name, designation, person_mobile, person_email, product, meeting_date, follow_up, remarks;
    Button pick_meetingdate, pick_followup, button_save;
    int month, year, day;
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead_detail);

        organisation_name = (EditText) findViewById(R.id.et_organisation_name);
        organisation_address = (EditText) findViewById(R.id.et_organisation_address);
        organisation_phone = (EditText) findViewById(R.id.et_organisation_phone);
        website = (EditText) findViewById(R.id.et_website);

        person_name = (EditText) findViewById(R.id.et_person_name);
        designation = (EditText) findViewById(R.id.et_designation);
        person_mobile = (EditText) findViewById(R.id.et_mobile_no);
        person_email = (EditText) findViewById(R.id.et_email);

        product = (EditText) findViewById(R.id.et_product);
        meeting_date = (EditText) findViewById(R.id.et_meetingdate);
        follow_up = (EditText) findViewById(R.id.et_follow_up);
        remarks = (EditText) findViewById(R.id.et_remarks);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        meeting_date.getLayoutParams().width = width / 2;
        follow_up.getLayoutParams().width = width / 2;

        pick_meetingdate = (Button) findViewById(R.id.button_meetingdate);
        pick_followup = (Button) findViewById(R.id.button_followupdate);

        button_save = (Button) findViewById(R.id.button_save);


        pick_meetingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetingDatePicker(meeting_date);
            }
        });

        pick_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meetingDatePicker(follow_up);
            }
        });
    }

    public void meetingDatePicker(View view) {
        // Checks whether to add date to meeting date or follow-up date
        int check = -1;
        if (view == meeting_date)
            check = 0;
        else if (view == follow_up)
            check = 1;

        showDatePicker(check, mYear, mMonth, mDay);

    }

    public void showDatePicker(final int finalCheck, int mYear, int mMonth, int mDay) {
        //shows the actual datepicker dialog
        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // Display Selected date in textbox
                        switch (finalCheck) {
                            case 0:
                                meeting_date.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                if (meeting_date.getText().toString().trim().length() > 0 && follow_up.getText().toString().trim().length() > 0) {
                                    checkDateOrder(meeting_date.getText().toString(), follow_up.getText().toString());
                                }
                                break;
                            case 1:
                                follow_up.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);
                                if (meeting_date.getText().toString().trim().length() > 0 && follow_up.getText().toString().trim().length() > 0) {
                                    checkDateOrder(meeting_date.getText().toString(), follow_up.getText().toString());
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }, mYear, mMonth, mDay);
        dpd.show();
//        if (meeting_date.getText().toString().trim().length() > 0 && follow_up.getText().toString().trim().length() > 0) {
//            checkDateOrder(meeting_date.toString(), follow_up.toString());
//        }
    }

    public void checkDateOrder(String meetingDate, String followupDate) {
        //checks if the followup date is after the meetingDate or not
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = simpleDateFormat.parse(meetingDate);
            Date date2 = simpleDateFormat.parse(followupDate);

            if (date1.after(date2) || date1.equals(date2)) {
                new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                        .setTitle("Warning")
                        .setMessage("Follow up date needs to be after the meeting date")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                meetingDatePicker(follow_up);
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_lead_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
