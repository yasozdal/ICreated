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
    private LockType lockType;

    public enum LockType
    {
        PRIVATE,
        PUBLIC
    }

    public Event()
    {
        time = null;
        latLng = null;
        description = null;
        category = -1;
        lockType = null;
    }

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

    public void setTime(Calendar time)
    {
        this.time = time;
    }

    public Calendar getTime()
    {
        return time;
    }

    public void setLatLng(LatLng latLng)
    {
        this.latLng = latLng;
    }

    public LatLng getLatLng()
    {
        return latLng;
    }

    public void setCategory(int category)
    {
        this.category = category;
    }

    public int getCategory()
    {
        return category;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setLockType(LockType type)
    {
        this.lockType = type;
    }

    public LockType getLockType()
    {
        return lockType;
    }
}
