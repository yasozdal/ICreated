package com.Android.ICreated.Activity.eventsShow;


import android.os.Bundle;
import android.app.Fragment;
import com.Android.ICreated.network.MyOkHttpSpiceService;
import com.octo.android.robospice.SpiceManager;

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
