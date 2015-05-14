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
    static final String TABLE_LEADS = "leads";
    static final String KEY_ID = "id";
    static final String KEY_OFIC_NAME = "org_name";
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
    private static final String DATABASE_NAME = "myLeads";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String MY_LEADS_TABLE = "CREATE TABLE "+ TABLE_LEADS +"("
                +KEY_ID+" INTEGER PRIMARY KEY,"+KEY_OFIC_NAME+" TEXT,"
                +KEY_OFIC_ADD+" TEXT,"+ KEY_OFIC_NUM+" TEXT,"
                +KEY_WEB+" TEXT,"+KEY_PER_NAME+" TEXT,"
                +KEY_DESN+" TEXT,"+KEY_MOB+" TEXT,"
                +KEY_EMAIL+" TEXT,"+ KEY_PRODUCTS+" TEXT,"
                +KEY_MEETING_DATE+" TEXT"+KEY_FOLLOWUP_DATE+" TEXT"
                +KEY_REMARKS+" TEXT"+")";
        db.execSQL(MY_LEADS_TABLE);
    }

    void addRecord(myLeads leads){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OFIC_NAME, leads.get_com_name());
        values.put(KEY_OFIC_ADD,leads.get_com_add());
        values.put(KEY_OFIC_NUM,leads.get_com_num());
        values.put(KEY_WEB,leads.get_com_web());
        values.put(KEY_PER_NAME,leads.get_per_name());
        values.put(KEY_DESN,leads.get_designation());
        values.put(KEY_MOB,leads.get_mobile());
        values.put(KEY_EMAIL,leads.get_email());
        values.put(KEY_PRODUCTS,leads._products);
        values.put(KEY_MEETING_DATE,leads.get_meeting_date());
        values.put(KEY_FOLLOWUP_DATE,leads.get_followup_date());
        values.put(KEY_REMARKS,leads.get_remarks());

        db.insert(TABLE_LEADS, null, values);
        db.close();
    }

    myLeads getRecord(int d){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_LEADS,new String[]{
                KEY_ID,KEY_OFIC_NAME,KEY_OFIC_ADD,KEY_OFIC_NUM,KEY_WEB,
                KEY_PER_NAME,KEY_DESN,KEY_MOB,KEY_EMAIL,
                KEY_PRODUCTS,KEY_MEETING_DATE,KEY_FOLLOWUP_DATE,KEY_REMARKS},KEY_ID+
        "=?",new String[]{String.valueOf(d)},null,null,null,null);
        if(cursor!=null)
            cursor.moveToFirst();

        myLeads leads = new myLeads(
        );
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
                leads.set_id(Integer.parseInt(cursor.getString(0)));
                leads.set_com_name(cursor.getString(1));
                leads.set_com_add(cursor.getString(2));
                leads.set_com_num(cursor.getString(3));
                leads.set_com_web(cursor.getString(4));
                leads.set_per_name(cursor.getString(5));
                leads.set_designation(cursor.getString(6));
                leads.set_mobile(cursor.getString(7));
                leads.set_email(cursor.getString(8));
                leads.set_products(cursor.getString(9));
                leads.set_meeting_date(cursor.getString(10));
                leads.set_followup_date(cursor.getString(11));
                leads.set_remarks(cursor.getString(12));
            } while (cursor.moveToNext());
        }
        return recordList;
    }

    public int updateRecord(myLeads leads) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_OFIC_NAME, leads.get_com_name());
        values.put(KEY_OFIC_ADD, leads.get_com_add());
        values.put(KEY_OFIC_NUM, leads.get_com_num());
        values.put(KEY_WEB, leads.get_com_web());
        values.put(KEY_PER_NAME, leads.get_per_name());
        values.put(KEY_DESN, leads.get_designation());
        values.put(KEY_MOB, leads.get_mobile());
        values.put(KEY_EMAIL, leads.get_email());
        values.put(KEY_PRODUCTS, leads._products);
        values.put(KEY_MEETING_DATE, leads.get_meeting_date());
        values.put(KEY_FOLLOWUP_DATE, leads.get_followup_date());
        values.put(KEY_REMARKS, leads.get_remarks());
        return db.update(TABLE_LEADS, values, KEY_ID + "=?",
                new String[]{String.valueOf(leads.get_id())});
    }

    public void deleteRecord(myLeads leads) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEADS, KEY_ID + "=?",
                new String[]{String.valueOf(leads.get_id())});
        db.close();
    }

    public int getRecordsCount() {
        String countQuery = "SELECT * FROM " + TABLE_LEADS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEADS);
        onCreate(db);
    }

    public void just() {

    }
}
