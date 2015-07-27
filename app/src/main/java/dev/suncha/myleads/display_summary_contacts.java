package dev.suncha.myleads;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class display_summary_contacts extends AppCompatActivity {

    ListView summary;
    ArrayList<Lead> leadArrayList = new ArrayList<Lead>();
    DatabaseHelper db;
    newAdapter leadAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_listview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_summary_contacts, menu);
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

    public void refreshData() {
        leadArrayList.clear();
        db = new DatabaseHelper(this);
        ArrayList<Lead> lead_array_from_db = db.getAllLeads();
        for (int i = 0; i < lead_array_from_db.size(); i++) {
            int tidno = lead_array_from_db.get(i).getId();
            String companyname = lead_array_from_db.get(i).getCompany_name();
            String personname = lead_array_from_db.get(i).getPerson_name();
            String mobilenum = lead_array_from_db.get(i).getPerson_mobile();
            String emailid = lead_array_from_db.get(i).getPerson_email();

            Lead leadtoload = new Lead();
            leadtoload.setId(tidno);
            leadtoload.setCompany_name(companyname);
            leadtoload.setPerson_name(personname);
            leadtoload.setPerson_mobile(mobilenum);
            leadtoload.setPerson_email(emailid);

            leadArrayList.add(leadtoload);
        }
        db.close();
        leadAdapter = new newAdapter(display_summary_contacts.this, R.layout.display_summary_listview, leadArrayList);
        summary.setAdapter(leadAdapter);
        leadAdapter.notifyDataSetChanged();


    }


    public class newAdapter extends ArrayAdapter<Lead> {
        Activity activity;
        int layoutResourceId;
        Lead mlead;
        ArrayList<Lead> lead = new ArrayList<Lead>();

        public newAdapter(Activity act, int layoutResourceId, ArrayList<Lead> data) {
            super(act, layoutResourceId, data);
            this.layoutResourceId = layoutResourceId;
            this.activity = act;
            this.lead = data;
            notifyDataSetChanged();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            LeadHolder holder = null;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new LeadHolder();
                holder.
            } else {
                holder = (LeadHolder) row.getTag();
            }
            mlead = lead.get(position);
            holder.

        }

        class LeadHolder {
            ImageView imageView;
            ImageView checkedIcon;
            TextView companyname;
            TextView personname;
            TextView personphone;
            TextView personemail;
        }
    }
}
