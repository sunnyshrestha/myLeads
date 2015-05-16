package dev.suncha.myleads;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;


public class showSummary extends ActionBarActivity {
    private DatabaseHandler mHelper;
    private SQLiteDatabase dataBase;

    private ArrayList<String> _id = new ArrayList<String>();
    private ArrayList<String> _com_name = new ArrayList<String>();
    private ArrayList<String> _per_name = new ArrayList<String>();
    private ArrayList<String> _mobile = new ArrayList<String>();
    private ArrayList<String> _email = new ArrayList<String>();

    private ListView summaryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_summary_listview);
        summaryList = (ListView) findViewById(R.id.list);
        mHelper = new DatabaseHandler(this);
        displayData();

    }

    private void displayData() {
        dataBase = mHelper.getWritableDatabase();
        Cursor mCursor = dataBase.rawQuery("SELECT*FROM" + DatabaseHandler.TABLE_LEADS, null);
        _com_name.clear();
        _per_name.clear();
        _mobile.clear();
        _email.clear();

        if (mCursor.moveToFirst()) {
            do {
                _com_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_OFIC_NAME)));
                _per_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_PER_NAME)));
                _mobile.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_MOB)));
                _email.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHandler.KEY_EMAIL)));
            } while (mCursor.moveToNext());
        }
        DisplayAdapter displayAdapter = new DisplayAdapter(showSummary.this, _id, _com_name, _per_name, _mobile, _email);
        summaryList.setAdapter(displayAdapter);
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
