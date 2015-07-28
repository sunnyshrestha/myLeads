package dev.suncha.myleads;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

public class display_summary_contacts extends AppCompatActivity {

    ListView summary;
    ArrayList<Lead> leadArrayList = new ArrayList<Lead>();
    DatabaseHelper db;
    newAdapter leadAdapter;
    FloatingActionButton fab;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_listview);
        try {
            summary = (ListView) findViewById(R.id.list);
            summary.setItemsCanFocus(false);
            fab = (FloatingActionButton) findViewById(R.id.buttonFloat);
            setupToolbar();
        } catch (Exception e) {
            Log.e("Exception occured ", "" + e);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addLead = new Intent(getApplicationContext(), AddLeadDetail.class);
                startActivity(addLead);
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        //Show menu icon
        //final ActionBar actionBar=getSupportActionBar();
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_display_summary_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

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
                holder.companyname = (TextView) row.findViewById(R.id.companyname);
                holder.personname = (TextView) row.findViewById(R.id.personname);
                holder.personphone = (TextView) row.findViewById(R.id.personphone);
                holder.personemail = (TextView) row.findViewById(R.id.personemail);
                holder.imageView = (ImageView) row.findViewById(R.id.imageView);
                holder.checkedIcon = (ImageView) row.findViewById(R.id.checkIcon);
                row.setTag(holder);
            } else {
                holder = (LeadHolder) row.getTag();
            }

            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(String.valueOf(mlead.getCompany_name().charAt(0)), mColorGenerator.getRandomColor());

            TextDrawable color = TextDrawable.builder()
                    .buildRect(" ", mColorGenerator.getColor("Selected"));

            mlead = lead.get(position);
            holder.imageView.setImageDrawable(drawable);
            holder.checkedIcon.setImageDrawable(color);
            holder.companyname.setText(mlead.getCompany_name());
            holder.personname.setText(mlead.getPerson_name());
            holder.personphone.setText(mlead.getPerson_mobile());
            holder.personemail.setText(mlead.getPerson_email());

            return row;
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
