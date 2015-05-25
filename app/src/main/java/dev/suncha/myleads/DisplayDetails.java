package dev.suncha.myleads;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class DisplayDetails extends ActionBarActivity {
    //private SQLiteDatabase dataBase;
    TextView organisation_name, office_address, office_phone, website, person_name, designation, mobile, email, product_discussed, meeting_date, followup_date, remarks;
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


        Intent intent = getIntent();
        int entryId = intent.getIntExtra("key", 0);
        //Toast.makeText(getApplicationContext(),"Number "+entryId,Toast.LENGTH_LONG).show();
        //dataBase = mHelper.getWritableDatabase();
        // Cursor mCursor = dataBase.rawQuery("SELECT*FROM " + DatabaseHandler.TABLE_LEADS, null );
        //Toast.makeText(getApplicationContext(),"Name: "+mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_PER_NAME)),Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(),"Name: "+mHelper.getRecord(entryId+1).getPer_name(),Toast.LENGTH_SHORT).show();
        organisation_name.setText(mHelper.getRecord(entryId + 1).getOfic_name());
        office_address.setText(mHelper.getRecord(entryId + 1).getOfic_add());
        office_phone.setText(mHelper.getRecord(entryId + 1).getOfic_num());
        website.setText(mHelper.getRecord(entryId + 1).getWeb());
        person_name.setText(mHelper.getRecord(entryId + 1).getPer_name());
        designation.setText(mHelper.getRecord(entryId + 1).getDesignation());
        mobile.setText(mHelper.getRecord(entryId + 1).getMobile());
        email.setText(mHelper.getRecord(entryId + 1).getEmail());
        product_discussed.setText(mHelper.getRecord(entryId + 1).getProducts());
        meeting_date.setText(mHelper.getRecord(entryId + 1).getMeeting_date());
        followup_date.setText(mHelper.getRecord(entryId + 1).getfollowup_date());
        remarks.setText(mHelper.getRecord(entryId + 1).getRemarks());


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_details, menu);
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
