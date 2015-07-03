package dev.suncha.myleads;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 7/3/2015.
 * Created as a substitute for DatabaseHandler in an attempt to write cleaner code
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "myleads";

    //Lead table name
    private static final String TABLE_LEADS = "leads";

    //Lead Table Columns Names
    private static final String KEY_ID = "id";
    private static final String KEY_COMPANY_NAME = "companyname";
    private static final String KEY_COMPANY_ADDRESS = "companyaddress";
    private static final String KEY_COMPANY_PHONE = "companyphone";
    private static final String KEY_COMPANY_WEBSITE = "companywebsite";
    private static final String KEY_PERSON_NAME = "personname";
    private static final String KEY_DESIGNATION = "designation";
    private static final String KEY_PERSON_PHONE = "personphone";
    private static final String KEY_PERSON_EMAIL = "personemail";
    private static final String KEY_PRODUCTS_DISCUSSED = "productsdiscussed";
    private static final String KEY_MEETING_DATE = "meetingdate";
    private static final String KEY_FOLLOWUP_DATE = "followupdate";
    private static final String KEY_REMARKS = "remarks";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LEADS_TABLE = "CREATE TABLE" + TABLE_LEADS + "("
                + KEY_ID + "INTEGER PRIMARY KEY,"
                + KEY_COMPANY_NAME + "TEXT,"
                + KEY_COMPANY_ADDRESS + "TEXT,"
                + KEY_COMPANY_PHONE + "TEXT,"
                + KEY_COMPANY_WEBSITE + "TEXT,"
                + KEY_PERSON_NAME + "TEXT,"
                + KEY_DESIGNATION + "TEXT,"
                + KEY_PERSON_PHONE + "TEXT,"
                + KEY_PERSON_EMAIL + "TEXT,"
                + KEY_PRODUCTS_DISCUSSED + "TEXT,"
                + KEY_MEETING_DATE + "TEXT,"
                + KEY_FOLLOWUP_DATE + "TEXT,"
                + KEY_REMARKS + "TEXT" + ")";
        db.execSQL(CREATE_LEADS_TABLE);
    }

    //Upgrading Database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older table if new exists
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_LEADS);
        //Create tables again
        onCreate(db);
    }

    //Adding a new lead
    public void addLead(Lead lead) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMPANY_NAME, lead.getCompany_name());
        values.put(KEY_COMPANY_ADDRESS, lead.getCompany_address());
        values.put(KEY_COMPANY_PHONE, lead.getCompany_phone());
        values.put(KEY_COMPANY_WEBSITE, lead.getCompany_web());
        values.put(KEY_PERSON_NAME, lead.getPerson_name());
        values.put(KEY_DESIGNATION, lead.getPerson_designation());
        values.put(KEY_PERSON_PHONE, lead.getPerson_mobile());
        values.put(KEY_PERSON_EMAIL, lead.getPerson_email());
        values.put(KEY_PRODUCTS_DISCUSSED, lead.getProduct_discussed());
        values.put(KEY_MEETING_DATE, lead.getMeeting_date());
        values.put(KEY_FOLLOWUP_DATE, lead.getFollowup_date());
        values.put(KEY_REMARKS, lead.getRemarks());

        //Inserting Row
        db.insert(TABLE_LEADS, null, values);
        db.close();
    }

    //Getting a single lead
    public Lead getLead(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LEADS,
                new String[]{KEY_ID,
                        KEY_COMPANY_NAME, KEY_COMPANY_ADDRESS, KEY_COMPANY_PHONE,
                        KEY_COMPANY_WEBSITE, KEY_PERSON_NAME, KEY_DESIGNATION,
                        KEY_PERSON_PHONE, KEY_PERSON_EMAIL, KEY_PRODUCTS_DISCUSSED,
                        KEY_MEETING_DATE, KEY_FOLLOWUP_DATE, KEY_REMARKS}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor!=null)
            cursor.moveToFirst();
        Lead lead = new Lead(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5 ),cursor.getString(6),
                cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),
                cursor.getString(11),cursor.getString(12));

        //Return lead
        return lead;
    }

    //getAllContacts() will return all contacts from databse in arraylist
    //format of Lead class type
    //Need to write a for loop to go through each lead

    //Getting all leads
    public List<Lead>getAllLeads(){
        List<Lead> leadList=new ArrayList<Lead>();
        //Select all query
        String selectQuery="SELECT*FROM"+ TABLE_LEADS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        //Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Lead lead=new Lead();
                lead.setId(Integer.parseInt(cursor.getString(0)));
                lead.setCompany_name(cursor.getString(1));
                lead.setCompany_address(cursor.getString(2));
                lead.setCompany_phone(cursor.getString(3));
                lead.setCompany_web(cursor.getString(4));
                lead.setPerson_name(cursor.getString(5));
                lead.setPerson_designation(cursor.getString(6));
                lead.setPerson_mobile(cursor.getString(7));
                lead.setPerson_email(cursor.getString(8));
                lead.setProduct_discussed(cursor.getString(9));
                lead.setMeeting_date(cursor.getString(10));
                lead.setFollowup_date(cursor.getString(11));
                lead.setRemarks(cursor.getString(12));
            }while (cursor.moveToNext());
        }

        //Return lead list
        return leadList;
    }

    //Getting leads count
    public int getLeadsCount(){
        String countQuery = "SELECT*FROM"+TABLE_LEADS;
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        cursor.close();

        //return count
        return cursor.getCount();
    }

    //updateLead() will update single lead in database
    //This method accepts Lead class objeact as parameter

    public  int updateLead(Lead lead){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_COMPANY_NAME, lead.getCompany_name());
        values.put(KEY_COMPANY_ADDRESS, lead.getCompany_address());
        values.put(KEY_COMPANY_PHONE, lead.getCompany_phone());
        values.put(KEY_COMPANY_WEBSITE, lead.getCompany_web());
        values.put(KEY_PERSON_NAME, lead.getPerson_name());
        values.put(KEY_DESIGNATION, lead.getPerson_designation());
        values.put(KEY_PERSON_PHONE, lead.getPerson_mobile());
        values.put(KEY_PERSON_EMAIL, lead.getPerson_email());
        values.put(KEY_PRODUCTS_DISCUSSED, lead.getProduct_discussed());
        values.put(KEY_MEETING_DATE, lead.getMeeting_date());
        values.put(KEY_FOLLOWUP_DATE, lead.getFollowup_date());
        values.put(KEY_REMARKS, lead.getRemarks());

        //updating row
        return db.update(TABLE_LEADS,values,
                KEY_ID+"=?",
                new String[]{String.valueOf(lead.getId())});
    }

    //deleteLead will delete single lead from database
    public  void deleteLead(Lead lead){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LEADS,KEY_ID +"=?",new String[]{String.valueOf(lead.getId())});
        db.close();
    }
}
