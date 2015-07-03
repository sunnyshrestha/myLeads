package dev.suncha.myleads;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.protocol.HTTP;

import java.util.List;


public class DisplayDetails extends AppCompatActivity {
    //private SQLiteDatabase dataBase;
    TextView organisation_name, office_address, office_phone, website, person_name, designation, mobile, email, product_discussed, meeting_date, followup_date, remarks;
    Button contact, sendEmail;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private DatabaseHandler mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_details);
        mHelper = new DatabaseHandler(this);


        organisation_name = (TextView) findViewById(R.id.out_organisation_name);
        office_address = (TextView) findViewById(R.id.out_organisation_address);
        office_phone = (TextView) findViewById(R.id.out_organisation_phone);
        website = (TextView) findViewById(R.id.et_website);
        person_name = (TextView) findViewById(R.id.out_person_name);
        designation = (TextView) findViewById(R.id.out_designation);
        mobile = (TextView) findViewById(R.id.out_mobile_no);
        email = (TextView) findViewById(R.id.out_email);
        product_discussed = (TextView) findViewById(R.id.out_product);
        meeting_date = (TextView) findViewById(R.id.output_of_meetingdate);
        followup_date = (TextView) findViewById(R.id.output_of_followupdate);
        remarks = (TextView) findViewById(R.id.out_remarks);
        contact = (Button) findViewById(R.id.callormessage);
        sendEmail = (Button) findViewById(R.id.emailLeadButton);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.button_animation);


        Intent intent = getIntent();
        int entryId = intent.getIntExtra("key", -1);

        setupToolbar();

        organisation_name.setText(mHelper.getRecord(entryId).getOfic_name());
        office_address.setText(mHelper.getRecord(entryId).getOfic_add());
        office_phone.setText(mHelper.getRecord(entryId).getOfic_num());
        website.setText(mHelper.getRecord(entryId).getWeb());
        person_name.setText(mHelper.getRecord(entryId).getPer_name());
        designation.setText(mHelper.getRecord(entryId).getDesignation());
        mobile.setText(mHelper.getRecord(entryId).getMobile());
        email.setText(mHelper.getRecord(entryId).getEmail());
        product_discussed.setText(mHelper.getRecord(entryId).getProducts());
        meeting_date.setText(mHelper.getRecord(entryId).getMeeting_date());
        followup_date.setText(mHelper.getRecord(entryId).getfollowup_date());
        remarks.setText(mHelper.getRecord(entryId).getRemarks());


        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                contactLead(mobile.getText().toString());
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (width > height) {
            mobile.getLayoutParams().width = width * 4 / 5;
            email.getLayoutParams().width = width * 4 / 5;
        } else {
            mobile.getLayoutParams().width = width * 2 / 3;
            email.getLayoutParams().width = width * 2 / 3;
        }

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animAlpha);
                String emailId = email.getText().toString();
                if (emailId.length() == 0)
                    Toast.makeText(getApplicationContext(), R.string.noemail, Toast.LENGTH_SHORT).show();
                else
                    emailLead(emailId);
            }
        });
    }

    public void emailLead(String leadEmailId) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType(HTTP.PLAIN_TEXT_TYPE);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{leadEmailId});

        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe)
            startActivity(Intent.createChooser(emailIntent, ""));
        else
            Toast.makeText(getApplicationContext(), R.string.no_package, Toast.LENGTH_SHORT).show();

    }

    public void contactLead(String phone) {
        if (phone.length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.no_number, Toast.LENGTH_SHORT).show();
        } else {
            //call intent to make a call
            contactLeadDialogFragment contactLead = new contactLeadDialogFragment();
            Bundle args = new Bundle();
            args.putString("phone_number", phone);
            contactLead.setArguments(args);

            contactLead.show(fragmentManager, "CONTACT LEAD");
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        //Show menu icon
        //final ActionBar actionBar=getSupportActionBar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.context_menu, menu);
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
