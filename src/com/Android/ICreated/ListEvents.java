package com.Android.ICreated;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.Android.ICreated.Activity.EventShowActivity;
import com.Android.ICreated.Activity.EventsShowActivity;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class ListEvents extends Fragment implements StorageListener
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
        storage.addListener(this);

        lvEvents = (ListView) v.findViewById(R.id.lvEvents);
        upd();

        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                storage.setCurLatLng(storage.getEvent(position).getLatLng());
                Intent intent = new Intent(context, EventShowActivity.class);
                startActivity(intent);
            }
        });

        return  v;
    }

    private void upd()
    {
        String[] names = storage.getEventsNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.event_list_elem, names);
        lvEvents.setAdapter(adapter);
    }

    @Override
    public void onEventAdded(Event event)
    {
        upd();
    }
}
