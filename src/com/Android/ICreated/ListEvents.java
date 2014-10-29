package com.Android.ICreated;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class ListEvents extends Fragment
{
    Storage storage;
    Context context;
    ListView lvEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.events_list, container, false);

        context = getActivity();
        storage = (Storage) getActivity().getApplication();

        String[] names = storage.getEventsNames();

        lvEvents = (ListView) v.findViewById(R.id.lvEvents);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.event_list_elem, names);
        lvEvents.setAdapter(adapter);

        return  v;
    }
}
