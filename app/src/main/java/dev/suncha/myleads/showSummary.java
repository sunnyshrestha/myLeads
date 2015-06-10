package dev.suncha.myleads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class showSummary extends AppCompatActivity {
    ProgressDialog progress;
    ListView summary;
    TextView noLead;
    FloatingActionButton buttonFloat;
    ActionMode mActionMode;

   FragmentManager fragmentManager = getSupportFragmentManager();

    int positionFromListview = -1;

    private DatabaseHandler mHelper;
    private SQLiteDatabase dataBase;
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> com_name = new ArrayList<String>();
    private ArrayList<String> per_name = new ArrayList<String>();
    private ArrayList<String> mobile = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();

    DisplayAdapter displayAdapter = new DisplayAdapter(showSummary.this, id, com_name, per_name, mobile, email);

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            ConfirmDeleteFragment confirmDeleteFragment = new ConfirmDeleteFragment();
            switch (item.getItemId()) {
                case R.id.edit:
                    Toast.makeText(getApplicationContext(), "Edit action", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    return true;

                case R.id.delete:
                    confirmDeleteFragment.show(fragmentManager, "Delete lead");

                    mode.finish();
                    //return true;

                default:
                    return false;
            }
        }



        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public void deleteLead(){
        displayDialog();
        //displayAdapter.remove(positionFromListview);
        Log.v("Remove function done", "Message 2");
        displayAdapter.remove(positionFromListview);

        summary.setAdapter(displayAdapter);
        //Log.v("Adapter set", "Message 3");
        mHelper.removeLead(positionFromListview);
        populateListView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_listview);

        setupToolbar();

        summary = (ListView) findViewById(R.id.list);
        noLead = (TextView) findViewById(R.id.noLead);

        buttonFloat = (FloatingActionButton) findViewById(R.id.buttonFloat);
        buttonFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addLead = new Intent(getApplicationContext(), AddLeadDetail.class);
                startActivity(addLead);
            }
        });

        mHelper = new DatabaseHandler(this);
        noLead.setVisibility(View.GONE);
        summary.setVisibility(View.GONE);
        populateListView();

        summary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionFromListview = position;
                Log.v(String.valueOf(positionFromListview), "Position from List view value");
                if (mActionMode != null) {
                    return false;
                } else {
                    mActionMode = showSummary.this.startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    return true;
                }
            }
        });
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Show menu icon
        //final ActionBar actionBar=getSupportActionBar();


    }

    public void displayDialog() {
        progress = ProgressDialog.show(this, null, "Deleting item", true);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(500);
                    progress.dismiss();
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public void populateListView() {
        if (mHelper.getRecordsCount() == 0) {
            noLead.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);

        } else {
            dataBase = mHelper.getWritableDatabase();
            final Cursor mCursor = dataBase.rawQuery("SELECT*FROM " + DatabaseHandler.TABLE_LEADS, null);
            id.clear();
            com_name.clear();
            per_name.clear();
            mobile.clear();
            email.clear();

            if (mCursor.moveToFirst()) {
                do {
                    id.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_ID)));
                    com_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_OFIC_NAME)));
                    per_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_PER_NAME)));
                    mobile.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_MOB)));
                    email.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_EMAIL)));
                } while (mCursor.moveToNext());
            }
            summary.setAdapter(displayAdapter);

            summary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent showDetails = new Intent(getApplicationContext(), DisplayDetails.class);
                    showDetails.putExtra("key", mHelper.colIndex(position));
                    startActivity(showDetails);
                }
            });
            mCursor.close();
            noLead.setVisibility(View.GONE);
            summary.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_summary, menu);

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

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }


}
