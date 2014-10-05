package com.Android.ICreated;

import android.app.Application;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Mikhail on 04.10.2014.
 */
public final class Storage extends Application
{
    private ArrayList<Event> events;

    @Override
    public void onCreate()
    {
        super.onCreate();
        events = new ArrayList<Event>();
    }
}
