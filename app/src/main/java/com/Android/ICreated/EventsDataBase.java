package com.Android.ICreated;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mikhail on 22.10.2014.
 */
public class EventsDataBase
{
    static final String NAME = "ICreatedEventsDataBase";
    static final String ID = "id";
    static final String DESCRIPTION = "description";
    static final String TIME = "time";
    static final String LATITUDE = "lat";
    static final String LONGITUDE = "lng";
    static final String CATEGORY = "category";


    private Context context;
    private DBHelper dbHelper;
    private ContentValues cv;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] columns;

    public EventsDataBase(Context context)
    {
        this.context = context;
    }

    public ArrayList<Event> getEvents()
    {
        dbHelper = new DBHelper(context, NAME);
        ArrayList<Event> events = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        columns = new String[]{ID, DESCRIPTION, TIME, LATITUDE, LONGITUDE, CATEGORY};
        cursor = db.query(NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                String description = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(TIME)));
                LatLng latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex(LATITUDE)), cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
                int category = cursor.getInt(cursor.getColumnIndex(CATEGORY));
                events.add(new Event(time, latLng, description, category));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();
        return events;
    }

    public void addEvent(Event event)
    {
        dbHelper = new DBHelper(context, NAME);
        cv = new ContentValues();
        db = dbHelper.getWritableDatabase();

        cv.put(DESCRIPTION, event.getDescription());
        cv.put(LATITUDE, event.getLatLng().latitude);
        cv.put(LONGITUDE, event.getLatLng().longitude);
        cv.put(TIME, event.getTime().getTimeInMillis());
        cv.put(CATEGORY, event.getCategory());

        db.insert(NAME, null, cv);
        dbHelper.close();
    }

    public void recreateDataBase(Event[] events)
    {
        deleteDataBase();
        dbHelper = new DBHelper(context, NAME);
        for (int i = 0; i < events.length; ++i)
        {
            addEvent(events[i]);
        }
        dbHelper.close();
    }

    private void deleteDataBase()
    {
        dbHelper = new DBHelper(context, NAME);
        db = dbHelper.getWritableDatabase();
        db.delete(NAME, null, null);
        dbHelper.close();
    }
}
