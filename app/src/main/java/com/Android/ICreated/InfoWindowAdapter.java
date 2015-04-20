package com.Android.ICreated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.Android.ICreated.Activity.eventsShow.EventsShowModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Mikhail on 06.10.2014.
 */
public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    private EventsShowModel eventsShowModel;
    private Context context;

    public InfoWindowAdapter(EventsShowModel eventsShowModel, Context context)
    {
        this.context = context;
        this.eventsShowModel = eventsShowModel;
    }

    @Override
    public View getInfoWindow(Marker marker)
    {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        Event event = eventsShowModel.findEventByMarker(marker);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.info_window, null);

        TextView tv = (TextView)view.findViewById(R.id.tvDescription);
        tv.setText(event.getDescription());

        return view;
    }
}
