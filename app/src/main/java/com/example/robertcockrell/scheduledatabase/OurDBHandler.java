package com.example.robertcockrell.scheduledatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by elizabeth.quick on 2/15/2015.
 */
public class OurDBHandler extends SQLiteOpenHelper{

    public long idnumber = 0;

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "newdatabase.db";
    public static final String TABLE_SCHEDULE = "schedule";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "eventname";
    public static final String COLUMN_TIME = "eventtime";
    public String[] allColumns = {COLUMN_ID, COLUMN_NAME, COLUMN_TIME};

    public OurDBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE + "(" +
                COLUMN_ID + "INTEGER PRIMARY KEY," + COLUMN_NAME + "TEXT," +
                COLUMN_TIME + "TEXT" + ")";
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        onCreate(db);
    }

    public void addEvent(String eventName, String eventTime){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, idnumber);
        values.put(COLUMN_NAME, eventName);
        values.put(COLUMN_TIME, eventTime);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(OurDBHandler.TABLE_SCHEDULE, allColumns, OurDBHandler.COLUMN_ID + " = " + idnumber, null, null, null, null);
        cursor.moveToFirst();

        idnumber++;

        db.insert(TABLE_SCHEDULE, null, values);
        db.close();
    }
}
