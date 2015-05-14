package dev.suncha.myleads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sunny on 5/14/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myLeads";
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

        myLeads leads = new myLeads(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getString(3, cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),cursor.getString(12)));
    return leads;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
