package dev.suncha.myleads;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class AddLeadDetail extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {
    static final int PICK_CONTACT_REQUEST = 0;
    final Calendar c = Calendar.getInstance();
    int check = -1;
    EditText organisation_name, organisation_address, organisation_phone, website, person_name, designation, person_mobile, person_email, product, meeting_date, follow_up, remarks;

    Button pick_meetingdate;
    Button pick_followup;
    Button pickNumFromContacts;
    Button addToCalendar;
    RelativeLayout parentLayout;

    DatabaseHelper dbHelper = new DatabaseHelper(AddLeadDetail.this);
    AlertDialog alert;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead_detail);

        setupToolbar();

        parentLayout = (RelativeLayout) findViewById(R.id.parentlayout);

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

        addToCalendar = (Button) findViewById(R.id.button_event);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        meeting_date.getLayoutParams().width = width * 11 / 20;
        follow_up.getLayoutParams().width = width * 11 / 20;
        person_mobile.getLayoutParams().width = width * 11 / 20;


        pick_meetingdate = (Button) findViewById(R.id.button_meetingdate);
        pick_followup = (Button) findViewById(R.id.button_followupdate);
        pickNumFromContacts = (Button) findViewById(R.id.picknumfromcontact);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        pickNumFromContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                readContact();
            }
        });

        pick_meetingdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                meetingDatePicker(meeting_date);

            }
        });

        pick_followup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                meetingDatePicker(follow_up);
            }
        });


        addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (follow_up.getText().length() != 0) {
                    if (isDateValid(follow_up.getText().toString())) {

                        String parts[] = follow_up.getText().toString().split("-");
                        String dateOfMeeting = parts[0];
                        String monthOfMeeting = parts[1];
                        String yearOfMeeting = parts[2];

                        GregorianCalendar eventDate = new GregorianCalendar(Integer.valueOf(yearOfMeeting),Integer.valueOf(monthOfMeeting)-1,Integer.valueOf(dateOfMeeting));

                        Intent calIntent = new Intent(Intent.ACTION_EDIT);
                        calIntent.setType("vnd.android.cursor.item/event");
                        calIntent.putExtra(CalendarContract.Events.TITLE, "Follow-up with " + person_name.getText().toString() + " of " + organisation_name.getText().toString());
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,eventDate.getTimeInMillis());
                        calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY,eventDate.getTimeInMillis());
                        startActivity(calIntent);

//                        Intent startAddEventIntent = new Intent(getApplicationContext(), AddEvent.class);
//                        startAddEventIntent.putExtra("follow up date", follow_up.getText().toString());
//                        startAddEventIntent.putExtra("person name", person_name.getText().toString());
//                        startAddEventIntent.putExtra("company name", organisation_name.getText().toString());
//                        startActivity(startAddEventIntent);
                    } else {
                        Snackbar
                                .make(parentLayout, R.string.dateformaterror, Snackbar.LENGTH_LONG)
                                .show();
                    }
                } else
                    Snackbar
                            .make(parentLayout, R.string.addfollowupfirst, Snackbar.LENGTH_LONG)
                            .show();
            }
        });
    }

    public boolean isDateValid(String date) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            date = follow_up.getText().toString();
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        CharSequence officename = organisation_name.getText();
        CharSequence officeaddress = organisation_address.getText();
        CharSequence officephone = organisation_phone.getText();
        CharSequence officewebsite = website.getText();
        CharSequence personname = person_name.getText();
        CharSequence persondesignation = designation.getText();
        CharSequence personmobile = person_mobile.getText();
        CharSequence personemail = person_email.getText();
        CharSequence productdiscussed = product.getText();
        CharSequence metdate = meeting_date.getText();
        CharSequence followup = follow_up.getText();
        CharSequence remark = remarks.getText();


        outState.putCharSequence("org_name", officename);
        outState.putCharSequence("org_address", officeaddress);
        outState.putCharSequence("org_phone", officephone);
        outState.putCharSequence("url", officewebsite);
        outState.putCharSequence("contact_name", personname);
        outState.putCharSequence("designation", persondesignation);
        outState.putCharSequence("mobile", personmobile);
        outState.putCharSequence("email", personemail);
        outState.putCharSequence("product", productdiscussed);
        outState.putCharSequence("meeting_date", metdate);
        outState.putCharSequence("follow_up", followup);
        outState.putCharSequence("remarks", remark);

    }

    protected void onRestoreInstanceState(Bundle savedState) {
        CharSequence officename = savedState.getCharSequence("org_name");
        CharSequence officeaddress = savedState.getCharSequence("org_address");
        CharSequence officephone = savedState.getCharSequence("org_phone");
        CharSequence officewebsite = savedState.getCharSequence("url");
        CharSequence personname = savedState.getCharSequence("contact_name");
        CharSequence persondesignation = savedState.getCharSequence("designation");
        CharSequence personmobile = savedState.getCharSequence("mobile");
        CharSequence personemail = savedState.getCharSequence("email");
        CharSequence productdiscussed = savedState.getCharSequence("product");
        CharSequence metdate = savedState.getCharSequence("meeting_date");
        CharSequence followup = savedState.getCharSequence("follow_up");
        CharSequence remark = savedState.getCharSequence("remarks");

        organisation_name.setText(officename);
        organisation_address.setText(officeaddress);
        organisation_phone.setText(officephone);
        website.setText(officewebsite);
        person_name.setText(personname);
        designation.setText(persondesignation);
        person_mobile.setText(personmobile);
        person_email.setText(personemail);
        product.setText(productdiscussed);
        meeting_date.setText(metdate);
        follow_up.setText(followup);
        remarks.setText(remark);
    }

    public void readContact() {
        try {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent, PICK_CONTACT_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_CONTACT_REQUEST:
                    Cursor cursor = null;
                    String phoneNumber = "";
                    List<String> allNumbers = new ArrayList<String>();
                    int phoneIdx = 0;
                    try {
                        Uri result = data.getData();
                        String id = result.getLastPathSegment();
                        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                        phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                        if (cursor.moveToFirst()) {
                            while (cursor.isAfterLast() == false) {
                                phoneNumber = cursor.getString(phoneIdx);
                                allNumbers.add(phoneNumber);
                                cursor.moveToNext();
                            }
                        } else {
                            //no results actions
                            Snackbar
                                    .make(parentLayout, R.string.not_found, Snackbar.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //error actions
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }

                        final CharSequence[] items = allNumbers.toArray(new String[allNumbers.size()]);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddLeadDetail.this);
                        builder.setTitle("Choose a number");
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                String selectedNumber = items[item].toString();
                                selectedNumber = selectedNumber.replace("-", "");
                                person_mobile.setText(selectedNumber);
                            }
                        });
                        alert = builder.create();

                        if (allNumbers.size() > 1) {
                            alert.show();
                        } else {
                            String selectedNumber = phoneNumber.toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            person_mobile.setText(selectedNumber);
                        }
                        if (phoneNumber.length() == 0) {
                            //no numbers found actions
                            Snackbar
                                    .make(parentLayout, R.string.not_found, Snackbar.LENGTH_LONG)
                                    .show();
                            //Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        } else {
            //activity result error actions
            Snackbar
                    .make(parentLayout, R.string.not_found, Snackbar.LENGTH_LONG)
                    .show();
            //Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
        }
    }

    public void saveToDatabase() {
        if (isDateValid(meeting_date.getText().toString()) && isDateValid(follow_up.getText().toString())) {
            if(organisation_name.getText().length()==0)
                organisation_name.setText(R.string.notset);

            dbHelper.addLead(new Lead(
                    organisation_name.getText().toString(),
                    organisation_address.getText().toString(),
                    organisation_phone.getText().toString(),
                    website.getText().toString(),
                    person_name.getText().toString(),
                    designation.getText().toString(),
                    person_mobile.getText().toString(),
                    person_email.getText().toString(),
                    product.getText().toString(),
                    meeting_date.getText().toString(),
                    follow_up.getText().toString(),
                    remarks.getText().toString()
            ));
            //Intent i = new Intent(this, showSummary.class);
            Intent i = new Intent(this,display_summary_contacts.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("snackbar", 1); //1 is for SAVED snackbar, 2 for saved changes snackbar
            startActivity(i);
        } else {
            Snackbar
                    .make(parentLayout, R.string.dateformaterror, Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    public void meetingDatePicker(View view) {
        // Checks whether to add date to meeting date or follow-up date
        if (view == meeting_date)
            check = 0;
        else if (view == follow_up)
            check = 1;
        showDatePicker();

    }

    public void showDatePicker() {
        SelectDateFragment selectDateFragment = new SelectDateFragment();
        selectDateFragment.show(fragmentManager, "DATEPICKER");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Display Selected date in textbox
        switch (check) {
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

    public void checkDateOrder(String meetingDate, String followupDate) {
        //checks if the followup date is after the meetingDate or not
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = simpleDateFormat.parse(meetingDate);
            Date date2 = simpleDateFormat.parse(followupDate);

            if (date1.after(date2) || date1.equals(date2)) {
                follow_up.setText(null);
                RecheckDateDialog recheckdatedialog = new RecheckDateDialog();
                recheckdatedialog.show(fragmentManager, "Recheck date");
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

        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.save_button:
                saveToDatabase();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveDialogFragment savedialogfragment = new saveDialogFragment();
        savedialogfragment.show(fragmentManager, "SAVE DIALOG");
    }

}
