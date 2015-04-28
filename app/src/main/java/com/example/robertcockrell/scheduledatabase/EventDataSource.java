package com.example.robertcockrell.scheduledatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by elizabeth.quick on 2/16/2015.
 */
public class EventDataSource {
    private SQLiteDatabase db;
    private OurDBHandler dbHandler;
    private String[] allColumns = {OurDBHandler.COLUMN_ID, OurDBHandler.COLUMN_NAME, OurDBHandler.COLUMN_TIME};

    public EventDataSource(Context context){
        dbHandler = new OurDBHandler(context);
    }

    public void open() throws SQLException{
        db = dbHandler.getWritableDatabase();
    }

    public void close(){
        dbHandler.close();
    }

    public EventItem createEventItem(String eventName, String eventTime){
        ContentValues values = new ContentValues();
        values.put(OurDBHandler.COLUMN_NAME, eventName);
        values.put(OurDBHandler.COLUMN_TIME, eventTime);
        long insertId = db.insert(OurDBHandler.TABLE_SCHEDULE, null, values);
        Cursor cursor = db.query(OurDBHandler.TABLE_SCHEDULE, allColumns, OurDBHandler.COLUMN_ID + "=" + insertId, null, null, null, null);
        cursor.moveToFirst();
        EventItem newEventItem = cursorToEventItem(cursor);
        cursor.close();
        return newEventItem;
    }

    public List<EventItem> getAllItems(){
        List<EventItem> list = new ArrayList<EventItem>();
        Cursor cursor = db.query(OurDBHandler.TABLE_SCHEDULE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            EventItem item = cursorToEventItem(cursor);
            list.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean deleteItem(EventItem item){
        int success = db.delete(OurDBHandler.TABLE_SCHEDULE, OurDBHandler.COLUMN_ID + "=" + item.getId(), null);
        return success > 0;
    }

    private EventItem cursorToEventItem(Cursor cursor){
        EventItem eventItem = new EventItem();
        eventItem.setId(cursor.getLong(0));
        eventItem.setEventName(cursor.getString(1));
        eventItem.setEventTime(cursor.getString(2));
        return eventItem;
    }
}
