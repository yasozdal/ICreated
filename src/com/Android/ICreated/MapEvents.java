package com.Android.ICreated;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.Android.ICreated.Activity.EventCreateActivity;
import com.Android.ICreated.Activity.EventShowActivity;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Mikhail on 28.10.2014.
 */
public class MapEvents extends Fragment implements GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, StorageListener, GoogleMap.OnInfoWindowClickListener
{
    private MapView mapView;
    private GoogleMap map;
    private Storage storage;
    private Context context;
    private UiSettings uiSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.events_map, container, false);
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        context = getActivity();
        storage = (Storage) getActivity().getApplication();
        storage.addListener(this);

        mapInit();
        loadEvents();

        return v;
    }

    private void mapInit()
    {
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);

        MapsInitializer.initialize(this.getActivity()); // Для CameraUpdate

        map.setOnMapLongClickListener(this);
        map.setOnMarkerClickListener(this);
        map.setOnInfoWindowClickListener(this);
        map.setInfoWindowAdapter(new InfoWindowAdapter(context));

        uiSettings = map.getUiSettings();
        uiSettings.setCompassEnabled(false);
    }

    private void loadEvents()
    {
        Event[] events = storage.getAllEvents();
        for (int i = 0; i < storage.getSize(); ++i)
        {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(events[i].getLatLng()));
        }
    }

    @Override
    public void onResume()
    {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapLongClick(LatLng latLng)
    {
        storage.setCurLatLng(latLng);
        Intent intent = new Intent(context, EventCreateActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMarkerClick(Marker marker)
    {
        storage.setCurLatLng(marker.getPosition());
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker)
    {
        storage.setCurLatLng(marker.getPosition());
        Intent intent = new Intent(context, EventShowActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEventAdded(Event event)
    {
        Marker marker = map.addMarker(new MarkerOptions()
                .position(event.getLatLng()));
        marker.showInfoWindow();
    }
}
