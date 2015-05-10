package dev.suncha.myleads;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;


public class AddLeadDetail extends ActionBarActivity {
    EditText organisation_name,organisation_address,organisation_phone,website, person_name,designation,person_mobile,person_email,product,meeting_date,follow_up,remarks;
    Button pick_meetingdate, pick_followup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead_detail);

        organisation_name = (EditText)findViewById(R.id.et_organisation_name);
        organisation_address=(EditText)findViewById(R.id.et_organisation_address);
        organisation_phone=(EditText)findViewById(R.id.et_organisation_phone);
        website=(EditText)findViewById(R.id.et_website);

        person_name=(EditText)findViewById(R.id.et_person_name);
        designation=(EditText)findViewById(R.id.et_designation);
        person_mobile=(EditText)findViewById(R.id.et_mobile_no);
        person_email=(EditText)findViewById(R.id.et_email);

        product=(EditText)findViewById(R.id.et_product);
        meeting_date= (EditText)findViewById(R.id.et_meetingdate);
        follow_up=(EditText)findViewById(R.id.et_follow_up);
        remarks= (EditText)findViewById(R.id.remarks);

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
