package com.Android.ICreated;

import android.app.Application;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Mikhail on 04.10.2014.
 */
public final class Storage extends Application
{
    private ArrayList<Event> events;
    private LatLng curLatLng;
    private long curID;

    @Override
    public void onCreate()
    {
        super.onCreate();
        events = new ArrayList<Event>();
        curID = 0;
        curLatLng = null;
    }

    public void addEvent(Event event)
    {
        ++curID;
        event.setId(curID);
        events.add(event);
    }

    public Event getEvent(LatLng latLng)
    {
        for (Event event : events)
        {
            if (event.getLatLng() == latLng)
            {
                return event;
            }
        }
        return null;
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
