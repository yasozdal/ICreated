package com.Android.ICreated;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import com.Android.ICreated.Activity.eventsShow.EventsShowActivity;
import com.Android.ICreated.Activity.eventsShow.EventsShowModel;
import com.Android.ICreated.Activity.eventsShow.EventsShowWorkerFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class ListEvents extends Fragment implements EventsShowModel.Observer
{
    Context context;
    RecyclerView rvEvents;
    EventsShowModel eventsShowModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.events_list, container, false);

        context = getActivity();
        eventsShowModel = ((EventsShowActivity)getActivity()).getModel();

        eventsShowModel.addObserver(this);

        rvEvents = (RecyclerView) v.findViewById(R.id.list);
        rvEvents.setHasFixedSize(true);

        rvEvents.setLayoutManager(new LinearLayoutManager(context));
        upd();

        return  v;
    }



    private void upd()
    {
        EventsListAdapter adapter = new EventsListAdapter(eventsShowModel.getEventsNames(), context, eventsShowModel);
        rvEvents.setAdapter(adapter);
    }

    @Override
    public void onEventAdded(Event event)
    {
        upd();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        eventsShowModel.removeObserver(this);
    }
}
