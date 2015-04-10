package com.Android.ICreated.Activity.eventCreate;

import android.app.Fragment;
import android.os.Bundle;
import com.Android.ICreated.Event;

/**
 * Created by Mikhail on 10.04.2015.
 */
public class EventCreateWorkerFragment extends Fragment
{
    private final Event event;

    public EventCreateWorkerFragment()
    {
        event = new Event();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Event getEvent()
    {
        return event;
    }
}
