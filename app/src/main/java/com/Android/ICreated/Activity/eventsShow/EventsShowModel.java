package com.Android.ICreated.Activity.eventsShow;

import android.content.Context;
import com.Android.ICreated.Event;
import com.Android.ICreated.EventsDataBase;
import com.Android.ICreated.ServerAPI;
import com.Android.ICreated.network.Converter;
import com.Android.ICreated.network.requests.GetEventsRequest;
import com.Android.ICreated.network.responses.GetEventsResponse;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

/**
 * Created by Mikhail on 11.04.2015.
 */
public class EventsShowModel
{
    private ArrayList<Event> events;
    private CameraPosition cameraPosition;
    private ArrayList<Observer> listeners;
    private EventsDataBase dataBase;
    private ArrayList<String> markersId;
    private Context context;
    private SpiceManager spiceManager;

    public EventsShowModel()
    {
        events = new ArrayList<Event>();
        listeners = new ArrayList<Observer>();
        markersId = new ArrayList<>();
    }

    public void setContext(Context context)
    {
        this.context = context;
        dataBase = new EventsDataBase(context);
        spiceManager.execute(new GetEventsRequest(), new GetEventsRequestListener());
//        getEventsFromServer();
    }

    private final class GetEventsRequestListener implements RequestListener<GetEventsResponse> {
        @Override
        public void onRequestFailure(SpiceException spiceException) {
            //fail
        }

        @Override
        public void onRequestSuccess(GetEventsResponse result) {
            if (result.getStatusCode() == 200) {
                events = Converter.EventModelsToEvents(result.getResponse());
                dataBase.recreateDataBase(getEvents());
            }
            else {
                events = dataBase.getEvents();
            }

        }
    }

    public void setSpiceManager(SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
    }


    public String[] getEventsNames()
    {
        String[] names = new String[events.size()];

        for (int i = 0; i < events.size(); ++i)
        {
            names[i] = events.get(i).getDescription();
        }

        return names;
    }

    public Event getEvent(int index)
    {
        return  events.get(index);
    }

//    public void getEventsFromServer()
//    {
//        ServerAPI.Response response = ServerAPI.getEvents();
//        if (!response.isEmpty())
//        {
//            events = response.getEvents();
//            dataBase.recreateDataBase(getEvents());
//        }
//        else
//        {
//            events = dataBase.getEvents();
//        }
//    }

    public void addObserver(Observer observer)
    {
        listeners.add(observer);
    }

    public void removeObserver(Observer observer)
    {
        listeners.remove(observer);
    }

    private void fireListeners(Event event)
    {
        for (Observer listener : listeners)
        {
            listener.onEventAdded(event);
        }
    }

    public boolean containsObserver(Observer observer)
    {
        return listeners.contains(observer);
    }

    public Event[] getEvents()
    {
        Event[] eventsArr = new Event[events.size()];
        return events.toArray(eventsArr);
    }

    public void addMarkerId(Marker marker)
    {
        markersId.add(marker.getId());
    }

    public void addMarkerId(int index, Marker marker)
    {
        markersId.add(index, marker.getId());
    }

    public void clearMarkersId()
    {
        markersId = new ArrayList<>();
    }

    public Event findEventByMarker(Marker marker)
    {
        Event event = null;
        int i = 0;
        boolean isFound = false;

        while ((i < markersId.size()) && !isFound)
        {
            if (markersId.get(i).equals(marker.getId()))
            {
                event = events.get(i);
                isFound = true;
            }
            ++i;
        }
        return event;
    }

    public void addEvent(Event event)
    {
        events.add(0, event);
        fireListeners(event);
        dataBase.addEvent(event);
//        ServerAPI.addEvent("" + event.getLatLng().latitude, "" + event.getLatLng().longitude, event.getDescription(), event.getTime().toString());
    }

    public int getSize()
    {
        return events.size();
    }

    public CameraPosition getCameraPosition()
    {
        return cameraPosition;
    }

    public void setCameraPosition(CameraPosition cameraPosition)
    {
        this.cameraPosition = cameraPosition;
    }

    public interface Observer
    {
        public void onEventAdded(Event event);
    }
}
