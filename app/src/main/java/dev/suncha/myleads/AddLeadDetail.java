package dev.suncha.myleads;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.materialdesign.widgets.Dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddLeadDetail extends ActionBarActivity {
    static final int PICK_CONTACT_REQUEST = 0;
    final Calendar c = Calendar.getInstance();
    EditText organisation_name, organisation_address, organisation_phone, website, person_name, designation, person_mobile, person_email, product, meeting_date, follow_up, remarks;
    Button pick_meetingdate;
    Button pick_followup;
    Button pickNumFromContacts;
    int month, year, day;
    int mYear = c.get(Calendar.YEAR);
    int mMonth = c.get(Calendar.MONTH);
    int mDay = c.get(Calendar.DAY_OF_MONTH);
    DatabaseHandler db = new DatabaseHandler(this);
    Dialog dialog;



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
        person_mobile.getLayoutParams().width = width / 2;

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
                            Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
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
                        AlertDialog alert = builder.create();
                        if (allNumbers.size() > 1) {
                            alert.show();
                        } else {
                            String selectedNumber = phoneNumber.toString();
                            selectedNumber = selectedNumber.replace("-", "");
                            person_mobile.setText(selectedNumber);
                        }

                        if (phoneNumber.length() == 0) {
                            //no numbers found actions
                            Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        } else {
            //activity result error actions
            Toast.makeText(getApplicationContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
        }
    }


    public void saveToDatabase() {
        db.addRecord(new myLeads(
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
        Toast.makeText(getApplication(), R.string.savesuccess, Toast.LENGTH_SHORT).show();
        finish();

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
    }

    public void checkDateOrder(String meetingDate, String followupDate) {
        //checks if the followup date is after the meetingDate or not
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = simpleDateFormat.parse(meetingDate);
            Date date2 = simpleDateFormat.parse(followupDate);

            if (date1.after(date2) || date1.equals(date2)) {
                final Dialog dateDialog = new Dialog(AddLeadDetail.this, null, "Follow up date needs to be after the meeting date.");
                dateDialog.setCancelable(false);
                dateDialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateDialog.dismiss();
                        meetingDatePicker(follow_up);

                    }
                });
                dateDialog.show();
//                new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
//                        .setTitle("Warning")
//                        .setMessage("Follow up date needs to be after the meeting date")
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                meetingDatePicker(follow_up);
//                            }
//                        })
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .show();
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
        dialog = new Dialog(AddLeadDetail.this, null, "Going back will discard any changes. Still go back?");
        dialog.setOnAcceptButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLeadDetail.this.finish();
                dialog.dismiss();
            }
        });

        dialog.addCancelButton("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }

        });
        dialog.setCancelable(false);
        dialog.show();

    }

}
