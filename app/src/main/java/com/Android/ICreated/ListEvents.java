package com.Android.ICreated;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class ListEvents extends Fragment implements StorageListener
{
    Storage storage;
    Context context;
    RecyclerView rvEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.events_list, container, false);

        context = getActivity();
        storage = (Storage) getActivity().getApplication();
        storage.addListener(this);

        rvEvents = (RecyclerView) v.findViewById(R.id.list);
        rvEvents.setHasFixedSize(true);

        rvEvents.setLayoutManager(new LinearLayoutManager(context));
        upd();

        return  v;
    }

    private void upd()
    {
        EventsListAdapter adapter = new EventsListAdapter(storage.getEventsNames(), context);
        rvEvents.setAdapter(adapter);
    }

    @Override
    public void onEventAdded(Event event)
    {
        upd();
    }
}
