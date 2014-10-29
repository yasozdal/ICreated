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
    private EventsDataBase dataBase;

    @Override
    public void onCreate()
    {
        super.onCreate();
        events = new ArrayList<Event>();
        curID = 0;
        curLatLng = null;
        listeners = new ArrayList<StorageListener>();
        dataBase = new EventsDataBase(this);
        getEventsFromServer();
    }

    public String[] getEventsNames()
    {
        String[] names = new String[events.size()];

        for (int i = 0; i < events.size(); ++i)
        {
            names[i] = events.get(i).getDescription();
        }

        return names;
    }

    public void getEventsFromServer()
    {
        ServerAPI.Response response = ServerAPI.getEvents();
        if (!response.isEmpty())
        {
            events = response.getEvents();
            dataBase.recreateDataBase(getAllEvents());
        }
        else
        {
            events = dataBase.getEvents();
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

    public void addEvent(Event event)
    {
        ++curID;
        event.setId(curID);
        events.add(event);
        dataBase.addEvent(event);
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
