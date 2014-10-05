package com.Android.ICreated;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

/**
 * Created by Mikhail on 05.10.2014.
 */
public class Event
{
    private Calendar time;
    private LatLng latLng;
    private String title;
    private String description;

    public Event(Calendar time, LatLng latLng, String title, String description)
    {
        this.time = time;
        this.latLng = latLng;
        this.title = title;
        this.description = description;
    }
}
