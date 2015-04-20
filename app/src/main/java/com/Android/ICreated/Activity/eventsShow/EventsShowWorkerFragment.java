package com.Android.ICreated.Activity.eventsShow;


import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Mikhail on 11.04.2015.
 */
public class EventsShowWorkerFragment extends Fragment
{
    private EventsShowModel eventsShowModel;

    public EventsShowWorkerFragment()
    {
        eventsShowModel = new EventsShowModel();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public EventsShowModel getEventsShowModel()
    {
        return eventsShowModel;
    }
}
