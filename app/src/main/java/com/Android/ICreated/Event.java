package com.Android.ICreated;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by Mikhail on 05.10.2014.
 */
public class Event
{
    private long id;
    private Calendar time;
    private LatLng latLng;
    private String description;
    private int category;

    public Event(Calendar time, LatLng latLng, String description, int category)
    {
        id = -1;
        this.time = time;
        this.latLng = latLng;
        this.description = description;
        this.category = category;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public Calendar getTime()
    {
        return time;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }

    public int getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }
}
