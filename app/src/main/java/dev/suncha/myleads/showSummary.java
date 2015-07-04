package dev.suncha.myleads;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class showSummary extends AppCompatActivity {
    //ProgressDialog progress;
    ListView summary;
    TextView noLead;
    FloatingActionButton buttonFloat;
    ActionMode mActionMode;
    FragmentManager fragmentManager = getSupportFragmentManager();
    int positionFromListview;
    CoordinatorLayout coordinatorLayout;

    private ArrayList<String> id = new ArrayList<String>();
    private ArrayList<String> com_name = new ArrayList<String>();
    private ArrayList<String> per_name = new ArrayList<String>();
    private ArrayList<String> mobile = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> rating = new ArrayList<>();

    DisplayAdapter displayAdapter = new DisplayAdapter(showSummary.this, id, com_name, per_name, mobile, email, rating);
    //private DatabaseHandler mHelper;
    private DatabaseHelper mHelper;
    private SQLiteDatabase dataBase;
    private ArrayList<Integer> selectedItemsPosition = new ArrayList<Integer>();


//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.context_menu, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            ConfirmDeleteFragment confirmDeleteFragment = new ConfirmDeleteFragment();
//            switch (item.getItemId()) {
//                case R.id.edit:
//                    Toast.makeText(getApplicationContext(), "Edit action", Toast.LENGTH_SHORT).show();
//                    mode.finish();
//                    return true;
//
//                case R.id.delete:
//                    confirmDeleteFragment.show(fragmentManager, "Delete lead");
//                    mode.finish();
//                    //return true;
//
//                default:
//                    return false;
//            }
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            buttonFloat.setVisibility(View.VISIBLE);
//            mActionMode = null;
//        }
//    };

/*    public void deleteLead() {
        displayAdapter.remove(positionFromListview);
        summary.setAdapter(displayAdapter);
        mHelper.removeLead(positionFromListview);
        populateListView();
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_previous_listview);

        setupToolbar();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        snackbarDecider();

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

        //mHelper = new DatabaseHandler(this);
        mHelper = new DatabaseHelper(this);

        noLead.setVisibility(View.GONE);
        summary.setVisibility(View.GONE);
        populateListView();

//        summary.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                positionFromListview = position;
//                Log.v(String.valueOf(positionFromListview), "Position from List view value");
//                if (mActionMode != null) {
//                    return false;
//                } else {
//                    buttonFloat.setVisibility(View.GONE);
//                    mActionMode = showSummary.this.startActionMode(mActionModeCallback);
//                    view.setSelected(true);
//                    return true;
//                }
//            }
//        });

        //batch selection

        summary.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        summary.setMultiChoiceModeListener(
                new AbsListView.MultiChoiceModeListener() {

                    @Override
                    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                        //Here you can do something when items are selected/de-selected,
                        //such as update the title in the CAB
                        final int checkedCount = summary.getCheckedItemCount();
                        if (checkedCount > 1) {
                            mode.setTitle(checkedCount + " items selected");
                        } else if (checkedCount == 0) {

                        } else
                            mode.setTitle(checkedCount + " item selected");

                        if (checked) {
                            selectedItemsPosition.add(position);
                        } else {
                            for (int i = 0; i < selectedItemsPosition.size(); i++) {
                                if (position == selectedItemsPosition.get(i))
                                    selectedItemsPosition.remove(i);
                            }
                        }

                    }

                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        buttonFloat.setVisibility(View.GONE);
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
                        //ConfirmDeleteFragment confirmDeleteFragment = new ConfirmDeleteFragment();
                        //Respond to clicks on the actions in the CAB
                        switch (item.getItemId()) {
                            case R.id.delete:
                                //confirmDeleteFragment.show(fragmentManager, "Delete lead");
                                //new deleteAsync().execute();

                                //displayAdapter.remove(selectedItemsPosition.get(i));
                                //mHelper.removeLead(selectedItemsPosition.get(i));

                                populateListView();
                                mode.finish();
                                return true;

                            case R.id.edit:
                                Intent editLead = new Intent(getApplicationContext(), EditLeadActivity.class);
                                editLead.putExtra("key", mHelper.colIndex(selectedItemsPosition.get(0)));
                                startActivity(editLead);
                                mode.finish();
                                return true;

                            default:
                                return false;
                        }
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        buttonFloat.setVisibility(View.VISIBLE);
                        selectedItemsPosition.clear();
                    }


                }

        );
    }

    private void snackbarDecider() {
        switch (getIntent().getIntExtra("snackbar", -1)) {
            case 1:
                Snackbar
                        .make(coordinatorLayout, R.string.saved, Snackbar.LENGTH_LONG)
                        .show();
                break;
            case 2:
                Snackbar
                        .make(coordinatorLayout, R.string.changessaved, Snackbar.LENGTH_LONG)
                        .show();
                break;
            default:
                break;
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        //Show menu icon
        //final ActionBar actionBar=getSupportActionBar();
    }

    public void populateListView() {
        if (mHelper.getLeadsCount() <= 0) {
            noLead.setVisibility(View.VISIBLE);
            summary.setVisibility(View.GONE);
        } else {
            dataBase = mHelper.getWritableDatabase();
            final Cursor mCursor = dataBase.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_LEADS, null);
            id.clear();
            com_name.clear();
            per_name.clear();
            mobile.clear();
            email.clear();
            rating.clear();

            if (mCursor.moveToFirst()) {
                do {
                    id.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_ID)));
                    com_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_COMPANY_NAME)));
                    per_name.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_PERSON_NAME)));
                    mobile.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_PERSON_PHONE)));
                    email.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.KEY_PERSON_EMAIL)));

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

//    public void displayDialog() {
//        progress = ProgressDialog.show(this, null, "Deleting item", true);
//        new Thread() {
//            public void run() {
//                try {
//                    Thread.sleep(800);
//                    progress.dismiss();
//                } catch (Exception e) {
//                }
//            }
//        }.start();
//    }

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

    private class deleteAsync extends AsyncTask<Void, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            for (int a = selectedItemsPosition.size() - 1; a >= 0; a--) {
                positionFromListview = selectedItemsPosition.get(a);
                displayAdapter.remove(positionFromListview);
                //mHelper.removeLead(positionFromListview);
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(showSummary.this);
            progressDialog.setMessage("Deleting...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            populateListView();
        }
    }
}
