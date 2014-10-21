package com.Android.ICreated;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Mikhail on 04.10.2014.
 */
public final class Storage extends Application
{
    private ArrayList<Event> events;
    private LatLng curLatLng;
    private long curID;
    private ArrayList<StorageListener> listeners;
    private DBHelper dbHelper;
    private ContentValues cv;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String[] columns;

    @Override
    public void onCreate()
    {
        super.onCreate();
        events = new ArrayList<Event>();
        curID = 0;
        curLatLng = null;
        listeners = new ArrayList<StorageListener>();
        getEventsFromServer();
    }

    public void getEventsFromServer()
    {
        ServerAPI.Response response = ServerAPI.getEvents();
        if (!response.isEmpty())
        {
            events = response.getEvents();
            recreateDataBase();
        }
        else
        {
            getEventsFromDataBase();
        }
    }

    public void addListener(StorageListener listener)
    {
        listeners.add(listener);
    }

    private void fireListeners(Event event)
    {
        for (StorageListener listener : listeners)
        {
            listener.onEventAdded(event);
        }
    }

    private void getEventsFromDataBase()
    {
        dbHelper = new DBHelper(this);
        db = dbHelper.getReadableDatabase();
        columns = new String[]{"id", "description", "time", "lat", "lng", "category"};
        cursor = db.query(getString(R.string.db_name), columns, null, null, null, null, null);

        if (cursor.moveToFirst())
        {
            do
            {
                String description = cursor.getString(cursor.getColumnIndex("description"));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(cursor.getLong(cursor.getColumnIndex("time")));
                LatLng latLng = new LatLng(cursor.getDouble(cursor.getColumnIndex("lat")), cursor.getDouble(cursor.getColumnIndex("lng")));
                int category = cursor.getInt(cursor.getColumnIndex("category"));
                events.add(new Event(time, latLng, description, category));
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();
    }

    private void addEventToDataBase(Event event)
    {
        cv = new ContentValues();
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        cv.put("description", event.getDescription());
        cv.put("lat", event.getLatLng().latitude);
        cv.put("lng", event.getLatLng().longitude);
        cv.put("time", event.getTime().getTimeInMillis());
        cv.put("category", event.getCategory());

        db.insert(getString(R.string.db_name), null, cv);
        Log.d("Logs", "6");

        dbHelper.close();
    }

    private void recreateDataBase()
    {
        deleteDataBase();
        for (Event event : events)
        {
            addEventToDataBase(event);
        }
    }

    private void deleteDataBase()
    {
        dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();
        db.delete(getString(R.string.db_name), null, null);
        db.close();
    }

    public void addEvent(Event event)
    {
        ++curID;
        event.setId(curID);
        events.add(event);
        addEventToDataBase(event);
        ServerAPI.addEvent("0", "0", event.getDescription(), event.getTime().toString());
        fireListeners(event);
    }

    public Event getEvent(LatLng latLng)
    {
        for (Event event : events)
        {
            if (event.getLatLng().equals(latLng))
            {
                return event;
            }
        }
        return null;
    }

    public Event[] getAllEvents()
    {
        Event[] eventsArr = new Event[events.size()];
        return events.toArray(eventsArr);
    }

    public int getSize()
    {
        return events.size();
    }

    public void setCurLatLng(LatLng latLng)
    {
        curLatLng = latLng;
    }

    public LatLng getCurLatLng()
    {
        return curLatLng;
    }
}
