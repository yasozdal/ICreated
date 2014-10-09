package com.Android.ICreated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Mikhail on 06.10.2014.
 */
public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter
{
    private Context context;

    public InfoWindowAdapter(Context context)
    {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker)
    {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker)
    {
        Storage storage = (Storage)context.getApplicationContext();
        String title = storage.getEvent(storage.getCurLatLng()).getTitle();
        String description = storage.getEvent(storage.getCurLatLng()).getDescription();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.info_window, null);

        TextView tv = (TextView)view.findViewById(R.id.tvTitle);
        tv.setText(title);
        tv = (TextView)view.findViewById(R.id.tvDescription);
        tv.setText(description);

        return view;
    }
}
