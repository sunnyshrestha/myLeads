package dev.suncha.myleads;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class showSummary extends ActionBarActivity {
    ListView summary;
    private DatabaseHandler mHelper;
    private SQLiteDatabase dataBase;
    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> com_name = new ArrayList<String>();
    private ArrayList<String> per_name = new ArrayList<String>();
    private ArrayList<String> mobile = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_listview);
        summary = (ListView) findViewById(R.id.list);
        mHelper = new DatabaseHandler(this);

        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT*FROM " + DatabaseHandler.TABLE_LEADS, null);
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
        DisplayAdapter displayAdapter = new DisplayAdapter(showSummary.this, id, com_name, per_name, mobile, email);
        summary.setAdapter(displayAdapter);
        /*summary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Clicked,Toast", Toast.LENGTH_SHORT).show();
                Log.d("OK", "Clicked");
            }
        });*/
        mCursor.close();
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


}
