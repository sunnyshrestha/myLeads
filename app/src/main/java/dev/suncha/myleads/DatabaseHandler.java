package dev.suncha.myleads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 5/14/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    static final String TABLE_LEADS = "leadsTable";

    static final String KEY_ID = "id";
    static final String KEY_OFIC_NAME = "ofic_name";
    static final String KEY_OFIC_ADD = "ofic_add";
    static final String KEY_OFIC_NUM = "ofic_num";
    static final String KEY_WEB = "web";
    //Person fields
    static final String KEY_PER_NAME = "per_name";
    static final String KEY_DESN = "designation";
    static final String KEY_MOB = "mob";
    static final String KEY_EMAIL = "email";
    //Notes
    static final String KEY_PRODUCTS = "products";
    static final String KEY_MEETING_DATE = "meeting_date";
    static final String KEY_FOLLOWUP_DATE = "followup_date";
    static final String KEY_REMARKS = "remarks";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myLeadsDatabase";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String MY_LEADS_TABLE = "CREATE TABLE " + TABLE_LEADS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_OFIC_NAME + " TEXT,"
                + KEY_OFIC_ADD + " TEXT," + KEY_OFIC_NUM + " TEXT,"
                + KEY_WEB + " TEXT," + KEY_PER_NAME + " TEXT,"
                + KEY_DESN + " TEXT," + KEY_MOB + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PRODUCTS + " TEXT,"
                + KEY_MEETING_DATE + " TEXT," + KEY_FOLLOWUP_DATE + " TEXT,"
                + KEY_REMARKS + " TEXT" + ")";
        db.execSQL(MY_LEADS_TABLE);
    }

    void addRecord(myLeads lead) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_OFIC_NAME, lead.getOfic_name());
        values.put(KEY_OFIC_ADD, lead.getOfic_add());
        values.put(KEY_OFIC_NUM, lead.getOfic_num());
        values.put(KEY_WEB, lead.getWeb());
        values.put(KEY_PER_NAME, lead.getPer_name());
        values.put(KEY_DESN, lead.getDesignation());
        values.put(KEY_MOB, lead.getMobile());
        values.put(KEY_EMAIL, lead.getEmail());
        values.put(KEY_PRODUCTS, lead.getProducts());
        values.put(KEY_MEETING_DATE, lead.getMeeting_date());
        values.put(KEY_FOLLOWUP_DATE, lead.getfollowup_date());
        values.put(KEY_REMARKS, lead.getRemarks());

        db.insert(TABLE_LEADS, null, values);
        db.close();
    }

    myLeads getRecord(int d){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LEADS, new String[]{
                KEY_ID, KEY_OFIC_NAME, KEY_OFIC_ADD, KEY_OFIC_NUM, KEY_WEB,
                KEY_PER_NAME, KEY_DESN, KEY_MOB, KEY_EMAIL,
                KEY_PRODUCTS, KEY_MEETING_DATE, KEY_FOLLOWUP_DATE, KEY_REMARKS}, KEY_ID +
                "=?", new String[]{String.valueOf(d)}, null, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();

        myLeads leads = new myLeads(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),
                cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                cursor.getString(10), cursor.getString(11), cursor.getString(12));

    return leads;
    }

    //Getting all records
    public List<myLeads> getAllRecords() {
        List<myLeads> recordList = new ArrayList<myLeads>();
        String selectQuery = "SELECT * FROM " + TABLE_LEADS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                myLeads leads = new myLeads();
                leads.setId(Integer.parseInt(cursor.getString(0)));
                leads.setOfic_name(cursor.getString(1));
                leads.setOfic_add(cursor.getString(2));
                leads.setOfic_num(cursor.getString(3));
                leads.setWeb(cursor.getString(4));
                leads.setPer_name(cursor.getString(5));
                leads.setDesignation(cursor.getString(6));
                leads.setMobile(cursor.getString(7));
                leads.setEmail(cursor.getString(8));
                leads.setProducts(cursor.getString(9));
                leads.setMeeting_date(cursor.getString(10));
                leads.setfollowup_date(cursor.getString(11));
                leads.setRemarks(cursor.getString(12));
            } while (cursor.moveToNext());
        }
        return recordList;
    }

    public int updateRecord(myLeads leads) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OFIC_NAME, leads.getOfic_name());
        values.put(KEY_OFIC_ADD, leads.getOfic_add());
        values.put(KEY_OFIC_NUM, leads.getOfic_num());
        values.put(KEY_WEB, leads.getWeb());
        values.put(KEY_PER_NAME, leads.getPer_name());
        values.put(KEY_DESN, leads.getDesignation());
        values.put(KEY_MOB, leads.getMobile());
        values.put(KEY_EMAIL, leads.getEmail());
        values.put(KEY_PRODUCTS, leads.products);
        values.put(KEY_MEETING_DATE, leads.getMeeting_date());
        values.put(KEY_FOLLOWUP_DATE, leads.getfollowup_date());
        values.put(KEY_REMARKS, leads.getRemarks());

        return db.update(TABLE_LEADS, values, KEY_ID + "=?",
                new String[]{String.valueOf(leads.getId())});
    }

    public void deleteRecord(myLeads leads) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEADS, KEY_ID + "=?",
                new String[]{String.valueOf(leads.getId())});
        db.close();
    }

    public int getRecordsCount() {
        String countQuery = "SELECT * FROM " + TABLE_LEADS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//        return cursor.getCount();
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEADS);
        onCreate(db);
    }

}
