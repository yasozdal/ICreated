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
    private SpiceManager spiceManager = new SpiceManager(MyOkHttpSpiceService.class);

    public EventsShowWorkerFragment()
    {
        eventsShowModel = new EventsShowModel(spiceManager);
    }

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }


    public EventsShowModel getEventsShowModel()
    {
        return eventsShowModel;
    }
}
